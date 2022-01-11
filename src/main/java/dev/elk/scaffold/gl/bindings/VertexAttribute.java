package dev.elk.scaffold.gl.bindings;

import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.*;

/**
 * OpenGL vertex attributes are descriptions of the structure of a single vertex.
 * They define what information goes where in the vertex, such as position,
 * color, or texture coordinates.
 *
 * @author Louis Schell
 * @see <a href="https://www.khronos.org/opengl/wiki/Vertex_Specification">https://www.khronos.org/opengl/wiki/Vertex_Specification</a>
 */
public class VertexAttribute {

    /**
     * Program associated with the Vertex
     */
    private final ShaderProgram program;

    /**
     * Name of the Attribute
     */
    private final String attribName;

    /**
     * Datatype of the attribute
     */
    private final int dataType;

    /**
     * Index of the beginning of the attribute in the vertex
     */
    private final int vertexPointer;

    /**
     * Size of the attribute. i.e. The amount of the datatype specified
     */
    private final int attribSize;

    /**
     * Gl Location of the attribute
     */
    private int attribLocation;

    public VertexAttribute(ShaderProgram program, String attribName, int dataType, int vertexPointer, int attribSize) {
        this.program = program;
        this.attribName = attribName;
        this.dataType = dataType;
        this.vertexPointer = vertexPointer;
        this.attribSize = attribSize;
    }

    /**
     * Creates vertex attribute in the shader program.
     */
    public void enable() {
        attribLocation = glGetAttribLocation(program.getId(), attribName);
        glEnableVertexAttribArray(attribLocation);
        glVertexAttribPointer(attribLocation, attribSize, dataType, false, Vertex.BYTES, vertexPointer);
    }

    /**
     * @return shader program of the vertex attribute
     */
    public ShaderProgram getProgram() {
        return program;
    }

    /**
     * @return name of the attribute
     */
    public String getAttribName() {
        return attribName;
    }

    /**
     * @return OpenGL datatype code
     */
    public int getDataType() {
        return dataType;
    }

    /**
     * @return index of the beginning of the attribute
     */
    public int getVertexPointer() {
        return vertexPointer;
    }

    /**
     * @return size of the attribute in count of the specified datatype
     */
    public int getAttribSize() {
        return attribSize;
    }

    /**
     * @return location of the attribute in OpenGL
     */
    public int getAttribLocation() {
        return attribLocation;
    }
}
