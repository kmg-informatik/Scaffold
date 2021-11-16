package dev.elk.scaffold.events;

/**
 * A data class, that can be used to wrap information on a specific event.
 * @see dev.elk.scaffold.plugin.EventListener
 * @author Louis Schell
 */
public abstract class Event {

    public String getName(){
        return getClass().getName();
    }

}