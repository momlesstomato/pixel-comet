package com.cometproject.server.modules.events;

import com.cometproject.api.commands.CommandInfo;
import com.cometproject.api.events.Cancellable;
import com.cometproject.api.events.Event;
import com.cometproject.api.events.EventHandler;
import com.cometproject.api.events.EventListenerContainer;
import com.cometproject.api.events.EventSubscribe;
import com.cometproject.api.networking.sessions.ISession;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

/**
 * Reflection-backed module event bus using concrete event payload classes.
 */
public class EventHandlerService implements EventHandler {
    private final ExecutorService asyncEventExecutor;
    private final Logger logger = LoggerFactory.getLogger(EventHandlerService.class.getName());

    private final Map<Class<? extends Event>, List<RegisteredEventListener>> listeners;

    private final Map<String, BiConsumer<ISession, String[]>> chatCommands;
    private final Map<String, CommandInfo> commandInfo;

    /**
     * Creates the event handler service.
     */
    public EventHandlerService() {
        this.asyncEventExecutor = Executors.newCachedThreadPool();

        this.listeners = Maps.newConcurrentMap();
        this.chatCommands = Maps.newConcurrentMap();
        this.commandInfo = Maps.newConcurrentMap();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize() {
        this.listeners.clear();
        this.chatCommands.clear();
        this.commandInfo.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerCommandInfo(final String commandName, final CommandInfo info) {
        this.commandInfo.put(commandName, info);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerChatCommand(final String commandExecutor, final BiConsumer<ISession, String[]> consumer) {
        this.chatCommands.put(commandExecutor, consumer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerListeners(final EventListenerContainer listenerContainer) {
        for (Method method : listenerContainer.getClass().getDeclaredMethods()) {
            final EventSubscribe subscription = method.getAnnotation(EventSubscribe.class);

            if (subscription == null) {
                continue;
            }

            this.registerListenerMethod(listenerContainer, method, subscription);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handleEvent(final Event event) {
        final List<RegisteredEventListener> registeredListeners = this.listeners.get(event.getClass());

        if (registeredListeners == null || registeredListeners.isEmpty()) {
            this.logger.debug("Unhandled event: {}", event.getClass().getSimpleName());
            return this.cancelled(event);
        }

        this.invoke(event, registeredListeners);
        this.logger.debug("Event handled: {}", event.getClass().getSimpleName());

        return this.cancelled(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, CommandInfo> getCommands() {
        return this.commandInfo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handleCommand(final ISession session, final String commandExectutor, final String[] arguments) {
        if (!this.chatCommands.containsKey(commandExectutor) || !this.commandInfo.containsKey(commandExectutor)) {
            return false;
        }

        final CommandInfo command = this.commandInfo.get(commandExectutor);

        if (!session.getPlayer().getPermissions().hasCommand(command.getPermission()) && !command.getPermission().isEmpty()) {
            return false;
        }

        final BiConsumer<ISession, String[]> chatCommand = this.chatCommands.get(commandExectutor);

        try {
            chatCommand.accept(session, arguments);
        } catch (Exception exception) {
            this.logger.warn("Failed to execute module command: {}", commandExectutor, exception);
        }

        return true;
    }

    @SuppressWarnings("unchecked") // Safe after Event.class assignability check.
    private void registerListenerMethod(
            final EventListenerContainer listenerContainer,
            final Method method,
            final EventSubscribe subscription) {
        if (method.getParameterCount() != 1) {
            throw new IllegalArgumentException("Event listener method must have exactly one event parameter: "
                    + method.getName());
        }

        final Class<?> parameterType = method.getParameterTypes()[0];
        if (!Event.class.isAssignableFrom(parameterType)) {
            throw new IllegalArgumentException("Event listener parameter must extend Event: " + method.getName());
        }

        method.setAccessible(true);
        final Class<? extends Event> eventClass = (Class<? extends Event>) parameterType;
        this.listeners.computeIfAbsent(eventClass, ignored -> new CopyOnWriteArrayList<>())
                .add(new RegisteredEventListener(listenerContainer, method, subscription.async()));

        this.logger.debug("Registered event listener for {}#{}({})",
                listenerContainer.getClass().getSimpleName(),
                method.getName(),
                eventClass.getSimpleName());
    }

    private void invoke(final Event event, final List<RegisteredEventListener> registeredListeners) {
        for (RegisteredEventListener listener : registeredListeners) {
            if (event.isAsync() || listener.async()) {
                this.asyncEventExecutor.submit(() -> this.invoke(listener, event));
            } else {
                this.invoke(listener, event);
            }
        }
    }

    private void invoke(final RegisteredEventListener listener, final Event event) {
        try {
            listener.method().invoke(listener.container(), event);
        } catch (IllegalAccessException exception) {
            this.logger.warn("Failed to access event listener for {}", event.getClass().getSimpleName(), exception);
        } catch (InvocationTargetException exception) {
            this.logger.warn("Event listener failed for {}", event.getClass().getSimpleName(), exception.getCause());
        }
    }

    private boolean cancelled(final Event event) {
        return event instanceof Cancellable cancellable && cancellable.isCancelled();
    }

    private record RegisteredEventListener(EventListenerContainer container, Method method, boolean async) {
    }
}
