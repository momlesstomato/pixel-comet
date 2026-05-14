package com.cometproject.server.game.commands.staff.rewards;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.notification.NotificationMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.storage.queries.catalog.VoucherDao;

import java.util.Random;

/**
 * Describes voucher command behavior for the Comet subsystem.
 */
public class VoucherCommand extends ChatCommand {

    private String logDesc = "";

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        String type;
        String data;
        int limit;
        String code;
        String message;
        int createdBy = client.getPlayer().getId();

        if (params.length != 4)
            return;

        type = params[0];
        data = params[1];
        limit = Integer.parseInt(params[2]);
        code = params[3];

        message = "Vaya... parece que hay un nuevo voucher activo.\n\nHaz click aquí para ir al catálogo.\n\nEl código es: " + code;

        VoucherDao.addVoucher(type, data, createdBy, limit, code);

        if(type.equals("CRYPTOLOGY")) {
            message = "Una señal de Asgard murmura una señal divina, los montes distorsionan el mensaje... ayuda a tu tribu a descifrarlo:\n\n" + crypto(code);
            type = "crypto";
        }

        globalNotification(type, message);

        this.logDesc = "%s ha creado el voucher '%v' con %l unidades de código."
                .replace("%s", client.getPlayer().getData().getUsername())
                .replace("%v", code)
                .replace("%l", limit + "");
    }

    private void globalNotification(String image, String message) {
        NetworkManager.getInstance().getSessions().broadcast(new NotificationMessageComposer(image, message, "catalog/open/frontpage"));
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "voucher_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.voucher", "%type% %prize% %limit% %code%");
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.get("command.voucher.description");
    }

    /**
     * Returns the loggable description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getLoggableDescription(){
        return this.logDesc;
    }

    /**
     * Executes loggable for this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean Loggable(){
        return true;
    }

    private String crypto(String w) {
        char[] wordarray = w.toCharArray();
        char[] dummywordarray = w.toCharArray();

        Random random = new Random();

        int r = random.nextInt(wordarray.length - 1);
        int i = 0;

        int j = r + 1;
        while (i <= r) {
            dummywordarray[wordarray.length - i - 1] = wordarray[i];
            i++;
        }

        while (j <= wordarray.length - 1) {
            dummywordarray[j - r - 1] = wordarray[j];
            j++;
        }

        return String.valueOf(dummywordarray);
    }
}
