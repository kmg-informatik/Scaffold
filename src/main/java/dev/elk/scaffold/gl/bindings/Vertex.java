package dev.elk.scaffold.gl.bindings;

import dev.elk.scaffold.gl.FloatRepresentation;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_SHORT;

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
public class Vertex implements FloatRepresentation, Cloneable {

    public Vector2f position;
    public Vector3f color;
    public Vector2f uvCoord;
    public int texId;

    public static final int STRIDE = 8;
    public static final int BYTES = STRIDE * Float.BYTES;

    public static final int POSITION_SIZE = 2;
    public static final int POSITION_SIZE_BYTES = POSITION_SIZE * Float.BYTES;

    public static final int COLOR_SIZE = 3;
    public static final int COLOR_SIZE_BYTES = COLOR_SIZE * Float.BYTES;

    public static final int UV_COORD_SIZE = 2;
    public static final int UV_COORD_SIZE_BYTES = UV_COORD_SIZE * Float.BYTES;

    public static final int TEX_ID_SIZE = 1;
    public static final int TEX_ID_SIZE_BYTES = TEX_ID_SIZE * Integer.BYTES;


    public Vertex(Vector2f position, Vector3f color, Vector2f uvCoord, int texId){
        this.position = position;
        this.color = color;
        this.uvCoord = uvCoord;
        this.texId = texId;
    }

    public static void initAttributes(ShaderProgram program){
        VertexAttribute positionAttrib = new VertexAttribute(
                program,
                "position",
                GL_FLOAT,
                0,
                Vertex.POSITION_SIZE
        );
        positionAttrib.enable();

        VertexAttribute colorAttrib = new VertexAttribute(
                program,
                "color",
                GL_FLOAT,
                Vertex.POSITION_SIZE_BYTES,
                Vertex.COLOR_SIZE
        );
        colorAttrib.enable();

        VertexAttribute textureAttrib = new VertexAttribute(
                program,
                "texCoords",
                GL_FLOAT,
                Vertex.POSITION_SIZE_BYTES + Vertex.COLOR_SIZE_BYTES,
                Vertex.UV_COORD_SIZE
        );
        textureAttrib.enable();

        VertexAttribute textureIdAttribute = new VertexAttribute(
                program,
                "texId",
                GL_SHORT,
                Vertex.POSITION_SIZE_BYTES + Vertex.COLOR_SIZE_BYTES + Vertex.UV_COORD_SIZE_BYTES,
                Vertex.TEX_ID_SIZE
        );
        textureIdAttribute.enable();
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
        return new float[]{position.x, position.y, color.x, color.y, color.z, uvCoord.x, uvCoord.y, texId};
    }

    @Override
    public Vertex clone() {
        try {
            Vertex clone = (Vertex) super.clone();
            clone.position = new Vector2f(position);
            clone.color = new Vector3f(color);
            clone.uvCoord = new Vector2f(uvCoord);
            clone.texId = texId;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}