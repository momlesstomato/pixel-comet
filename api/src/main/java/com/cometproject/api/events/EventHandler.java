package com.cometproject.api.events;


import com.cometproject.api.commands.CommandInfo;
import com.cometproject.api.networking.sessions.ISession;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Publishes module events and module-provided chat command callbacks.
 */
public interface EventHandler  {
   /**
    * Clears registered listeners and command callbacks.
    */
   void initialize();

   /**
    * Publishes an event to registered listeners.
    *
    * @param eventClass the event listener class to publish.
    * @param args       the event arguments.
    * @param <T>        the event argument type.
    * @return true when a synchronous listener cancelled the event.
    */
   <T extends EventArgs> boolean handleEvent(Class<? extends Event> eventClass, T args);

   /**
    * Registers a module event listener.
    *
    * @param consumer the listener wrapper.
    */
   void registerEvent(Event consumer);

   /**
    * Registers a module chat command callback.
    *
    * @param commandExecutor the command name.
    * @param consumer        the command callback.
    */
   void registerChatCommand(String commandExecutor, BiConsumer<ISession, String[]> consumer);

   /**
    * Registers metadata for a module chat command.
    *
    * @param commandName the command name.
    * @param info        the command metadata.
    */
   void registerCommandInfo(String commandName, CommandInfo info);

   /**
    * Returns registered module command metadata.
    *
    * @return command metadata keyed by command name.
    */
   Map<String, CommandInfo> getCommands();

   /**
    * Executes a module chat command when the session has permission.
    *
    * @param session          the invoking session.
    * @param commandExectutor the command name.
    * @param arguments        the command arguments.
    * @return true when a command handled the request.
    */
   boolean handleCommand(ISession session, String commandExectutor, String[] arguments);
}
