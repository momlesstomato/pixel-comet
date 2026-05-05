package com.cometproject.server.api.routes;

import com.cometproject.server.api.ApiRequestUtils;
import com.cometproject.server.api.ApiResponseUtils;
import com.cometproject.server.api.rooms.RoomStats;
import com.cometproject.server.game.rooms.RoomManager;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.outgoing.notification.AdvancedAlertMessageComposer;
import io.javalin.http.Context;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class RoomRoutes {
    public static void getAllActiveRooms(final Context context) {
        List<RoomStats> activeRooms = new ArrayList<>();

        for (Room room : RoomManager.getInstance().getRoomInstances().values()) {
            activeRooms.add(new RoomStats(room));
        }

        ApiResponseUtils.success(context, Map.of("active_rooms", activeRooms));
    }

    public static void roomAction(final Context context) {
        final Integer roomId = ApiRequestUtils.pathInt(context, "id");

        if (roomId == null) {
            ApiResponseUtils.error(context, 400, "invalid_id", "Invalid room id.");
            return;
        }

        String action = context.pathParam("action");

        if (!RoomManager.getInstance().getRoomInstances().containsKey(roomId)) {
            ApiResponseUtils.error(context, 404, "room_not_active", "Room is not currently active.");
            return;
        }

        Room room = RoomManager.getInstance().get(roomId);

        switch (action) {
            default: {
                ApiResponseUtils.error(context, 400, "invalid_action", "Unsupported room action.");
                break;
            }

            case "alert": {
                String message = ApiRequestUtils.firstNonBlank(
                        context.header("message"),
                        ApiRequestUtils.bodyField(context, "message"),
                        context.queryParam("message")
                );
                if (StringUtils.isBlank(message)) {
                    ApiResponseUtils.error(context, 400, "missing_message", "Message is required.");
                } else {
                    room.getEntities().broadcastMessage(new AdvancedAlertMessageComposer(message));
                    ApiResponseUtils.success(context, Map.of(
                            "room_id", roomId,
                            "action", action,
                            "broadcast", true
                    ));
                }
                break;
            }

            case "dispose": {
                String message = ApiRequestUtils.firstNonBlank(
                        context.header("message"),
                        ApiRequestUtils.bodyField(context, "message"),
                        context.queryParam("message")
                );

                if (message != null && !message.isEmpty()) {
                    room.getEntities().broadcastMessage(new AdvancedAlertMessageComposer(message));
                }

                room.setIdleNow();
                ApiResponseUtils.success(context, Map.of(
                        "room_id", roomId,
                        "action", action,
                        "disposed", true
                ));
                break;
            }

            case "data": {
                ApiResponseUtils.success(context, Map.of(
                        "room_id", roomId,
                        "room_data", room.getData()
                ));
                break;
            }

            case "delete": {
                ApiResponseUtils.error(context, 501, "not_implemented", "Delete room action is not implemented.");
                break;
            }
        }
    }
}
