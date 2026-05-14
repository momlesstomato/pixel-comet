package com.cometproject.server.composers.camera;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the photo preview message for the Pixel Protocol client.
 */
public class PhotoPreviewMessageComposer extends MessageComposer {

    private final String fileName;

    /**
     * Creates a photo preview message composer instance for the protocol composer subsystem.
     *
     * @param fileName File name value supplied by the caller.
     */
    public PhotoPreviewMessageComposer(final String fileName) {
        this.fileName = fileName;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return Composers.PhotoPreviewMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString(this.fileName);
    }
}
