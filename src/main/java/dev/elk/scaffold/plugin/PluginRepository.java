package dev.elk.scaffold.plugin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * A utility class for storing instances of {@link EventListening} and
 * notifying listeners of an event.<p>
 * Event listeners must be registered to the plugin repository, in order
 * for them to be notified of events. Listeners added more than once will
 * result in them being called more than once (This may be subject to change).
 * Note that the listeners are called in the order in which they were
 * registered to the plugin repository.
 *
 * @author Louis Schell
 * @apiNote Use a hashmap rather than an arraylist?
 * @see EventListening
 * @see dev.elk.scaffold.events.Event
 */
public class PluginRepository {

    private static final ArrayList<EventListening> listeners = new ArrayList<>();

    static {
        addListener(AudioPlayer.getSingleton());
    }

    private PluginRepository() {
    }

    public static ArrayList<EventListening> getListeners() {
        return listeners;
    }

    public static boolean addListener(EventListening listener) {
        return listeners.add(listener);
    }

    public static boolean addListener(Class<? extends EventListening> listenerClass)
            throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        EventListening instance = listenerClass.getDeclaredConstructor().newInstance();
        return listeners.add(instance);
    }

    public static boolean deleteListener(EventListening listener) {
        return listeners.remove(listener);
    }

    public static void notifyAllOf(Consumer<EventListening> consumer) {
        listeners.forEach(consumer);
    }

    public static void notifyAllOfAsync(Consumer<EventListening> consumer) {
        assert false : "Not implemented yet";
        Thread thread = new Thread(() -> {
            try {
                listeners.forEach(consumer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

}