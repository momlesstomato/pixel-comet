package com.cometproject.server.game.rooms.models;

import com.cometproject.api.game.rooms.models.InvalidModelException;
import com.cometproject.api.game.rooms.models.IRoomModel;
import com.cometproject.api.game.rooms.models.IRoomModelFactory;
import com.cometproject.api.game.rooms.models.RoomModelData;
import com.cometproject.server.game.rooms.models.types.DynamicRoomModel;


/**
 * Server-side implementation of {@link IRoomModelFactory} that creates
 * {@link IRoomModel} instances using the server's own {@link RoomModel} infrastructure.
 */
public class ServerRoomModelFactory implements IRoomModelFactory {

    /** Shared singleton instance. */
    public static final ServerRoomModelFactory INSTANCE = new ServerRoomModelFactory();

    private ServerRoomModelFactory() {}

    /**
     * Creates a new {@link IRoomModel} from the given {@link RoomModelData}.
     *
     * @param roomModelData the descriptor with heightmap and door coordinates.
     * @return a parsed room model.
     * @throws InvalidModelException if the heightmap cannot be parsed.
     */
    @Override
    public IRoomModel createModel(RoomModelData roomModelData) throws InvalidModelException {
        return new DynamicRoomModel(roomModelData);
    }
}
