package com.cometproject.games.snowwar.items;
import com.cometproject.api.networking.messages.IComposer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Carries map stuff data data for the Snow War game subsystem.
 */
public class MapStuffData extends ExtraDataBase {
    public Map<String, String> extraData;
    public static final String STATE = "state";
    public static final String RARITY = "rarity";

    /**
     * Returns the type for this Snow War game contract.
     *
     * @return Value exposed by the contract.
     */
    public int getType() {
        return 1;
    }

    /**
     * Creates a map stuff data instance for the Snow War game subsystem.
     *
     * @param data Data supplied by the caller.
     */
    public MapStuffData(String data) {
        this.extraData = new ConcurrentHashMap<>();
        setExtraData(data);
    }

    /**
     * Executes data for this Snow War game contract.
     *
     * @return Result produced by the operation.
     */
    public byte[] data() {
        StuffDataWriter data = new StuffDataWriter(1);
        data.writeInt8(this.extraData.size());
        for (String key : this.extraData.keySet()) {
            data.writeString(key);
            data.writeString(this.extraData.get(key));
        }
        return data.getData();
    }

    /**
     * Executes serialize composer for this Snow War game contract.
     *
     * @param writer Writer supplied by the caller.
     */
    public void serializeComposer(IComposer writer) {
        writer.writeInt(this.extraData.size());
        for (Map.Entry<String, String> key : this.extraData.entrySet()) {
            writer.writeString(key.getKey());
            writer.writeString(key.getValue());
        }
    }

    /**
     * Updates the extra data for this Snow War game contract.
     *
     * @param data Data supplied by the caller.
     */
    public void setExtraData(String data) {
        String[] values = data.split("\t");
        for (String part : values) {
            if (!part.isEmpty() && !part.equals("=")) {

                String[] a = part.split("=");
                if (a.length == 2) {

                    this.extraData.put(a[0], a[1]);
                }
            }
        }
    }

    /**
     * Returns the wall legacy string for this Snow War game contract.
     *
     * @return Value exposed by the contract.
     */
    public String getWallLegacyString() {
        if (this.extraData == null) {
            return "";
        }

        String data = this.extraData.get("state");
        if (data == null) {
            return "";
        }

        return data;
    }
}