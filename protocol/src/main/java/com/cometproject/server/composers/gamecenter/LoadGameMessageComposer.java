package com.cometproject.server.composers.gamecenter;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the load game message for the Pixel Protocol client.
 */
public class LoadGameMessageComposer extends MessageComposer {

    private final int gameId;

    private final String swfLocation;
    private final String accessToken;
    private final String gameServerHost;
    private final String gameServerPort;
    private final String socketPolicyPort;
    private final String assetsUrl;

    /**
     * Creates a load game message composer instance for the protocol composer subsystem.
     *
     * @param gameId Game id value supplied by the caller.
     * @param swfLocation Swf location value supplied by the caller.
     * @param accessToken Access token value supplied by the caller.
     * @param gameServerHost Game server host value supplied by the caller.
     * @param gameServerPort Game server port value supplied by the caller.
     * @param socketPolicyPort Socket policy port value supplied by the caller.
     * @param assetsUrl Assets url value supplied by the caller.
     */
    public LoadGameMessageComposer(int gameId, String swfLocation, String accessToken, String gameServerHost, String gameServerPort, String socketPolicyPort, String assetsUrl) {
        this.gameId = gameId;
        this.swfLocation = swfLocation;
        this.accessToken = accessToken;
        this.gameServerHost = gameServerHost;
        this.gameServerPort = gameServerPort;
        this.socketPolicyPort = socketPolicyPort;
        this.assetsUrl = assetsUrl;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return Composers.LoadGameMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.gameId);
        msg.writeString(Long.toString(System.currentTimeMillis()));
        msg.writeString(this.swfLocation);
        msg.writeString("best");
        msg.writeString("showAll");
        msg.writeInt(60);
        msg.writeInt(10);
        msg.writeInt(0);
        msg.writeInt(6);

        msg.writeString("habboHost");
        msg.writeString("hhus");
        msg.writeString("accessToken");
        msg.writeString(this.accessToken);
        msg.writeString("gameServerHost");
        msg.writeString(this.gameServerHost);
        msg.writeString("gameServerPort");
        msg.writeString(this.gameServerPort);
        msg.writeString("socketPolicyPort");
        msg.writeString(this.socketPolicyPort);
        msg.writeString("assetUrl");
        msg.writeString(this.assetsUrl);
    }
}
