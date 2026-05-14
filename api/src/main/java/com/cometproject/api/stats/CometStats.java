package com.cometproject.api.stats;

/**
 * Describes comet stats behavior for the Comet subsystem.
 */
public class CometStats {
    private int players;
    private int rooms;
    private String uptime;

    private int processId;
    private long allocatedMemory;
    private long usedMemory;
    private String operatingSystem;
    private int cpuCores;

    /**
     * Returns the players for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPlayers() {
        return players;
    }

    /**
     * Updates the players for this Comet contract.
     *
     * @param players Players supplied by the caller.
     */
    public void setPlayers(int players) {
        this.players = players;
    }

    /**
     * Returns the rooms for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRooms() {
        return rooms;
    }

    /**
     * Updates the rooms for this Comet contract.
     *
     * @param rooms Rooms supplied by the caller.
     */
    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    /**
     * Returns the uptime for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getUptime() {
        return uptime;
    }

    /**
     * Updates the uptime for this Comet contract.
     *
     * @param uptime Uptime supplied by the caller.
     */
    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    /**
     * Returns the process id for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getProcessId() {
        return processId;
    }

    /**
     * Updates the process id for this Comet contract.
     *
     * @param processId Process id supplied by the caller.
     */
    public void setProcessId(int processId) {
        this.processId = processId;
    }

    /**
     * Returns the allocated memory for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public long getAllocatedMemory() {
        return allocatedMemory;
    }

    /**
     * Updates the allocated memory for this Comet contract.
     *
     * @param allocatedMemory Allocated memory supplied by the caller.
     */
    public void setAllocatedMemory(long allocatedMemory) {
        this.allocatedMemory = allocatedMemory;
    }

    /**
     * Returns the used memory for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public long getUsedMemory() {
        return usedMemory;
    }

    /**
     * Updates the used memory for this Comet contract.
     *
     * @param usedMemory Used memory supplied by the caller.
     */
    public void setUsedMemory(long usedMemory) {
        this.usedMemory = usedMemory;
    }

    /**
     * Returns the operating system for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getOperatingSystem() {
        return operatingSystem;
    }

    /**
     * Updates the operating system for this Comet contract.
     *
     * @param operatingSystem Operating system supplied by the caller.
     */
    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    /**
     * Returns the cpu cores for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getCpuCores() {
        return cpuCores;
    }

    /**
     * Updates the cpu cores for this Comet contract.
     *
     * @param cpuCores Cpu cores supplied by the caller.
     */
    public void setCpuCores(int cpuCores) {
        this.cpuCores = cpuCores;
    }
}
