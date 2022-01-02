package dev.elk.scaffold.plugin;

/**
 * An abstract event listener, that enables a class to wait for a
 * certain {@link dev.elk.scaffold.events.Event} to happen in the
 * pipeline, without being implicitly called.<p>
 * This is done by extending {@linkplain EventListener} and implementing
 * the method. If the method is not implemented, the function
 * defaults to nothing.<p>
 * Methods in {@linkplain EventListener} may not have a return value,
 * because all listeners are notified of an event.
 *
 * @author Louis Schell
 */
public abstract class EventListener{

}
