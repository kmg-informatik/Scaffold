package dev.elk.scaffold.gl;

import dev.elk.scaffold.renderer.Sprite;
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
    private final Sprite sprite;


    public Quad(Sprite sprite, Vector2f posLB, float width, float height){
        this.sprite = sprite;
        vertices[0] = new Vertex(new Vector2f(posLB), new Vector3f(), sprite.getUvCoords()[0]);
        vertices[1] = new Vertex(posLB.add(width, 0), new Vector3f(), sprite.getUvCoords()[1]);
        vertices[2] = new Vertex(posLB.add(0, height),new Vector3f(),  sprite.getUvCoords()[2]);
        vertices[3] = new Vertex(posLB.add(width, height),new Vector3f(),  sprite.getUvCoords()[3]);
    }

    public Quad(Sprite sprite, Vector2f posLB, Vector2f posTR){
        Color color = Color.WHITE;
        Vector3f colorVec = new Vector3f(color.getRGBColorComponents(null));
        this.sprite = sprite;
        vertices[0] = new Vertex(new Vector2f(posLB),colorVec,  sprite.getUvCoords()[0]);
        vertices[1] = new Vertex(new Vector2f(posTR.x, posLB.y), colorVec, sprite.getUvCoords()[1]);
        vertices[2] = new Vertex(new Vector2f(posTR), colorVec, sprite.getUvCoords()[2]);
        vertices[3] = new Vertex(new Vector2f(posLB.x, posTR.y),colorVec,  sprite.getUvCoords()[3]);
    }

    public Quad(Sprite sprite, Vector2f posLB, Vector2f posTR, Color color) {
        Vector3f colorVec = new Vector3f(color.getRGBColorComponents(null));
        this.sprite = sprite;
        vertices[0] = new Vertex(new Vector2f(posLB),colorVec,  sprite.getUvCoords()[0]);
        vertices[1] = new Vertex(new Vector2f(posTR.x, posLB.y), colorVec, sprite.getUvCoords()[1]);
        vertices[2] = new Vertex(new Vector2f(posTR), colorVec, sprite.getUvCoords()[2]);
        vertices[3] = new Vertex(new Vector2f(posLB.x, posTR.y),colorVec,  sprite.getUvCoords()[3]);
    }

    public void updateTexCoords(Sprite sprite){
        for (int i = 0; i < vertices.length; i++) {
            vertices[i].uvCoord = sprite.getUvCoords()[i];
        }
    }

    public void updateTexCoords(){
        for (int i = 0; i < vertices.length; i++) {
            vertices[i].uvCoord = this.sprite.getUvCoords()[i];
        }
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

    public Sprite getSprite() {
        return sprite;
    }
}
