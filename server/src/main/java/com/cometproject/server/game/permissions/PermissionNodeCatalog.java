package com.cometproject.server.game.permissions;

/**
 * Provides stable permission node names shared by gameplay permission checks.
 */
public final class PermissionNodeCatalog {
    private static final int[] PERSONAL_STAFF_EFFECTS = {
            600, 780, 779, 776, 774, 775, 766, 773, 772, 770, 102, 769, 767, 178, 783, 781, 187, 768, 782, 786, 0
    };

    private PermissionNodeCatalog() {

    }

    /**
     * Builds the permission node for one avatar effect.
     *
     * @param effectId the avatar effect id.
     * @return the effect permission node.
     */
    public static String effect(final int effectId) {
        return "effects." + effectId;
    }

    /**
     * Builds the permission node for one chat bubble.
     *
     * @param bubbleId the chat bubble id.
     * @return the chat bubble permission node.
     */
    public static String chatBubble(final int bubbleId) {
        return "chat_bubbles." + bubbleId;
    }

    /**
     * Returns effects ordered from highest legacy restriction to lowest.
     *
     * @return the ordered personal staff effect ids.
     */
    public static int[] personalStaffEffects() {
        return PERSONAL_STAFF_EFFECTS.clone();
    }
}
