package dev.elk.scaffold.physics;

import dev.elk.scaffold.renderer.Sprite;
import org.joml.Vector2f;

public class PhysicsSquare extends PhysicsQuad {

    public PhysicsSquare(Sprite sprite, Vector2f position, float size) {
        super(sprite, new Vector2f(position),
                new Vector2f(new Vector2f(position).add(size, size))); //TODO should probably be new Vector2f(position).add instead
    }
}
