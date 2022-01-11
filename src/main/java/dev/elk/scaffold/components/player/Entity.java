package dev.elk.scaffold.components.player;

import dev.elk.scaffold.Physics.PhysicsQuad;
import dev.elk.scaffold.gl.Component;
import dev.elk.scaffold.renderer.AnimatedSprite;
import org.joml.Vector2f;

/**
 * @author Louis Schell
 * @since 11/01/2022
 */
public abstract class Entity extends PhysicsQuad implements Component {

    public Entity(AnimatedSprite sprite, Vector2f lb, Vector2f tr){
        super(sprite, lb, tr);
    }

    abstract boolean isAi();

    abstract float getMovementSpeed();
}
