package com.cometproject.api.utilities;

/**
 * Represents a resource that can release any held state explicitly.
 */
public interface Disposable {
    /**
     * Releases any resources held by this instance.
     */
    void dispose();
}