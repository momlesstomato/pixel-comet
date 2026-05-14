package com.cometproject.tools.packets.instances;

import com.cometproject.api.networking.messages.IMessageComposer;
import com.cometproject.tools.packets.Packet;
import javolution.util.FastList;

import java.util.List;


/**
 * Serializes the value message for the Pixel Protocol client.
 */
public class MessageComposer extends Packet {
    private List<String> structure;
    private String parserName;

    /**
     * Creates a message composer instance for the tooling subsystem.
     *
     * @param id Id supplied by the caller.
     * @param className Class name supplied by the caller.
     * @param parserName Parser name supplied by the caller.
     */
    public MessageComposer(short id, String className, String parserName) {
        super(id, className, PacketType.COMPOSER);

        this.structure = new FastList<>();
        this.parserName = parserName;
    }

    /**
     * Executes append structure for this tooling contract.
     *
     * @param type Type supplied by the caller.
     */
    public void appendStructure(String type) {
        this.structure.add(type);
    }

    /**
     * Updates the structure for this tooling contract.
     *
     * @param structure Structure supplied by the caller.
     */
    public void setStructure(List<String> structure) {
        this.structure = structure;
    }

    /**
     * Returns the structure for this tooling contract.
     *
     * @return Value exposed by the contract.
     */
    public List<String> getStructure() {
        return this.structure;
    }
}
