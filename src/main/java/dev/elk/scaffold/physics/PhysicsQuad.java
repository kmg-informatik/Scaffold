package dev.elk.scaffold.physics;

import dev.elk.scaffold.gl.TexturedQuad;
import dev.elk.scaffold.renderer.Sprite;
import org.joml.Vector2f;


/**
 * Implements a Quad with physical properties such as falling
 *
 * @author Felix Kunze
 */
public class PhysicsQuad extends TexturedQuad implements Physics {

    private float gravity = Physics.NORMAL_GRAVITY;

    public PhysicsQuad(Vector2f posLB, float size, Sprite sprite){
        super(posLB, size, sprite);
    }

    public PhysicsQuad(Sprite sprite, Vector2f posLB, float width, float height) {
        super(posLB, width, height, sprite);
    }

    public PhysicsQuad(Sprite sprite, Vector2f posLB, Vector2f posTR) {
        super(posLB, posTR, sprite);
    }

    @Override
    public float getCurrentGravity() {
        return gravity;
    }

    /**
     * Sets the current gravity to a new value,
     * this makes it {@link Physics#fall() fall} faster or slower.
     * @param g The new gravity
     */
    @Override
    public void setCurrentGravity(float g) {
        gravity = g;
    }
}