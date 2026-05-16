package com.cometproject.storage.api.data.permissions.exceptions;

/**
 * Indicates that a requested permission resource does not exist.
 */
public final class PermissionNotFoundException extends RuntimeException {
    /**
     * Creates a missing permission resource exception.
     *
     * @param message the public exception message.
     */
    public PermissionNotFoundException(final String message) {
        super(message);
    }
}
