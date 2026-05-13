package com.cometproject.api.events;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method as a module event listener.
 *
 * <p>Listener methods must accept exactly one parameter that extends {@link Event}
 * and should return {@code void}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventSubscribe {
    /**
     * Indicates whether this listener may run on the async event executor.
     *
     * @return true when this listener can run asynchronously.
     */
    boolean async() default false;
}
