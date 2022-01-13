package dev.elk.scaffold.events;

/**
 * A data class, that can be used to wrap information on a specific event.
 *
 * @author Louis Schell
 * @see dev.elk.scaffold.plugin.EventListening
 */
public abstract class Event {

    public String getName() {
        return getClass().getName();
    }

}