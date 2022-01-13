package dev.elk.scaffold.components.player;

import dev.elk.scaffold.physics.PhysicsQuad;
import dev.elk.scaffold.gl.Component;
import dev.elk.scaffold.renderer.AnimatedSprite;
import dev.elk.scaffold.renderer.Sprite;
import org.joml.Vector2f;

/**
 * Abstract class for Player creation
 * @author Louis Schell
 * @author Felix Kunze
 * @since 11/01/2022
 */
public abstract class Entity extends PhysicsQuad implements Component {

    /**
     * @param posLB The Left Bottom position of the entiyy
     * @param size the size of the entity
     * @param sprite the Sprite
     */
    public Entity(Vector2f posLB, float size, Sprite sprite){
        super(posLB, size, sprite);
    }

    /**
     * Allows for Ai functionality
     * @return wether it is human controlled or Ai Controlled
     */
    abstract boolean isAi();

    /**
     * Returns the movement speed of the entity
     */
    abstract float getMovementSpeed();
}
