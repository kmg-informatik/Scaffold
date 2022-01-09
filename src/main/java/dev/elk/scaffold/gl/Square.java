package dev.elk.scaffold.gl;

import dev.elk.scaffold.renderer.Sprite;
import org.joml.Vector2f;

import java.awt.*;

/**
 * Constructs a square in positive x and y Direction from a bottomLeft point
 * @author Felix Kunze
 */
public class Square extends Quad{

    public Square(Sprite sprite, Vector2f position, float size) {
        super(sprite, new Vector2f(position),
                new Vector2f(new Vector2f(position).add(size, size)));
    }

}
