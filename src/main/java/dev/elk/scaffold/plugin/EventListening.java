package dev.elk.scaffold.plugin;

import dev.elk.scaffold.events.AudioEvent;

/**
 * An interface, that enables a class to wait for a
 * certain {@link dev.elk.scaffold.events.Event} to happen in the
 * pipeline, without being implicitly called.<p>
 * This is done by implementing {@linkplain EventListening} and overriding
 * the method. If the method is not implemented, the function
 * defaults to nothing the implementation in {@linkplain EventListening}.<p>
 * Methods in {@linkplain EventListening} may not have a return value,
 * because all listeners are notified of an event by a separate {@link PluginRepository}.
 *
 * @author Louis Schell
 */
public interface EventListening {
    // definiere onButtonClick, etc hier, ruf damit AudioPlayer auf

    void onJump(AudioEvent event);
}
