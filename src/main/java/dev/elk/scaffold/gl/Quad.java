package dev.elk.scaffold.gl;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.awt.*;
import java.util.stream.IntStream;

/**
 * Gennerates a basic quad constructable either from Vertices or from Key points.
 * This allows for the construction of multiple geometric shapes that are made up of such quads
 * @author Felix Kunze
 * @author Louis Schell
 */
public class Quad implements Geometry{

    private final Vertex[] vertices = new Vertex[4];

    public Quad(Vector2f posLB, Vector2f posTR, Color color){

        Vector3f col = new Vector3f(color.getRGBColorComponents(null));

        vertices[0] = new Vertex(new Vector2f(posLB), col);
        vertices[1] = new Vertex(new Vector2f(posTR.x, posLB.y), col);
        vertices[2] = new Vertex(new Vector2f(posTR), col);
        vertices[3] = new Vertex(new Vector2f(posLB.x, posTR.y), col);

    }

    @Override
    public Vertex[] getVertices() {
        return vertices;
    }

    public float[] intoFloats(){
        //I know this is bodged, but I don't want to break colored vertices at the expense of textured ones
        int stride = vertices[0].getStride();

        float[] allVertexData = new float[vertices.length * stride];

        for (int i = 0; i < vertices.length; i++)
            System.arraycopy(vertices[i].intoFloats(), 0, allVertexData, stride * i, stride);

        return allVertexData;

    }

    /**
     * Returns the index representation of the triangles allowing for 4 Vertices to draw 2 Triangles.
     * @return The elements for the Element Array
     */
    @Override
    public int[] getIndices() {
        return new int[]{0,1,2,2,3,0};
    }


    @Override
    public float getHeight() {
        return vertices[0].position.y - vertices[2].position.y;
    }

    @Override
    public float getWidth() {
        return vertices[0].position.x - vertices[2].position.x;
    }

    @Override
    public Vector2f getPosition() {
        return vertices[0].position;
    }

    @Override
    public void moveTo(Vector2f bottomLeft) {
        vertices[0].position = bottomLeft;
        vertices[1].position = bottomLeft.add(0, getWidth());
        vertices[2].position = bottomLeft.add(getHeight(), 0);
        vertices[3].position = bottomLeft.sub(0, getWidth());
    }

    @Override
    public void moveBy(Vector2f mov) {
        IntStream.range(0, vertices.length).forEach(i -> vertices[i].position.add(mov));
    }
}
