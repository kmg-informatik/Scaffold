package dev.elk.game;

import dev.elk.scaffold.components.*;
import dev.elk.scaffold.gl.Quad;
import dev.elk.scaffold.gl.Vertex;
import dev.elk.scaffold.renderer.*;
import org.joml.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

import static dev.elk.scaffold.util.Utils.*;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * Test scene for stuff. Mainly first tests of OpenGL and textures and stuff.
 *
 * @author Louis Schell
 * @author Felix Kunze
 */
public class PrimaryScene extends Scene {

    private final ShaderProgram program;
    private Spritesheet spritesheet;
    private AnimatedSpritesheet animatedSpritesheet;
    private Texture texture;

    private int vaoID;
    private int vboID;
    private int eboID;

    public Quad quad;

    public PrimaryScene(ShaderProgram program) {
        this.program = program;
    }

    @Override
    public void init() {

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        try {
            texture = new Texture("Assets/PixelArt/pixelfiles/magu-linksbundig-26pxwidth.png");
            animatedSpritesheet= AnimatedSpritesheet.from(Paths.get("Assets/SpriteJson/animationTest.json"), texture);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(animatedSpritesheet.getSprites().get(0).toString());

        quad = new Quad(animatedSpritesheet.getSprites().get(0), new Vector2f(0f, 0f),  new Vector2f(0.255f,0.5f));

        MeshRepository.put(quad);

        program.compile();
        program.use();

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, MeshRepository.getVertexArray(), GL_DYNAMIC_DRAW);

        int posAttrib = glGetAttribLocation(program.getId(), "position");
        glEnableVertexAttribArray(posAttrib);
        glVertexAttribPointer(posAttrib, Vertex.POSITION_SIZE, GL_FLOAT, false, Vertex.BYTES, 0);

        int colAttrib = glGetAttribLocation(program.getId(), "texCoords");
        glEnableVertexAttribArray(colAttrib);
        glVertexAttribPointer(colAttrib, Vertex.UV_COORD_SIZE, GL_FLOAT, false, Vertex.BYTES, Vertex.POSITION_SIZE_BYTES);

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, MeshRepository.getElementArray(), GL_DYNAMIC_DRAW);

        program.uploadTexture("TEX_SAMPLER", 0);
        glActiveTexture(GL_TEXTURE0);
        texture.bind();
    }

    int counter = 0;
    long frame = 0;
    boolean facingRight = true;

    @Override
    public void onUpdate(float dt) {
        frame++;

        float movSpeed = 1f;

        if (KeyListener.isKeyPressed(KEY_W) | KeyListener.isKeyPressed(KEY_S)) {
            if (KeyListener.isKeyPressed(KEY_W)){
                quad.translate(new Vector2f(0, movSpeed).mul(dt));
            }else{
                quad.translate(new Vector2f(0, -movSpeed).mul(dt));
            }
        }
        if (KeyListener.isKeyPressed(KEY_A) | KeyListener.isKeyPressed(KEY_D)) {
            counter %= 5;
            if (counter == 0){
                animatedSpritesheet.getSprites().get(0).nextFrame();
                quad.updateTexCoords();
            }
            counter++;

            if (KeyListener.isKeyPressed(KEY_A)){
                quad.translate(new Vector2f(-movSpeed, 0.0f).mul(dt));
                if (facingRight) {
                    quad.flipY();
                    facingRight = false;
                }
            }else{
                quad.translate(new Vector2f(movSpeed, 0.0f).mul(dt));
                if (!facingRight){
                    quad.flipY();
                    facingRight = true;
                }
            }

        }
        if (KeyListener.isKeyPressed(KEY_R)) {
            quad.rotate_origin(2f*dt);
        }
        if (KeyListener.isKeyPressed(KEY_SPACE)) {
            quad.translateTo(new Vector2f());
        }

        MeshRepository.update();

        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, 0, MeshRepository.getElementArray());
        glBufferSubData(GL_ARRAY_BUFFER, 0, MeshRepository.getVertexArray());

        glDrawElements(GL_TRIANGLES, 12, GL_UNSIGNED_INT, 0);

    }
}