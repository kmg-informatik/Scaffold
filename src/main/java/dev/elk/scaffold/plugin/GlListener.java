package dev.elk.scaffold.plugin;

import dev.elk.scaffold.gl.Vertex;
import dev.elk.scaffold.gl.bindings.VertexAttribute;
import dev.elk.scaffold.renderer.ShaderProgram;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class GlListener extends EventListener{

    @Override
    public void onDefineBuffers(ShaderProgram program) {

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
    }
}
