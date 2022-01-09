package dev.elk.scaffold.plugin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * A utility class for storing instances of {@link EventListener} and
 * notifying listeners of an event.<p>
 * Event listeners must be registered to the plugin repository, in order
 * for them to be notified of events. Listeners added more than once will
 * result in them being called more than once (This may be subject to change).
 * Note that the listeners are called in the order in which they were
 * registered to the plugin repository.
 *
 * @author Louis Schell
 * @apiNote Use a hashmap rather than an arraylist?
 * @see EventListener
 * @see dev.elk.scaffold.events.Event
 */
public class PluginRepository {

    private PluginRepository() {
    } //Disable instantiation

    /**
     * All {@link EventListener} that will be notified.
     */
    private static final ArrayList<EventListener> listeners = new ArrayList<>();

    /**
     * @return list of all subscribed listeners
     */
    public static ArrayList<EventListener> getListeners() {
        return listeners;
    }

    /**
     * Registers an {@link EventListener} to the PluginRepository, subscribing that listener
     * to all events.
     *
     * @param listener listener to be added
     * @return true, if the listener was added.
     */
    public static boolean addListener(EventListener listener) {
        return listeners.add(listener);
    }

    /**
     * Registers all given {@link EventListener} to the PluginRepository, subscribing those listeners
     * to all events.
     *
     * @param listeners listeners to be added
     * @return true, if at least one listener was added.
     */
    public static boolean addListeners(EventListener... listeners) {
        return PluginRepository.listeners.addAll(Arrays.asList(listeners));
    }

    /**
     * Instantiates and adds a listener
     *
     * @param listenerClass class of the listener to be added
     * @return true, if the listener was added.
     */
    public static boolean addListener(Class<? extends EventListener> listenerClass)
            throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        EventListener instance = listenerClass.getDeclaredConstructor().newInstance();
        return listeners.add(instance);
    }

    /**
     * Unsubscribes the specified listener from the plugin repository.
     *
     * @param listener listener to be removed
     * @return true, if the listeners contained the given listener
     */
    public static boolean deleteListener(EventListener listener) {
        return listeners.remove(listener);
    }

    /**
     * Notifies all listeners in {@link #listeners} of a specified event. In other words,
     * executes the given method for all listeners.
     *
     * @param consumer function to be called.
     */
    public static void notifyAllOf(Consumer<EventListener> consumer) {
        listeners.forEach(consumer);
    }

    static {
        PluginRepository.addListener(new GlListener());
    }

}