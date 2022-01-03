package dev.elk.scaffold.gl;

import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * Vertices are blobs of data, that are used by OpenGL
 * to store information about each point of a geometry. <p>
 * Vertices are made up of floats but more generally every
 * type of data can be stored.
 * @apiNote Once all is finished decide upon one vertexLayout. maybe.
 * @author Louis Schell
 * @author Felix Kunze
 *
 */
public class Vertex implements Floatrepresentation{

    public Vector2f position;
    public Vector3f color;
    public Vector2f uvCoord;
    private final VertexLayout vertexLayout;

    final public static int positionSize = 2;
    final public static int positionSizeBytes = positionSize * Float.BYTES;

    final public static int uvCoordSize = 2;
    final public static int uvCoordSizeBytes = uvCoordSize * Float.BYTES;

    final public static int colorSize = 3;
    final public static int colorSizeBytes = colorSize * Float.BYTES;

    public Vertex(Vector2f position, Vector3f rgb){
        vertexLayout = VertexLayout.POS_COLOR;
        this.color = rgb;
        this.position = position;
    }

    public Vertex(Vector2f position, Vector3f rgb, Vector2f uvCoord){
        vertexLayout = VertexLayout.POS_COLOR_TEX;
        this.color = rgb;
        this.position = position;
        this.uvCoord = uvCoord;
    }

    public Vertex(Vector2f position, Vector2f uvCoord){
        vertexLayout = VertexLayout.POS_TEX;
        this.position = position;
        this.uvCoord = uvCoord;
    }


    @Override
    public String toString() {
        return switch (vertexLayout){
            case POS_COLOR -> "Vertex{" +
                    "position=" + position +
                    ", color=" + color +
                    '}';
            case POS_TEX -> "Vertex{" +
                    "position=" + position +
                    ", uvCoords=" + uvCoord +
                    '}';
            case POS_COLOR_TEX -> "Vertex{" +
                    "position=" + position +
                    ", color=" + color +
                    ", uvCoords=" + uvCoord +
                    '}';
        };
    }

    @Override
    public float[] intoFloats() {
        return switch (vertexLayout){
            case POS_COLOR -> new float[]{position.x, position.y, color.x, color.y, color.z};
            case POS_TEX -> new float[]{position.x, position.y, uvCoord.x, uvCoord.y};
            case POS_COLOR_TEX -> new float[]{position.x, position.y, color.x, color.y, color.z, uvCoord.x, uvCoord.y};
        };
    }

    public static Vertex[] fromBuffer(float[] data, VertexLayout vertexLayout){
        Vertex[] vertices = new Vertex[4];
        int stride = switch (vertexLayout){
            case POS_TEX -> 4;
            case POS_COLOR -> 5;
            case POS_COLOR_TEX -> 7;
        };

        for (int i = 0; i < data.length / stride; i++) {
            vertices[i] = switch (vertexLayout){
                case POS_TEX -> new Vertex(
                        new Vector2f(data[i * stride], data[1 + i * stride]) ,
                        new Vector2f(data[2 + i * stride], data[3 + i * stride]));
                case POS_COLOR -> new Vertex(
                        new Vector2f(data[i * stride], data[1 + i * stride]) ,
                        new Vector3f(data[2 + i * stride], data[3 + i * stride], data[4 + i* stride]));
                case POS_COLOR_TEX -> new Vertex(
                        new Vector2f(data[i * stride], data[1 + i * stride]) ,
                        new Vector3f(data[2 + i * stride], data[3 + i * stride], data[4 + i* stride]),
                        new Vector2f(data[5+i*stride],data[6+i*stride]));
            };
        }
        return  vertices;
    }

    public int getStride() {
        return switch (vertexLayout){
            case POS_TEX -> 4;
            case POS_COLOR -> 5;
            case POS_COLOR_TEX -> 7;
        };
    }

    enum VertexLayout{
        POS_COLOR_TEX,
        POS_COLOR,
        POS_TEX
    }


}
