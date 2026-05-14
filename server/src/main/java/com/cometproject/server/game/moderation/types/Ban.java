package com.cometproject.server.game.moderation.types;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Describes ban behavior for the moderation subsystem.
 */
public class Ban {
    private int id;
    private String data;
    private long expire;
    private BanType type;
    private String reason;

    /**
     * Creates a ban instance for the moderation subsystem.
     *
     * @param data Data supplied by the caller.
     * @throws SQLException When the operation cannot complete.
     */
    public Ban(ResultSet data) throws SQLException {
        this.id = data.getInt("id");
        this.data = data.getString("data");
        this.expire = data.getInt("expire");
        this.type = BanType.getType(data.getString("type"));
        this.reason = data.getString("reason");
    }

    /**
     * Creates a ban instance for the moderation subsystem.
     *
     * @param id Id supplied by the caller.
     * @param data Data supplied by the caller.
     * @param expire Expire supplied by the caller.
     * @param type Type supplied by the caller.
     * @param reason Reason supplied by the caller.
     */
    public Ban(int id, String data, long expire, BanType type, String reason) {
        this.id = id;
        this.data = data;
        this.expire = expire;
        this.type = type;
        this.reason = reason;
    }

    /**
     * Returns the id for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the data for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public String getData() {
        return this.data;
    }

    /**
     * Returns the expire for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public long getExpire() {
        return this.expire;
    }

    /**
     * Returns the type for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public BanType getType() {
        return this.type;
    }

    /**
     * Returns the reason for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public String getReason() {
        return this.reason;
    }
}
