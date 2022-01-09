package dev.elk.scaffold.plugin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * A utility class for storing instances of {@link EventListener} and
 * notifying listeners of an event.<p>
 * Event listeners must be registered to the plugin repository, in order
 * for them to be notified of events. Listeners added more than once will
 * result in them being called more than once (This may be subject to change).
 * Note that the listeners are called in the order in which they were
 * registered to the plugin repository.
 * @apiNote Use a hashmap rather than an arraylist?
 * @see EventListener
 * @see dev.elk.scaffold.events.Event
 * @author Louis Schell
 */
public class PluginRepository {

    private PluginRepository(){}

    private static final ArrayList<EventListener> listeners = new ArrayList<>();

    public static ArrayList<EventListener> getListeners() {
        return listeners;
    }

    public static boolean addListener(EventListener listener){
        return listeners.add(listener);
    }

    public static boolean addListener(Class<? extends EventListener> listenerClass)
            throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        EventListener instance = listenerClass.getDeclaredConstructor().newInstance();
        return listeners.add(instance);
    }

    public static boolean deleteListener(EventListener listener){
        return listeners.remove(listener);
    }

    public static void notifyAllOf(Consumer<EventListener> consumer){
        listeners.forEach(consumer);
    }

    public static void notifyAllOfAsync(Consumer<EventListener> consumer){
        assert false : "Not implemented yet";
        Thread thread = new Thread(()->{
            try{
                listeners.forEach(consumer);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        thread.start();
    }

    static{
        PluginRepository.addListener(new GlListener());
    }

}