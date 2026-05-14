package com.cometproject.server.game.rooms.objects.items.queue;

/**
 * Enumerates room item event type values used by the room subsystem.
 */
public enum RoomItemEventType {
    PreStepOn,
    StepOn,
    StepOff,
    Placed,
    Pickup,
    Interact
}
