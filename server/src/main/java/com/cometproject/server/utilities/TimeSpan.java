package com.cometproject.server.utilities;

/**
 * Describes time span behavior for the Comet subsystem.
 */
public class TimeSpan {
    private long difference;

    /**
     * Creates a time span instance for the Comet subsystem.
     *
     * @param start Start supplied by the caller.
     * @param finish Finish supplied by the caller.
     */
    public TimeSpan(long start, long finish) {
        this.difference = finish - start;
    }

    /**
     * Executes milliseconds to date for this Comet contract.
     *
     * @param time Time supplied by the caller.
     * @return Result produced by the operation.
     */
    public static String millisecondsToDate(long time) {

        int SECOND = 1000;
        int MINUTE = 60 * SECOND;
        int HOUR = 60 * MINUTE;
        int DAY = 24 * HOUR;

        long ms = time;

        StringBuffer text = new StringBuffer();
        if (ms > DAY) {
            text.append(ms / DAY).append("d ");
            ms %= DAY;
        } else if (ms > HOUR) {
            text.append(ms / HOUR).append("h ");
            ms %= HOUR;
        } else if (ms > MINUTE) {
            text.append(ms / MINUTE).append("min ");
            ms %= MINUTE;
        } else if (ms > SECOND) {
            text.append(ms / SECOND).append("sec ");
            ms %= SECOND;
        }

        return text.toString();
    }

    /**
     * Executes to seconds for this Comet contract.
     *
     * @return Result produced by the operation.
     */
    public long toSeconds() {
        return this.difference / 1000;
    }

    /**
     * Executes to milliseconds for this Comet contract.
     *
     * @return Result produced by the operation.
     */
    public long toMilliseconds() {
        return this.difference;
    }

    /**
     * Executes to minutes for this Comet contract.
     *
     * @return Result produced by the operation.
     */
    public long toMinutes() {
        return (this.difference / 1000) / 60;
    }

    /**
     * Executes to hours for this Comet contract.
     *
     * @return Result produced by the operation.
     */
    public long toHours() {
        return ((this.difference / 1000) / 60) / 60;
    }

    /**
     * Executes to days for this Comet contract.
     *
     * @return Result produced by the operation.
     */
    public long toDays() {
        return (((this.difference / 1000) / 60) / 60) / 24;
    }

    /**
     * Executes to weeks for this Comet contract.
     *
     * @return Result produced by the operation.
     */
    public long toWeeks() {
        return ((((this.difference / 1000) / 60) / 60) / 24) / 7;
    }
}
