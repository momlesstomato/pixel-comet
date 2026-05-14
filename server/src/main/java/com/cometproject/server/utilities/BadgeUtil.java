package com.cometproject.server.utilities;

import java.util.List;


/**
 * Describes badge util behavior for the Comet subsystem.
 */
public class BadgeUtil {
    private static String format(int num) {
        return (num < 10 ? "0" : "") + num;
    }

    /**
     * Executes generate for this Comet contract.
     *
     * @param guildBase Guild base supplied by the caller.
     * @param guildBaseColor Guild base color supplied by the caller.
     * @param guildStates Guild states supplied by the caller.
     * @return Result produced by the operation.
     */
    public static String generate(int guildBase, int guildBaseColor, List<Integer> guildStates) {
        String badgeImage = "b" + format(guildBase) + "" + format(guildBaseColor);

        for (int i = 0; i < 3 * 4; i += 3) {
            badgeImage += i >= guildStates.size() ? "s" : "s" + format(guildStates.get(i)) + format(guildStates.get(i + 1)) + "" + guildStates.get(i + 2);
        }

        return badgeImage;
    }
}
