package com.cometproject.stresstest.connections;

/**
 * Describes comet client config behavior for the tooling subsystem.
 */
public class CometClientConfig {
    private String hostName;
    private int port;
    private String ssoTicket;

    /**
     * Creates a comet client config instance for the tooling subsystem.
     *
     * @param hostName Host name supplied by the caller.
     * @param port Port supplied by the caller.
     * @param ssoTicket Sso ticket supplied by the caller.
     */
    public CometClientConfig(String hostName, int port, String ssoTicket) {
        this.hostName = hostName;
        this.port = port;
        this.ssoTicket = ssoTicket;
    }

    /**
     * Returns the host name for this tooling contract.
     *
     * @return Value exposed by the contract.
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * Updates the host name for this tooling contract.
     *
     * @param hostName Host name supplied by the caller.
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * Returns the port for this tooling contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPort() {
        return port;
    }

    /**
     * Updates the port for this tooling contract.
     *
     * @param port Port supplied by the caller.
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Returns the SSO ticket for this tooling contract.
     *
     * @return Value exposed by the contract.
     */
    public String getSsoTicket() {
        return ssoTicket;
    }

    /**
     * Updates the SSO ticket for this tooling contract.
     *
     * @param ssoTicket Sso ticket supplied by the caller.
     */
    public void setSsoTicket(String ssoTicket) {
        this.ssoTicket = ssoTicket;
    }
}
