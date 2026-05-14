package com.cometproject.server.game.guides;

import com.cometproject.api.utilities.Startable;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.game.guides.types.HelpRequest;
import com.cometproject.server.game.guides.types.HelperSession;
import com.cometproject.server.network.messages.outgoing.help.guides.GuideSessionAttachedMessageComposer;
import com.cometproject.server.network.messages.outgoing.help.guides.GuideSessionDetachedMessageComposer;
import com.cometproject.server.network.messages.outgoing.help.guides.GuideSessionEndedMessageComposer;
import com.cometproject.server.network.messages.outgoing.help.guides.GuideSessionErrorMessageComposer;
import com.cometproject.server.tasks.CometThreadManager;
import com.cometproject.server.utilities.collections.ConcurrentHashSet;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Manages guide runtime state for the Comet subsystem.
 */
public class GuideManager implements Startable {

    private final Map<Integer, HelperSession> sessions = new ConcurrentHashMap<>();

    private final Map<Integer, Boolean> activeGuides = new ConcurrentHashMap<>();
    private final Set<Integer> activeGuardians = new ConcurrentHashSet<>();

    private final Map<Integer, HelpRequest> activeHelpRequests = new ConcurrentHashMap<>();

    /**
     * Starts this Comet component.
     */
    @Override
    public void start() {
        CometThreadManager.getInstance().executePeriodic(this::processRequests, 1000L, 1000L, TimeUnit.MILLISECONDS);
    }

    /**
     * Returns the active help requests for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<Integer, HelpRequest> getactiveHelpRequests()
    {
        return activeHelpRequests;
    }

    private void processRequests()
    {
        if(this.getActiveGuideCount() == 0)
        {
            if(this.activeHelpRequests.size() > 0)
            {
                for(Map.Entry<Integer, HelpRequest> help : this.activeHelpRequests.entrySet())
                {
                    HelpRequest helpRequest = help.getValue();
                    if(helpRequest.getPlayerSession() != null && !helpRequest.IsBEGIN())
                        helpRequest.getPlayerSession().send(new GuideSessionErrorMessageComposer(GuideSessionErrorMessageComposer.NO_HELPERS_AVAILABLE));
                    else if(helpRequest.getPlayerSession() != null && helpRequest.IsBEGIN())
                        helpRequest.getPlayerSession().send(new GuideSessionEndedMessageComposer(1));
                }

                this.activeHelpRequests.clear();
            }

            return;
        }

        Map<Integer, HelpRequest> req = new ConcurrentHashMap<>(this.activeHelpRequests);

        List<Map.Entry<Integer, HelpRequest>> entries = new ArrayList<Map.Entry<Integer, HelpRequest>>(req.entrySet());
        Collections.shuffle(entries);
        for(Map.Entry<Integer, HelpRequest> help : entries)
        {
            HelpRequest helpRequest = help.getValue();
            if(!helpRequest.IsBEGIN())
            {
                if(helpRequest.getProcessTicks() >= 60)
                {
                    if(helpRequest.hasGuide())
                    {
                        if(this.activeGuides.containsKey(helpRequest.guideId))
                            this.activeGuides.replace(helpRequest.guideId, false);
                        else
                            this.activeGuides.put(helpRequest.guideId, false);

                        if(helpRequest.getGuideSession() != null)
                            helpRequest.getGuideSession().send(new GuideSessionDetachedMessageComposer());

                        helpRequest.decline(helpRequest.guideId);
                        helpRequest.setGuide(0);
                    }

                    boolean Active = false;
                    for(Map.Entry<Integer, Boolean> activeGuide : activeGuides.entrySet())
                    {
                        if(!activeGuide.getValue())
                        {
                            if(!helpRequest.declined(activeGuide.getKey()))
                            {
                                Active = true;
                                helpRequest.setGuide(activeGuide.getKey());
                                helpRequest.getGuideSession().getPlayer().setHelpRequest(helpRequest);
                                helpRequest.getPlayerSession().getPlayer().setHelpRequest(helpRequest);
                                helpRequest.getPlayerSession().send(new GuideSessionAttachedMessageComposer(helpRequest, false));
                                helpRequest.getGuideSession().send(new GuideSessionAttachedMessageComposer(helpRequest, true));
                                break;
                            }
                        }
                    }

                    if(!Active)
                    {
                        helpRequest.getPlayerSession().send(new GuideSessionErrorMessageComposer(GuideSessionErrorMessageComposer.NO_HELPERS_AVAILABLE));
                        this.activeHelpRequests.remove(help.getKey());
                        continue;
                    }

                    if(helpRequest.hasGuide())
                    {
                        if(this.activeGuides.containsKey(helpRequest.guideId))
                            this.activeGuides.replace(helpRequest.guideId, true);
                        else
                            this.activeGuides.put(helpRequest.guideId, true);
                    }

                    helpRequest.resetProcessTicks();
                }
                else
                {
                    if(helpRequest.IsStop())
                    {
                        if(helpRequest.getPlayerSession() != null)
                        {
                            //helpRequest.getPlayerSession().send(new GuideSessionEndedMessageComposer(0));
                            helpRequest.getPlayerSession().send(new GuideSessionDetachedMessageComposer());
                        }

                        if(helpRequest.getGuideSession()!= null)
                        {
                            //helpRequest.getGuideSession().send(new GuideSessionEndedMessageComposer(0));
                            helpRequest.getGuideSession().send(new GuideSessionDetachedMessageComposer());

                            if(this.activeGuides.containsKey(helpRequest.guideId))
                                this.activeGuides.replace(helpRequest.guideId, false);
                            else
                                this.activeGuides.put(helpRequest.guideId, false);
                        }

                        this.activeHelpRequests.remove(help.getKey());
                        continue;
                    }

                    helpRequest.incrementProcessTicks();
                }

            }
            else
            {
                if(helpRequest.getPlayerSession() == null || helpRequest.getGuideSession() == null)
                {
                    if(helpRequest.getPlayerSession() != null)
                    {
                        //helpRequest.getPlayerSession().send(new GuideSessionEndedMessageComposer(0));
                        helpRequest.getPlayerSession().send(new GuideSessionDetachedMessageComposer());
                    }

                    if(helpRequest.getGuideSession()!= null)
                    {
                        //helpRequest.getGuideSession().send(new GuideSessionEndedMessageComposer(0));
                        helpRequest.getGuideSession().send(new GuideSessionDetachedMessageComposer());

                        if(this.activeGuides.containsKey(helpRequest.guideId))
                            this.activeGuides.replace(helpRequest.guideId, false);
                        else
                            this.activeGuides.put(helpRequest.guideId, false);
                    }

                    this.activeHelpRequests.remove(help.getKey());
                    continue;
                }

                if(helpRequest.getPlayerSession().getPlayer() == null || helpRequest.getGuideSession().getPlayer() == null)
                {
                    if(helpRequest.getPlayerSession() != null)
                    {
                        //helpRequest.getPlayerSession().send(new GuideSessionEndedMessageComposer(0));
                        helpRequest.getPlayerSession().send(new GuideSessionDetachedMessageComposer());
                    }

                    if(helpRequest.getGuideSession()!= null)
                    {
                        //helpRequest.getGuideSession().send(new GuideSessionEndedMessageComposer(0));
                        helpRequest.getGuideSession().send(new GuideSessionDetachedMessageComposer());

                        if(this.activeGuides.containsKey(helpRequest.guideId))
                            this.activeGuides.replace(helpRequest.guideId, false);
                        else
                            this.activeGuides.put(helpRequest.guideId, false);
                    }

                    this.activeHelpRequests.remove(help.getKey());
                    continue;
                }

                if(helpRequest.getPlayerSession().getPlayer().getHelpRequest() == null && helpRequest.getGuideSession().getPlayer().getHelpRequest() == null)
                {
                    if(helpRequest.getPlayerSession() != null)
                    {
                        //helpRequest.getPlayerSession().send(new GuideSessionEndedMessageComposer(0));
                        helpRequest.getPlayerSession().send(new GuideSessionDetachedMessageComposer());
                    }

                    if(helpRequest.getGuideSession()!= null)
                    {
                        //helpRequest.getGuideSession().send(new GuideSessionEndedMessageComposer(0));
                        helpRequest.getGuideSession().send(new GuideSessionDetachedMessageComposer());

                        if(this.activeGuides.containsKey(helpRequest.guideId))
                            this.activeGuides.replace(helpRequest.guideId, false);
                        else
                            this.activeGuides.put(helpRequest.guideId, false);
                    }

                    this.activeHelpRequests.remove(help.getKey());
                    continue;
                }

                if(helpRequest.getPlayerSession().getPlayer().getHelpRequest() == null || helpRequest.getGuideSession().getPlayer().getHelpRequest() == null)
                {
                    if(helpRequest.getPlayerSession() != null)
                    {
                        //helpRequest.getPlayerSession().send(new GuideSessionEndedMessageComposer(0));
                        helpRequest.getPlayerSession().send(new GuideSessionDetachedMessageComposer());
                    }

                    if(helpRequest.getGuideSession()!= null)
                    {
                        //helpRequest.getGuideSession().send(new GuideSessionEndedMessageComposer(0));
                        helpRequest.getGuideSession().send(new GuideSessionDetachedMessageComposer());

                        if(this.activeGuides.containsKey(helpRequest.guideId))
                            this.activeGuides.replace(helpRequest.guideId, false);
                        else
                            this.activeGuides.put(helpRequest.guideId, false);
                    }

                    this.activeHelpRequests.remove(help.getKey());
                    continue;
                }
            }
        }
    }

    /**
     * Executes start player duty for this Comet contract.
     *
     * @param helperSession Helper session supplied by the caller.
     */
    public void startPlayerDuty(final HelperSession helperSession) {
        this.sessions.put(helperSession.getPlayerId(), helperSession);

        if(helperSession.handlesHelpRequests()) {
            this.activeGuides.put(helperSession.getPlayerId(), false);
        }

        if(helperSession.handlesBullyReports()) {
            this.activeGuardians.add(helperSession.getPlayerId());
        }
    }

    /**
     * Executes finish player duty for this Comet contract.
     *
     * @param helperSession Helper session supplied by the caller.
     */
    public void finishPlayerDuty(final HelperSession helperSession) {
        //check if they have any on-going stuff?

        if(this.sessions.containsKey(helperSession.getPlayerId())) {
            this.sessions.remove(helperSession.getPlayerId());
        }

        if(helperSession.handlesHelpRequests()) {
            this.activeGuides.remove(helperSession.getPlayerId());
        }

        if(helperSession.handlesBullyReports()) {
            this.activeGuardians.remove(helperSession.getPlayerId());
        }
    }

    /**
     * Executes request help for this Comet contract.
     *
     * @param helpRequest Help request supplied by the caller.
     */
    public void requestHelp(final HelpRequest helpRequest) {
        this.activeHelpRequests.put(helpRequest.getPlayerId(), helpRequest);
    }

    /**
     * Returns the help request by creator for this Comet contract.
     *
     * @param playerId Player identifier used by the operation.
     * @return Value exposed by the contract.
     */
    public HelpRequest getHelpRequestByCreator(final int playerId) {
        return this.activeHelpRequests.get(playerId);
    }

    /**
     * Returns the active guide count for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getActiveGuideCount() {
        return this.activeGuides.size();
    }

    /**
     * Returns the active guardian count for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getActiveGuardianCount() {
        return this.activeGuardians.size();
    }

    /**
     * Returns the instance for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public static GuideManager getInstance() {
        return CometBootstrap.resolve(GuideManager.class);
    }
}
