package dev.elk.game;

import dev.elk.scaffold.Physics.Ground;
import dev.elk.scaffold.Physics.PhysicsQuad;
import dev.elk.scaffold.Physics.PhysicsSquare;
import dev.elk.scaffold.components.*;
import dev.elk.scaffold.components.cameras.FloatingCamera;
import dev.elk.scaffold.gl.Vertex;
import dev.elk.scaffold.renderer.ShaderProgram;
import dev.elk.scaffold.renderer.Sprite;
import dev.elk.scaffold.renderer.Spritesheet;
import dev.elk.scaffold.renderer.Texture;
import org.joml.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

import static dev.elk.scaffold.util.Utils.*;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * Test scene for stuff. Mainly first tests of OpenGL and textures and stuff.
 *
 * @author Louis Schell
 * @author Felix Kunze
 */
public class PrimaryScene extends Scene {

    private final ShaderProgram program;
    private Spritesheet<Sprite> spritesheet = new Spritesheet<>();
    private Texture texture;

    private int vaoID;
    private int vboID;
    private int eboID;

    public Ground ground;
    public PhysicsQuad obj1;

    public PrimaryScene(Window window, ShaderProgram program) {
        super(window);
        this.program = program;
    }

    @Override
    public void init() {
        this.camera = new FloatingCamera(new Vector2f(), 2f,50);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        try {
            texture = new Texture("Assets/Spritesheets/tiles.png");
            spritesheet = Spritesheet.from(Paths.get("Assets/SpriteJson/tiles.json"), texture);

        } catch (IOException e) {
            e.printStackTrace();
        }
        obj1 = new PhysicsSquare(spritesheet.getSprite("marble"), new Vector2f(0f, 0.2f),  0.3f);

        Sprite[] groundLevels = {
                spritesheet.getSprite("dirtDark"),
                spritesheet.getSprite("dirtTop"),
                spritesheet.getSprite("grassTop")
        };

        Ground.buildGround(0.1f, groundLevels);
        MeshRepository.putAll(Ground.getQuads());
        MeshRepository.put(obj1);

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

        int colAttrib = glGetAttribLocation(program.getId(), "color");
        glEnableVertexAttribArray(colAttrib);
        glVertexAttribPointer(colAttrib, Vertex.COLOR_SIZE, GL_FLOAT, false, Vertex.BYTES, Vertex.POSITION_SIZE_BYTES);

        int texAttrib = glGetAttribLocation(program.getId(), "texCoords");
        glEnableVertexAttribArray(texAttrib);
        glVertexAttribPointer(texAttrib, Vertex.UV_COORD_SIZE, GL_FLOAT, false, Vertex.BYTES, Vertex.POSITION_SIZE_BYTES + Vertex.COLOR_SIZE_BYTES);

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, MeshRepository.getElementArray(), GL_DYNAMIC_DRAW);

        program.uploadTexture("TEX_SAMPLER", 0);
        glActiveTexture(GL_TEXTURE0);
        texture.bind();

    }

    private boolean facingRight = true;

    @Override
    public void onUpdate() {
        float movSpeed = 1f;
        float windowStretch = (float) window.getHeight() / (float) window.getWidth();
        program.uploadFloat("windowStretch", windowStretch);

        obj1.fall();

        Vector2f movementVector = new Vector2f();
        if (KeyListener.isKeyPressed(KEY_W) | KeyListener.isKeyPressed(KEY_S)) {
            if (KeyListener.isKeyPressed(KEY_W) && obj1.hasGroundContact()) {
                obj1.setCurrentGravity(10);
            } else {
                obj1.translate(new Vector2f(0, -movSpeed).mul(Window.dt));
            }
        }

        if (KeyListener.isKeyPressed(KEY_A) | KeyListener.isKeyPressed(KEY_D)) {
            if (KeyListener.isKeyPressed(KEY_A)) {
                obj1.translate(new Vector2f(-movSpeed, 0.0f).mul(Window.dt));
                movementVector.add(new Vector2f(-movSpeed, 0.0f).mul(Window.dt));
                if (facingRight) {
                    obj1.flipY(obj1.centerOfMass());
                    facingRight = false;
                }
            } else {
                movementVector.add(new Vector2f(movSpeed, 0.0f).mul(Window.dt));
                if (!facingRight) {
                    obj1.flipY(obj1.centerOfMass());
                    facingRight = true;
                }
            }
        }
        if (KeyListener.isKeyPressed(KEY_SPACE)) {
            movementVector = new Vector2f(obj1.getOrigin()).negate();
        }
        obj1.translate(movementVector);

        camera.position = camera.getNextPosition(obj1.centerOfMass().mul(windowStretch));
        program.uploadFloat("windowStretch", windowStretch);
        program.uploadMat4f("cameraProjection",camera.getProjectionMatrix());
        program.uploadMat4f("cameraView",camera.getViewMatrix());

        MeshRepository.update(windowStretch);
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, 0, MeshRepository.getElementArray());
        glBufferSubData(GL_ARRAY_BUFFER, 0, MeshRepository.getVertexArray());
        glDrawElements(GL_TRIANGLES, MeshRepository.vertexCount(), GL_UNSIGNED_INT, 0);

    }
}