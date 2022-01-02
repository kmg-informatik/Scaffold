package dev.elk.scaffold.gl;

import org.joml.Vector2f;

import java.awt.*;

public class Square extends Quad{

    public Square(Vector2f position, float size, Color color) {
        super(new Vector2f(position.sub(size, size)),
                new Vector2f(position.add(2*size, 2*size)), color);
    }
}
