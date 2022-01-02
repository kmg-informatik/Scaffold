package dev.elk.scaffold.gl;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.awt.*;
import java.util.ArrayList;

public class Quad implements Geometry{

    private final Vertex[] vertices = new Vertex[4];

    public Quad(Vector2f posLB, Vector2f posTR, Color color){

        Vector3f col = new Vector3f(color.getRGBColorComponents(null));

        Vertex bl = new Vertex(new Vector2f(posLB), col);
        Vertex br = new Vertex(new Vector2f(posTR.x, posLB.y), col);
        Vertex tr = new Vertex(new Vector2f(posTR), col);
        Vertex tl = new Vertex(new Vector2f(posLB.x, posTR.y), col);

        vertices[0] = bl;
        vertices[1] = br;
        vertices[2] = tr;
        vertices[3] = tl;

    }

    @Override
    public Vertex[] getVertices() {
        return vertices;
    }

    /**
     * Omg i am literally too dumb to do this without a
     * list halp
     */
    @Override
    public float[] intoFloats() {

        ArrayList<Float> floats = new ArrayList<>(20);

        for (Vertex vertex : vertices) {
            for (float aFloat : vertex.getFloats()) {
                floats.add(aFloat);
            }
        }

        float[] rFloats = new float[floats.size()];

        for (int i = 0; i < floats.size(); i++) {
            rFloats[i] = floats.get(i);
        }

        return rFloats;
    }

    @Override
    public int[] getIndices() {
        return new int[]{0,1,2,2,3,0};
    }
}
