package dev.elk.scaffold.gl;

import dev.elk.scaffold.renderer.Sprite;
import org.joml.Vector2f;

import java.util.stream.IntStream;

/**
 * Gennerates a basic quad constructable either from Vertices or from Key points.
 * This allows for the construction of multiple geometric shapes that are made up of such quads
 * @author Felix Kunze
 * @author Louis Schell
 */
public class Quad implements Geometry{

    private final Vertex[] vertices = new Vertex[4];

    public Quad(Sprite sprite, Vertex[] vertices){

    }

    public Quad(Sprite sprite, Vector2f posLB, float width, float height){
        vertices[0] = new Vertex(new Vector2f(posLB), sprite.getUvCoords()[0]);
        vertices[1] = new Vertex(posLB.add(width, 0), sprite.getUvCoords()[1]);
        vertices[2] = new Vertex(posLB.add(0, height), sprite.getUvCoords()[2]);
        vertices[3] = new Vertex(posLB.add(width, height), sprite.getUvCoords()[3]);
    }

    public Quad(Sprite sprite, Vector2f posLB, Vector2f posTR){

        vertices[0] = new Vertex(new Vector2f(posLB), sprite.getUvCoords()[0]);
        vertices[1] = new Vertex(new Vector2f(posTR.x, posLB.y), sprite.getUvCoords()[1]);
        vertices[2] = new Vertex(new Vector2f(posTR), sprite.getUvCoords()[2]);
        vertices[3] = new Vertex(new Vector2f(posLB.x, posTR.y), sprite.getUvCoords()[3]);
    }

    @Override
    public Vertex[] getVertices() {
        return vertices;
    }

    public float[] intoFloats(){
        //I know this is bodged, but I don't want to break colored vertices at the expense of textured ones
        float[] allVertexData = new float[vertices.length * Vertex.STRIDE];

        for (int i = 0; i < vertices.length; i++)
            System.arraycopy(vertices[i].intoFloats(), 0, allVertexData, Vertex.STRIDE * i, Vertex.STRIDE);

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
    public Vector2f getOrigin() {
        return vertices[0].position;
    }
}
