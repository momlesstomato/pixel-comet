package com.cometproject.server.utilities.comporators;

import com.cometproject.server.game.rooms.objects.RoomFloorObject;
import com.cometproject.server.game.rooms.objects.RoomObject;

import java.util.Comparator;


/**
 * Describes position comparator behavior for the Comet subsystem.
 */
public class PositionComparator implements Comparator<RoomFloorObject> {
    private RoomObject roomFloorObject;

    /**
     * Creates a position comparator instance for the Comet subsystem.
     *
     * @param roomFloorObject Room floor object supplied by the caller.
     */
    public PositionComparator(RoomObject roomFloorObject) {
        this.roomFloorObject = roomFloorObject;
    }

    /**
     * Executes compare for this Comet contract.
     *
     * @param o1 O1 supplied by the caller.
     * @param o2 O2 supplied by the caller.
     * @return Result produced by the operation.
     */
    @Override
    public int compare(RoomFloorObject o1, RoomFloorObject o2) {
        final double distanceOne = o1.getPosition().distanceTo(this.roomFloorObject.getPosition());
        final double distanceTwo = o2.getPosition().distanceTo(this.roomFloorObject.getPosition());

        if (distanceOne > distanceTwo)
            return 1;
        else if (distanceOne < distanceTwo)
            return -1;

        return 0;
    }
}
