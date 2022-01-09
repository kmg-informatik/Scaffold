package dev.elk.scaffold.gl.bindings;

import dev.elk.scaffold.gl.Vertex;
import dev.elk.scaffold.renderer.ShaderProgram;

import static org.lwjgl.opengl.GL20.*;

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
    private Integer attribLocation;

    public VertexAttribute(ShaderProgram program, String attribName, int dataType, int vertexPointer, int attribSize) {
        this.program = program;
        this.attribName = attribName;
        this.dataType = dataType;
        this.vertexPointer = vertexPointer;
        this.attribSize = attribSize;
    }

    /**
     * Creates attribute in Gl
     */
    public void enable(){
        attribLocation = glGetAttribLocation(program.getId(), attribName);
        glEnableVertexAttribArray(attribLocation);
        glVertexAttribPointer(attribLocation, attribSize, dataType,false, Vertex.BYTES, vertexPointer);
    }

    public ShaderProgram getProgram() {
        return program;
    }

    public String getAttribName() {
        return attribName;
    }

    public int getDataType() {
        return dataType;
    }

    public int getVertexPointer() {
        return vertexPointer;
    }

    public int getAttribSize() {
        return attribSize;
    }

    public Integer getAttribLocation() {
        return attribLocation;
    }
}
