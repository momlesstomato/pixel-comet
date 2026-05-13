package com.cometproject.server.storage.jooq.repositories;

import com.cometproject.server.game.players.data.PlayerData;
import org.jooq.Record;

final class PlayerDataMapper {
    private PlayerDataMapper() {
    }

    static PlayerData fromPlayerRecord(final Record record) {
        return new PlayerData(
                value(record, "id", Integer.class),
                value(record, "username", String.class),
                value(record, "motto", String.class),
                value(record, "figure", String.class),
                value(record, "gender", String.class),
                nullableValue(record, "email", String.class, ""),
                value(record, "rank", Integer.class),
                value(record, "credits", Integer.class),
                value(record, "currency5", Integer.class),
                value(record, "currency0", Integer.class),
                value(record, "currency103", Integer.class),
                value(record, "currency105", Integer.class),
                value(record, "reg_date", String.class),
                value(record, "last_online", Integer.class),
                "1".equals(value(record, "vip", String.class)),
                value(record, "achievement_points", Integer.class),
                value(record, "reg_timestamp", Integer.class),
                value(record, "favourite_group", Integer.class),
                value(record, "last_ip", String.class),
                value(record, "quest_id", Integer.class),
                value(record, "time_muted", Integer.class),
                value(record, "name_colour", String.class),
                value(record, "tag", String.class),
                value(record, "job", String.class),
                value(record, "view_points", Integer.class),
                null);
    }

    private static <T> T nullableValue(
            final Record record,
            final String fieldName,
            final Class<T> type,
            final T fallback) {
        final T value = record.get(fieldName, type);

        return value == null ? fallback : value;
    }

    private static <T> T value(final Record record, final String fieldName, final Class<T> type) {
        final T value = record.get(fieldName, type);

        if (value == null && type == String.class) {
            return type.cast("");
        }

        if (value == null && type == Integer.class) {
            return type.cast(0);
        }

        return value;
    }
}
