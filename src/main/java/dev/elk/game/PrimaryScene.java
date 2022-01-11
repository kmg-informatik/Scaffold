package dev.elk.game;

import dev.elk.game.fontSettings.Font;
import dev.elk.game.fontSettings.FontInformation;
import dev.elk.game.spritesheetHandlers.SpritesheetInfo;
import dev.elk.scaffold.components.Scene;
import dev.elk.scaffold.components.cameras.FloatingCamera;
import dev.elk.scaffold.gl.Geometry;
import dev.elk.scaffold.gl.TexturedQuad;
import dev.elk.scaffold.gl.Window;
import dev.elk.scaffold.gl.bindings.ShaderProgram;
import dev.elk.scaffold.gl.bindings.Vertex;
import dev.elk.scaffold.renderer.Batch;
import dev.elk.scaffold.renderer.Spritesheet;
import dev.elk.scaffold.renderer.Text;
import org.joml.Vector2f;

import java.io.IOException;

import static dev.elk.game.spritesheetHandlers.SpritesheetBuilder.*;
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

    private final Batch<Geometry> dynamicBatch = new Batch<>(2000, 2_000_000, 750_000);
    private final Batch<Geometry> staticBatch = new Batch<>(2000, 2_000_000, 750_000);

    private TexturedQuad quad;
    private Text text;

    public PrimaryScene(Window window, ShaderProgram program) {
        super(window);
        this.program = program;
    }

    @Override
    public void init() throws InstantiationException, IOException {
        {
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
        generateSpritesheets(SpritesheetInfo.COZETTE);
        generateSpritesheets(SpritesheetInfo.TILES);


        text = new Text(
                new FontInformation(Font.COZETTE, 30),
                new Vector2f(10,10),
                "abb"
        );

        this.camera = new FloatingCamera(new Vector2f(), 20);

        quad = new TexturedQuad(new Vector2f(0,0),new Vector2f(10,10),Spritesheet.STATIC_SPRITES.get("marble"));
        staticBatch.put(quad);
        staticBatch.put(text);
        System.out.println(text.getTexture().getTexID());
        System.out.println(quad.getSprite().getTexture().getTexID());
        System.out.println();
        System.out.println(TEXTURES.get(0).getTexID());
        System.out.println(TEXTURES.get(1).getTexID());

        for (int i = 0; i < TEXTURES.size(); i++) {
            glActiveTexture(GL_TEXTURE0 + i);
            TEXTURES.get(i).bind();
        }
        program.uploadTextures("texSamplers");

    }

    @Override
    public void update() {
        dynamicBatch.getGeometries().clear();
        //text.setText(String.format("%.0f fps", window.avgFps()));
        //dynamicBatch.put(text);

        camera.adjustProjection();
        //camera.position = camera.getNextPosition(quad.center());
        program.uploadMat4f("cameraProjection",camera.getProjectionMatrix());
        program.uploadMat4f("cameraView",camera.getViewMatrix());
        program.uploadFloat("windowStretch", window.height/(float) window.width);

        dynamicBatch.render();
        staticBatch.render();
    }
}