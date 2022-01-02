package dev.elk.game;

import dev.elk.scaffold.components.*;
import dev.elk.scaffold.gl.Quad;
import dev.elk.scaffold.gl.Square;
import dev.elk.scaffold.renderer.*;
import org.joml.Math;
import org.joml.Vector2f;

import java.awt.*;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * Test scene for stuff. Mainly first tests of OpenGL
 * @author Louis Schell
 */
public class PrimaryScene extends Scene {

    private final ShaderProgram program;
    
    private int vaoID;
    private int vboID;
    private int eboID;
    
    private final int[] elementArray = new int[20000];
    private final float[] vertices = new float[20000];

    Quad quad = new Quad(
            new Vector2f(-0.3f, -0.5f),
            new Vector2f(0.3f, 0.5f),
            Color.green
    );

    Square sq = new Square(new Vector2f(0.2f,0.2f), 0.3f, Color.orange);

    public PrimaryScene(ShaderProgram program) {
        this.program = program;
    }

    @Override
    public void init() {
        program.compile();
        program.use();

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_DYNAMIC_DRAW);

        glBindFragDataLocation(program.getId(), 0, "outColor"); //Lol no clue what this does

        int posAttrib = glGetAttribLocation(program.getId(), "position");
        glEnableVertexAttribArray(posAttrib);
        glVertexAttribPointer(posAttrib, 2, GL_FLOAT, false, 5*Float.BYTES, 0);

        int colAttrib = glGetAttribLocation(program.getId(), "color");
        glEnableVertexAttribArray(colAttrib);
        glVertexAttribPointer(colAttrib, 3, GL_FLOAT, false, 5*Float.BYTES, 2*Float.BYTES);

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementArray, GL_DYNAMIC_DRAW);
    }

    @Override
    public void onUpdate(float dt) {

        quad.rotate(Math.toRadians(90f) * dt);
        sq.rotate(Math.toRadians(20f)*dt);

        System.arraycopy(sq.intoFloats(), 0, vertices, 0, sq.intoFloats().length);
        System.arraycopy(sq.getIndices(), 0, elementArray, 0, sq.getIndices().length);

        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, 0, elementArray);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

        /////////////////

        System.arraycopy(quad.intoFloats(), 0, vertices, 0, quad.intoFloats().length);
        System.arraycopy(quad.getIndices(), 0, elementArray, 0, quad.getIndices().length);

        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, 0, elementArray);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

    }
}