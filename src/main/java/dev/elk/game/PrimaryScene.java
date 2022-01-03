package dev.elk.game;

import dev.elk.scaffold.components.*;
import dev.elk.scaffold.gl.Quad;
import dev.elk.scaffold.gl.Square;
import dev.elk.scaffold.gl.Vertex;
import dev.elk.scaffold.renderer.*;
import org.joml.Math;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.awt.*;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * Test scene for stuff. Mainly first tests of OpenGL and textures and stuff.
 *
 *
 * @apiNote  Textures not 100% working yet, will fix later.
 * @author Louis Schell
 * @author Felix Kunze
 */
public class PrimaryScene extends Scene {

    private final ShaderProgram program;
    private Texture texture;
    
    private int vaoID;
    private int vboID;
    private int eboID;
    
    private final int[] elementArray = new int[20000];
    private final float[] vertices = new float[20000];
    public Quad quad;



    public PrimaryScene(ShaderProgram program){
        this.program = program;

    }

    @Override
    public void init() {
        try {
            texture = new Texture("Assets/PixelArt/Magu-final-1 - frame0001.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Sprite sprite = new Sprite(texture, new Vector2i(0,0), new Vector2i(0,0), "ericBlob");
        sprite.setUvCoords(new Vector2f[]{
                new Vector2f(0,0),
                new Vector2f(1,0),
                new Vector2f(1,1),
                new Vector2f(0,1)
        });

        quad =  new Square(sprite, new Vector2f(0.1f,0.1f), 3.0f);

        program.compile();
        program.use();



        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_DYNAMIC_DRAW);

        int posAttrib = glGetAttribLocation(program.getId(), "position");
        glEnableVertexAttribArray(posAttrib);
        glVertexAttribPointer(posAttrib, Vertex.POSITION_SIZE, GL_FLOAT, false, Vertex.STRIDE_BYTES, 0);

        int colAttrib = glGetAttribLocation(program.getId(), "texCoords");
        glEnableVertexAttribArray(colAttrib);
        glVertexAttribPointer(colAttrib, Vertex.UV_COORD_SIZE, GL_FLOAT, false, 5*Float.BYTES, Vertex.POSITION_SIZE_BYTES);

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementArray, GL_DYNAMIC_DRAW);
    }

    @Override
    public void onUpdate(float dt) {

        //quad.rotate(Math.toRadians(90f) * dt);
        System.arraycopy(quad.intoFloats(), 0, vertices, 0, quad.intoFloats().length);
        System.arraycopy(quad.getIndices(), 0, elementArray, 0, quad.getIndices().length);

        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, 0, elementArray);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);

        program.uploadTexture("TEX_SAMPLER", 0);
        glActiveTexture(GL_TEXTURE0);
        texture.bind();

        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

    }
}