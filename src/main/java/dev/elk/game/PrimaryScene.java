package dev.elk.game;

import dev.elk.game.fontSettings.FontInformation;
import dev.elk.game.fontSettings.FontType;
import dev.elk.scaffold.components.Scene;
import dev.elk.scaffold.components.Window;
import dev.elk.scaffold.components.cameras.FloatingCamera;
import dev.elk.scaffold.physics.PhysicsQuad;
import dev.elk.scaffold.plugin.PluginRepository;
import dev.elk.scaffold.renderer.*;
import org.joml.Vector2f;

import java.awt.*;
import java.io.IOException;

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
    private final Spritesheet<Sprite> spritesheet = new Spritesheet<>();

    private int vaoID;
    private int vboID;
    private int eboID;

    private final Batch staticBatch = new Batch(2000, 200000, 75000);
    private final Batch dynamicBatch = new Batch(2000, 200000, 75000);

    public PhysicsQuad obj1;
    public Text text;

    public PrimaryScene(Window window, ShaderProgram program) {
        super(window);
        this.program = program;
    }

    @Override
    public void init() throws InstantiationException, IOException {
        {
            program.compile();
            program.use();

            vaoID = glGenVertexArrays();
            glBindVertexArray(vaoID);

            vboID = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, vboID);
            glBufferData(GL_ARRAY_BUFFER, dynamicBatch.getVertexArray(), GL_DYNAMIC_DRAW);

            eboID = glGenBuffers();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, dynamicBatch.getElementArray(), GL_DYNAMIC_DRAW);

            PluginRepository.notifyAllOf(e->e.onDefineBuffers(program));
        }

        this.camera = new FloatingCamera(new Vector2f(), 1f,50);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        /*
        try {
            spritesheet = Spritesheet.from(Paths.get("Assets/SpriteJson/tiles.json"), new Texture(Paths.get("Assets/Spritesheets/tiles.png")));

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

        staticBatch.putAll(Ground.getQuads());
        dynamicBatch.put(obj1);

         */
        text = new Text(
                    new FontInformation(FontType.COZETTE,16),
                    Color.WHITE,
                    new Vector2f(0,0),
                6
            );
        dynamicBatch.putAll(text.getQuads());

        program.uploadTexture("TEX_SAMPLER", 0);
        glActiveTexture(GL_TEXTURE0);
        text.getSpritesheet().getTexture().bind();

    }

    private boolean facingRight = true;

    @Override
    public void onUpdate() {
        text.setText(Integer.toString((int)(1f/Window.dt)));
        float movSpeed = 1f;
        float windowStretch = (float) window.getHeight() / (float) window.getWidth();
        program.uploadFloat("windowStretch", windowStretch);

        /*
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

         */

        //camera.position = camera.getNextPosition(obj1.centerOfMass().mul(windowStretch));
        program.uploadFloat("windowStretch", windowStretch);
        program.uploadMat4f("cameraProjection",camera.getProjectionMatrix());
        program.uploadMat4f("cameraView",camera.getViewMatrix());

        staticBatch.render();
        dynamicBatch.render();
    }
}