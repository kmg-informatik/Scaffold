package dev.elk.scaffold.gl;

import dev.elk.scaffold.renderer.Sprite;
import org.joml.Vector2f;

public class TexturedSquare extends TexturedQuad{

    public TexturedSquare(Vector2f position, float size, Sprite sprite) {
        super( new Vector2f(position),
                new Vector2f(position.add(size, size)),sprite);
    }
}
