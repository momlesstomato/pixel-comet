package com.cometproject.game.rooms.services;

import com.cometproject.api.game.rooms.models.*;
import com.cometproject.storage.api.repositories.IRoomRepository;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Coordinates room model behavior for the room subsystem.
 */
public class RoomModelService implements IRoomModelService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomModelService.class);

    private final IRoomRepository roomRepository;
    private final IRoomModelFactory roomModelFactory;

    private final Map<String, IRoomModel> models;

    /**
     * Creates a room model service instance for the room subsystem.
     *
     * @param roomModelFactory Room model factory supplied by the caller.
     * @param roomRepository Room repository supplied by the caller.
     */
    public RoomModelService(IRoomModelFactory roomModelFactory, IRoomRepository roomRepository) {
        this.roomRepository = roomRepository;
        this.roomModelFactory = roomModelFactory;

        this.models = Maps.newConcurrentMap();
    }

    /**
     * Loads models for this room contract.
     */
    @Override
    public void loadModels() {
        this.models.clear();

        this.roomRepository.getAllModels((modelData) -> {
            for (Map.Entry<String, RoomModelData> roomModelData : modelData.entrySet()) {
                try {
                    final IRoomModel roomModel = this.roomModelFactory.createModel(roomModelData.getValue());

                    if (roomModel != null) {
                        this.models.put(roomModelData.getKey(), roomModel);
                    }
                } catch (InvalidModelException e) {
                    LOGGER.error("Failed to load model " + roomModelData.getKey(), e);
                }
            }

            LOGGER.info("Loaded " + this.models.size() + " static room models");
        });
    }

    /**
     * Returns the model for this room contract.
     *
     * @param id Id supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public IRoomModel getModel(String id) {
        return this.models.get(id);
    }

    /**
     * Returns the room model factory for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public IRoomModelFactory getRoomModelFactory() {
        return this.roomModelFactory;
    }
}
