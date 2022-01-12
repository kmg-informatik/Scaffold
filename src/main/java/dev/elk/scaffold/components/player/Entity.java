package dev.elk.scaffold.components.player;

import dev.elk.scaffold.physics.PhysicsQuad;
import dev.elk.scaffold.gl.Component;
import dev.elk.scaffold.renderer.AnimatedSprite;
import dev.elk.scaffold.renderer.Sprite;
import org.joml.Vector2f;

/**
 * @author Louis Schell
 * @since 11/01/2022
 */
public abstract class Entity extends PhysicsQuad implements Component {

    public Entity(Vector2f posLB, float size, Sprite sprite){
        super(posLB, size, sprite);
    }

    abstract boolean isAi();

    abstract float getMovementSpeed();
}
