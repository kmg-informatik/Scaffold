package dev.elk.scaffold.plugin;

import dev.elk.scaffold.events.StartupEvent;
import dev.elk.scaffold.events.TextureLoadEvent;
import dev.elk.scaffold.events.CollisionEvent;

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

    public void onStartup(StartupEvent event){}

    public void onShutdown(){}

    public void onLoadTexture(TextureLoadEvent event){}

    public void onCollision(CollisionEvent event){}
}
