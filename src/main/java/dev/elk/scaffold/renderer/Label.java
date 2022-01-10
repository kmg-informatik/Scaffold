package dev.elk.scaffold.renderer;

import dev.elk.scaffold.gl.Geometry;
import dev.elk.scaffold.gl.Vertex;
import org.joml.Vector2f;

import java.awt.*;

public class Label implements Geometry {

    private final Text text;

    public Label(Vector2f position, Text text){
        this(position, text, new Color(0,0,0,0));
    }

    public Label(Vector2f position, Text text, Color backgroundColor){
        this.text = text;
        text.setPosition(position);
    }

    @Override
    public int[] getIndices() {
        return text.getIndices();
    }

    @Override
    public Vertex[] getVertices() {
        return text.getVertices();
    }
}
