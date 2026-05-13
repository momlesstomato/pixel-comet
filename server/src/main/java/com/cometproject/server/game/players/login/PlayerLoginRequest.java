package com.cometproject.server.game.players.login;

import java.time.LocalDate;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import com.cometproject.api.config.CometSettings;
import com.cometproject.api.config.Configuration;
import com.cometproject.api.events.players.OnPlayerLoginEvent;
import com.cometproject.api.game.achievements.types.AchievementType;
import com.cometproject.api.game.sso.ISsoTicketService;
import com.cometproject.api.game.sso.SsoTicket;
import com.cometproject.api.networking.sessions.ISession;
import com.cometproject.server.boot.Comet;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.moderation.BanManager;
import com.cometproject.server.game.moderation.ModerationManager;
import com.cometproject.server.game.moderation.types.BanType;
import com.cometproject.server.game.players.PlayerManager;
import com.cometproject.server.game.players.login.exceptions.InvalidSSOTicketException;
import com.cometproject.server.game.players.types.Player;
import com.cometproject.server.game.rooms.RoomManager;
import com.cometproject.server.game.sso.exceptions.SsoBackendUnavailableException;
import com.cometproject.server.modules.ModuleManager;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.handshake.AuthenticationOKMessageComposer;
import com.cometproject.server.network.messages.outgoing.handshake.HomeRoomMessageComposer;
import com.cometproject.server.network.messages.outgoing.handshake.UniqueIDMessageComposer;
import com.cometproject.server.network.messages.outgoing.landing.calendar.CampaignCalendarDataMessageComposer;
import com.cometproject.server.network.messages.outgoing.messenger.InviteFriendMessageComposer;
import com.cometproject.server.network.messages.outgoing.moderation.CfhTopicsInitMessageComposer;
import com.cometproject.server.network.messages.outgoing.navigator.FavouriteRoomsMessageComposer;
import com.cometproject.server.network.messages.outgoing.notification.AlertMessageComposer;
import com.cometproject.server.network.messages.outgoing.notification.MassEventMessageComposer;
import com.cometproject.server.network.messages.outgoing.notification.NotificationMessageComposer;
import com.cometproject.server.network.messages.outgoing.nuxs.EmailVerificationWindowMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.engine.RoomForwardMessageComposer;
import com.cometproject.server.network.messages.outgoing.user.club.ClubStatusMessageComposer;
import com.cometproject.server.network.messages.outgoing.user.details.AvailabilityStatusMessageComposer;
import com.cometproject.server.network.messages.outgoing.user.details.PlayerSettingsMessageComposer;
import com.cometproject.server.network.messages.outgoing.user.details.UserObjectMessageComposer;
import com.cometproject.server.network.messages.outgoing.user.inventory.EffectsInventoryMessageComposer;
import com.cometproject.server.network.messages.outgoing.user.mistery.MisteryBoxDataMessageComposer;
import com.cometproject.server.network.messages.outgoing.user.permissions.FuserightsMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.network.sessions.SessionManager;
import com.cometproject.server.storage.queries.player.PlayerAccessDao;
import com.cometproject.server.storage.queries.player.PlayerDao;
import com.cometproject.storage.api.repositories.IPlayerRepository;
import com.cometproject.server.tasks.CometTask;
import com.cometproject.server.tasks.CometThreadManager;

public class PlayerLoginRequest implements CometTask {

    private final Session client;
    private final String ticket;
    private final ISsoTicketService ssoTicketService;
    private final IPlayerRepository playerRepository;

    /**
     * Creates a login task for a single client authentication attempt.
     *
     * @param client           the authenticating client session.
     * @param ticket           the SSO ticket supplied by the client.
     * @param ssoTicketService the service that validates and consumes SSO tickets.
     * @param playerRepository the repository used to load the authenticated player.
     */
    public PlayerLoginRequest(
            Session client,
            String ticket,
            ISsoTicketService ssoTicketService,
            IPlayerRepository playerRepository) {
        this.client = client;
        this.ticket = ticket;
        this.ssoTicketService = ssoTicketService;
        this.playerRepository = playerRepository;
    }

    @Override
    public void run() {
        if (this.client == null) {
            return;
        }

        try {
            final String rawTicket = this.resolveRawTicket();
            final SsoTicket consumedTicket = this.consumeTicket(rawTicket);
            final Player player = this.loadPlayer(consumedTicket.playerId());

            if (player == null) {
                throw new InvalidSSOTicketException();
            }

            Session cloneSession = NetworkManager.getInstance().getSessions().getByPlayerId(player.getId());

            if (cloneSession != null && cloneSession.getPlayer() != null && cloneSession.getPlayer().getData() != null) {
                player.setData(cloneSession.getPlayer().getData());
                cloneSession.disconnect();
            }

            if (BanManager.getInstance().hasBan(Integer.toString(player.getId()), BanType.USER)) {
                client.getLogger().warn("Banned player: " + player.getId() + " tried logging in");
                client.disconnect("banned");
                return;
            }

            player.setSession(client);
            client.setPlayer(player);

            boolean hasTradeBan = BanManager.getInstance().hasBan(Integer.toString(player.getId()), BanType.TRADE);

            if (hasTradeBan) {
                client.getPlayer().getSettings().setAllowTrade(false);
                PlayerDao.saveAllowTrade(false, client.getPlayer().getId());
            } else if(client.getPlayer().getStats().getBans() > 0){
                client.getPlayer().getStats().setTradeLock(0);
                client.getPlayer().getStats().save();

                client.getPlayer().getSettings().setAllowTrade(true);
                client.getPlayer().getSettings().flush();
                PlayerDao.saveAllowTrade(true, client.getPlayer().getId());
                client.getLogger().warn("Unbanned player: " + player.getId() + " with expired trade sanction.");
            }

            String ipAddress = client.getIpAddress();

            if (ipAddress != null && !ipAddress.isEmpty()) {
                if (BanManager.getInstance().hasBan(ipAddress, BanType.IP)) {
                    client.getLogger().warn("Banned player: " + player.getId() + " tried logging in");
                    client.disconnect("banned");
                    return;
                }

                client.getPlayer().getData().setIpAddress(ipAddress);

                if (PlayerManager.getInstance().getPlayerCountByIpAddress(ipAddress) > CometSettings.maxConnectionsPerIpAddress) {
                    client.disconnect();
                    return;
                }
            }

            if (CometSettings.saveLogins)
                PlayerAccessDao.saveAccess(player.getId(), client.getUniqueId(), ipAddress);

            RoomManager.getInstance().loadRoomsForUser(player);

            client.getLogger().debug(client.getPlayer().getData().getUsername() + " logged in");

            player.setOnline(true);

            if(player.getSubscription().isValid()){
                player.getData().setVip(true);
                PlayerDao.updateSubscription(true, player.getId());
            } else {
                //player.getData().setTag("");
                PlayerDao.updateSubscription(false, player.getId());
            }

            boolean newPlayer = false;//client.getPlayer().getSettings().getNuxStatus() == 0;

            this.playerRepository.updateOnlineStatus(player.getId(), player.isOnline());
            this.playerRepository.updateLastOnline(player.getId(), player.getData().getIpAddress());

            client.sendQueue(new UniqueIDMessageComposer(client.getUniqueId()))
                    .sendQueue(new AuthenticationOKMessageComposer())
                    .sendQueue(new UserObjectMessageComposer(client.getPlayer()))
                    .sendQueue(new FuserightsMessageComposer(client.getPlayer().getSubscription().isValid(), client.getPlayer().getData().getRank()))
                    .sendQueue(new ClubStatusMessageComposer(client.getPlayer().getSubscription())).
                    sendQueue(new FavouriteRoomsMessageComposer(client.getPlayer().getNavigator().getFavouriteRooms())).
                    sendQueue(new AvailabilityStatusMessageComposer()).
                    sendQueue(new PlayerSettingsMessageComposer(player.getSettings(), 2)).
                    sendQueue(new HomeRoomMessageComposer(BanManager.getInstance().hasBan(client.getPlayer().getId() + "", BanType.PRISON) ? CometSettings.lawRoomId : newPlayer ? CometSettings.baseWelcomeRoomId : player.getSettings().getHomeRoom(), BanManager.getInstance().hasBan(client.getPlayer().getId() + "", BanType.PRISON) ? CometSettings.lawRoomId : newPlayer ? CometSettings.baseWelcomeRoomId : player.getSettings().getHomeRoom())).
                    sendQueue(new MisteryBoxDataMessageComposer(player.getMistery())).
                    sendQueue(new CampaignCalendarDataMessageComposer(player.getGifts())).
                    sendQueue(new EffectsInventoryMessageComposer(player.getInventory().getEffects(), player.getInventory().getEquippedEffect())).
                    sendQueue(new CfhTopicsInitMessageComposer());

            if (client.getPlayer().getPermissions().getRank().modTool())
                client.sendQueue(new EmailVerificationWindowMessageComposer(1,1));


            if(hasTradeBan)
                client.sendQueue(new NotificationMessageComposer("trade_block", Locale.getOrDefault("user.got.tradeblocked", "Se ha detectado una actividad sospechosa en tu cuenta y tus tradeos han sido bloqueados.")));

            if (CometSettings.motdEnabled) {
                client.sendQueue(new MassEventMessageComposer("habbopages/users/wabboween.txt?" + Comet.getTime()));
                client.sendQueue(new MassEventMessageComposer("habblet/open/socketLogin?sso=" + client.getPlayer().getId()));
            }

            if (CometSettings.onlineRewardDoubleDays.size() != 0) {
                LocalDate date = LocalDate.now();

                /*if (CometSettings.onlineRewardDoubleDays.contains(date.getDayOfWeek())) {
                    client.sendQueue(new MotdNotificationMessageComposer(Locale.getOrDefault("reward.double.points", "Hey %username%, \n\nToday we're giving out double points!").replace("%username%", player.getData().getUsername())));
                }*/
            }

            client.flush();

            if (client.getPlayer().getPermissions().getRank().sendLoginNotif() || player.getSettings().sendLoginNotif()) {
                NetworkManager.getInstance().getSessions().broadcast(new NotificationMessageComposer("generic",
                        Locale.getOrDefault("player.online", "%username% is online!")
                                .replace("%username%", player.getData().getUsername())));
            }

            if(client.getPlayer().getSettings().getNuxStatus() == 0){
                for (Session staff : ModerationManager.getInstance().getModerators()) {
                    staff.send(new InviteFriendMessageComposer(Locale.getOrDefault("onboarding.alert", "%p acaba de registrarse en %h.").replace("%p", client.getPlayer().getData().getUsername()).replace("%h", CometSettings.hotelName), Integer.MIN_VALUE + 4999));
                }

                client.getPlayer().getData().setFlaggingUser(true);
                client.getPlayer().getData().setChangingName(true);
                client.send(new UserObjectMessageComposer(client.getPlayer()));
            }

            // Process the achievements
            client.getPlayer().getAchievements().progressAchievement(AchievementType.ACH_31, 1);

            int regDate = StringUtils.isNumeric(client.getPlayer().getData().getRegDate()) ? Integer.parseInt(client.getPlayer().getData().getRegDate()) : client.getPlayer().getData().getRegTimestamp();

            if (regDate != 0) {
                int daysSinceRegistration = (int) Math.floor((((int) Comet.getTime()) - regDate) / 86400);

                if (!client.getPlayer().getAchievements().hasStartedAchievement(AchievementType.ACH_41)) {
                    client.getPlayer().getAchievements().progressAchievement(AchievementType.ACH_41, daysSinceRegistration);
                } else {
                    // Progress their achievement from the last progress to now.
                    int progress = client.getPlayer().getAchievements().getProgress(AchievementType.ACH_41).getProgress();
                    if (daysSinceRegistration > client.getPlayer().getAchievements().getProgress(AchievementType.ACH_41).getProgress()) {
                        int amountToProgress = daysSinceRegistration - progress;
                        client.getPlayer().getAchievements().progressAchievement(AchievementType.ACH_41, amountToProgress);
                    }
                }
            }

            if (player.getData().getAchievementPoints() < 0) {
                player.getData().setAchievementPoints(0);
                player.getData().save();
            }

            if (ModuleManager.getInstance().getEventHandler().handleEvent(new OnPlayerLoginEvent(client.getPlayer()))) {
                client.disconnect();
            }

            if (SessionManager.isLocked) {
                client.send(new AlertMessageComposer("Hotel's closed, come back later!"));
                CometThreadManager.getInstance().executeSchedule(client::disconnect, 5, TimeUnit.SECONDS);
            }

            if (client.getPlayer().getData().getTimeMuted() != 0) {
                if (client.getPlayer().getData().getTimeMuted() < (int) Comet.getTime()) {
                    PlayerDao.addTimeMute(player.getData().getId(), 0);
                }
            }

            player.getSession().getLogger().info("{} logged in from IP {}", player.getData().getUsername(), player.getData().getIpAddress());
            PlayerManager.getInstance().createAuthToken(player.getId(), rawTicket);
            client.registerAuthToken(rawTicket);

            if(client.getPlayer().getSettings().getNuxStatus() == 0) client.send(new MassEventMessageComposer("welcomewizard/show"));
            if(client.getPlayer().getSettings().getNuxStatus() == 0){
                client.send(new MassEventMessageComposer("welcomewizard/show"));
                for (ISession session : NetworkManager.getInstance().getSessions().getSessions().values()){
                    if(session.getPlayer().getData().getRank() > 2){
                        try{
                            session.send(new InviteFriendMessageComposer(Locale.getOrDefault("onboarding.alert", "%p acaba de registrarse en %h.").replace("%p", this.client.getPlayer().getData().getUsername()).replace("%h", CometSettings.hotelName), -2147478648));
                            session.send(new WhisperMessageComposer(session.getPlayer().getEntity().getId(), "Un usuario se acaba de registrar en el hotel.", 2));
                        }
                        catch (Exception ignored) { }
                    }
                }

            }

            try{
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if(client.getPlayer().getEntity() == null){
                            client.send(new RoomForwardMessageComposer(Integer.parseInt(Configuration.currentConfig().get("comet.recibidor.id"))));
                        }
                    }
                }, 3000);
            }
            catch (Exception e) { }

            } catch (InvalidSSOTicketException exception) {
            client.getLogger().warn("Session was disconnected because the supplied SSO ticket was invalid.");
            client.disconnect();
            } catch (SsoBackendUnavailableException exception) {
            client.getLogger().error("Session was disconnected because the SSO backend is unavailable.", exception);
            client.disconnect();
            } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String resolveRawTicket() {
        if (this.ticket.contains(":")) {
            final String[] ticketData = this.ticket.split(":", 2);

            if (ticketData.length == 2 && !ticketData[1].isBlank()) {
                return ticketData[1];
            }
        }

        return this.ticket;
    }

    private SsoTicket consumeTicket(final String rawTicket) throws InvalidSSOTicketException {
        return this.ssoTicketService.consume(rawTicket)
                .orElseThrow(InvalidSSOTicketException::new);
    }

    private Player loadPlayer(final int playerId) {
        final Player[] loadedPlayer = new Player[1];

        this.playerRepository.findById(playerId, player -> {
            if (player instanceof Player serverPlayer) {
                loadedPlayer[0] = serverPlayer;
            }
        });

        return loadedPlayer[0];
    }
}
