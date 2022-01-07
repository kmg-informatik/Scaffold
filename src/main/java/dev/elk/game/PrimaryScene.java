package dev.elk.game;

import dev.elk.scaffold.Physics.BoxCollider;
import dev.elk.scaffold.Physics.Ground;
import dev.elk.scaffold.Physics.SquareCollider;
import dev.elk.scaffold.components.KeyListener;
import dev.elk.scaffold.components.MeshRepository;
import dev.elk.scaffold.components.Scene;
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
    public BoxCollider obj1;

    public PrimaryScene(ShaderProgram program) {
        this.program = program;
    }

    @Override
    public void init() {

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        try {
            texture = new Texture("Assets/Spritesheets/tiles.png");
            spritesheet = Spritesheet.from(Paths.get("Assets/SpriteJson/tiles.json"), texture);

        } catch (IOException e) {
            e.printStackTrace();
        }
        obj1 = new SquareCollider(spritesheet.getSprite("marble"), new Vector2f(0f, 0.2f),  0.3f);

        ground = new Ground(0.1f, spritesheet.getSprite("dirtDark"), spritesheet.getSprite("dirtTop"),spritesheet.getSprite("grassTop"));
        MeshRepository.putAll(ground.getColliders());
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

    private int counter = 0;
    private long frame = 0;
    private boolean facingRight = true;
    private float movSpeed = 5f;
    private float gravity = -10f;

    @Override
    public void onUpdate(float dt) {
        frame++;
        if (obj1.hasCollision(ground.getColliders())) {
            counter = 1;
            gravity = 0;
        }

        else if(gravity > -10)  gravity--;
        if (KeyListener.isKeyPressed(KEY_W) | KeyListener.isKeyPressed(KEY_S)) {
            if (KeyListener.isKeyPressed(KEY_W) && obj1.hasCollision(ground.getColliders())) {
                gravity = 10f;
            } else {
                obj1.translate(new Vector2f(0, -movSpeed).mul(dt));
            }
        }
        if (KeyListener.isKeyPressed(KEY_A) | KeyListener.isKeyPressed(KEY_D)) {
            if (KeyListener.isKeyPressed(KEY_A)) {
                obj1.translate(new Vector2f(-movSpeed, 0.0f).mul(dt));
                if (facingRight) {
                    obj1.flipY();
                    facingRight = false;
                }
            } else {
                obj1.translate(new Vector2f(movSpeed, 0.0f).mul(dt));
                if (!facingRight) {
                    obj1.flipY();
                    facingRight = true;
                }
            }
        }

        if (KeyListener.isKeyPressed(KEY_R)) {
            obj1.rotate_origin(2f*dt);
        }
        if (KeyListener.isKeyPressed(KEY_SPACE)) {
            obj1.translateTo(new Vector2f(0,0));
        }



        //obj1.translate(new Vector2f(0, gravity ).mul(dt));

        if (counter %50 == 0) {
            System.out.println(gravity);
        }
        counter++;


        MeshRepository.update();

        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, 0, MeshRepository.getElementArray());
        glBufferSubData(GL_ARRAY_BUFFER, 0, MeshRepository.getVertexArray());

        glDrawElements(GL_TRIANGLES, MeshRepository.getElementArray().length, GL_UNSIGNED_INT, 0);
    }
}