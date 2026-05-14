package com.cometproject.server.utilities.attributes;

/**
 * Defines the attributable contract for the Comet subsystem.
 */
public interface Attributable {
    /**
     * Updates the attribute for this Comet contract.
     *
     * @param attributeKey Attribute key supplied by the caller.
     * @param attributeValue Attribute value supplied by the caller.
     */
    void setAttribute(String attributeKey, Object attributeValue);

    /**
     * Returns the attribute for this Comet contract.
     *
     * @param attributeKey Attribute key supplied by the caller.
     * @return Value exposed by the contract.
     */
    Object getAttribute(String attributeKey);

    /**
     * Executes has attribute for this Comet contract.
     *
     * @param attributeKey Attribute key supplied by the caller.
     * @return Value exposed by the contract.
     */
    boolean hasAttribute(String attributeKey);

    /**
     * Executes remove attribute for this Comet contract.
     *
     * @param attributeKey Attribute key supplied by the caller.
     */
    void removeAttribute(String attributeKey);
}
