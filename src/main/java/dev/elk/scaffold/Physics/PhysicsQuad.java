package dev.elk.scaffold.Physics;

import dev.elk.scaffold.gl.Quad;
import dev.elk.scaffold.gl.TexturedQuad;
import dev.elk.scaffold.renderer.Sprite;
import org.joml.Vector2f;


/**
 * Implements a Quad with physical properties such as falling
 * @author Felix Kunze
 */
public class PhysicsQuad extends TexturedQuad implements Physics {

    public PhysicsQuad(Sprite sprite, Vector2f posLB, float width, float height){
        super(posLB, width, height,sprite);
    }

    public PhysicsQuad(Sprite sprite, Vector2f posLB, Vector2f posTR){
        super(posLB, posTR, sprite);
    }

    private float gravity = Physics.NORMAL_GRAVITY;

    @Override
    public float getCurrentGravity() {
        return gravity;
    }

    @Override
    public void setCurrentGravity(float g) {
        gravity = g;
    }
}