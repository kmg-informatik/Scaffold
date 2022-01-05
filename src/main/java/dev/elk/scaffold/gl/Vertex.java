package dev.elk.scaffold.gl;

import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * Vertices are blobs of data, that are used by OpenGL
 * to store information about each point of a geometry. <p>
 * Vertices are made up of floats but more generally every
 * type of data can be stored.
 * @apiNote  I have decided upon using POS_TEX as the final Layout. Fight me on that.
 * @author Louis Schell
 * @author Felix Kunze
 *
 */
public class Vertex implements FloatRepresentation {

    public Vector2f position;
    public Vector3f color;
    public Vector2f uvCoord;

    public static final int STRIDE = 7;
    public static final int BYTES = STRIDE * Float.BYTES;

    public static final int POSITION_SIZE = 2;
    public static final int POSITION_SIZE_BYTES = POSITION_SIZE * Float.BYTES;

    public static final int COLOR_SIZE = 3;
    public static final int COLOR_SIZE_BYTES = COLOR_SIZE * Float.BYTES;

    public static final int UV_COORD_SIZE = 2;
    public static final int UV_COORD_SIZE_BYTES = UV_COORD_SIZE * Float.BYTES;


    public Vertex(Vector2f position, Vector3f color, Vector2f uvCoord){
        this.position = position;
        this.color = color;
        this.uvCoord = uvCoord;
    }


    @Override
    public String toString() {
        return "Vertex{" +
                "position=" + position +
                ", color=" + color +
                ", uvCoord=" + uvCoord +
                '}';
    }

    @Override
    public float[] intoFloats() {
        return new float[]{position.x, position.y, color.x, color.y, color.z, uvCoord.x, uvCoord.y};
    }

    public static Vertex[] fromBuffer(float[] data){
        Vertex[] vertices = new Vertex[data.length / STRIDE];

        for (int i = 0; i < data.length / STRIDE; i++) {
            int k = i * STRIDE;

            vertices[i] = new Vertex(
                    new Vector2f(data[k++], data[k++]) ,
                    new Vector3f(data[k++], data[k++], data[k++]),
                    new Vector2f(data[k++], data[k]));
        }
        return  vertices;
    }


}
