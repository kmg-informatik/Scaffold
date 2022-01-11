package dev.elk.scaffold.components;

import dev.elk.scaffold.components.cameras.Camera;
import dev.elk.scaffold.gl.Geometry;
import dev.elk.scaffold.gl.Window;
import dev.elk.scaffold.gl.bindings.ShaderProgram;
import dev.elk.scaffold.gl.bindings.Vertex;
import dev.elk.scaffold.renderer.Batch;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * Altered version of the Scene by GamesWithGabe
 * @author Louis Schell
 * @author Felix Kunze
 */
public abstract class Scene {

    protected Camera camera;
    protected final Window window;

    public Scene(Window window) {
        this.window = window;
    }

    public void init() throws InstantiationException, IOException {}

    public abstract void update();

    protected static void bufferInit(ShaderProgram program, Batch<Geometry> dynamicBatch) throws InstantiationException {
        program.compile();
        program.use();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        int vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        int vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, dynamicBatch.getVertexArray(), GL_DYNAMIC_DRAW);

        int eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, dynamicBatch.getElementArray(), GL_DYNAMIC_DRAW);

        Vertex.initAttributes(program);
    }


}
