package dev.elk.scaffold.Physics;

import dev.elk.scaffold.renderer.Sprite;
import org.joml.Vector2f;

public class SquareCollider extends BoxCollider{

    public SquareCollider(Sprite sprite, Vector2f position, float size) {
        super(sprite, new Vector2f(position),
                new Vector2f(position.add(size, size)));
    }
}
