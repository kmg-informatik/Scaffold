package dev.elk.scaffold.gl;

import dev.elk.scaffold.renderer.Sprite;
import org.joml.Vector2f;

import java.awt.*;

public class Square extends Quad{

    public Square(Sprite sprite, Vector2f position, float size) {
        super(sprite, new Vector2f(position.sub(size, size)),
                new Vector2f(position.add(2*size, 2*size)));
    }

}
