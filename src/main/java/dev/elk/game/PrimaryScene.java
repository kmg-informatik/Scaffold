package dev.elk.game;

import dev.elk.game.fontSettings.Font;
import dev.elk.game.fontSettings.FontInformation;
import dev.elk.game.spritesheetHandlers.SpritesheetBuilder;
import dev.elk.game.spritesheetHandlers.SpritesheetInfo;
import dev.elk.scaffold.components.Scene;
import dev.elk.scaffold.components.cameras.Camera;
import dev.elk.scaffold.components.cameras.FloatingCamera;
import dev.elk.scaffold.gl.Geometry;
import dev.elk.scaffold.gl.Quad;
import dev.elk.scaffold.gl.Window;
import dev.elk.scaffold.gl.bindings.ShaderProgram;
import dev.elk.scaffold.gl.bindings.Vertex;
import dev.elk.scaffold.renderer.Batch;
import dev.elk.scaffold.renderer.Text;
import org.joml.Vector2f;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

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
        SpritesheetBuilder.generateSpriteSheets(SpritesheetInfo.COZETTE);

        text = new Text(
                new FontInformation(Font.COZETTE, 30),
                new Vector2f(10,10),
                " "
        );

        this.camera = new FloatingCamera(new Vector2f(), 20);

        program.uploadTexture("TEX_SAMPLER", 0);
        glActiveTexture(GL_TEXTURE0);
        text.getTexture().bind();

    }

    @Override
    public void update() {
        staticBatch.getGeometries().clear();
        text.setText(String.format("%.0f fps", 1/window.dt));
        System.out.println(Arrays.toString(text.getVertices()));
        staticBatch.put(text);

        camera.adjustProjection();
        //camera.position = camera.getNextPosition(quad.center());
        program.uploadMat4f("cameraProjection",camera.getProjectionMatrix());
        program.uploadMat4f("cameraView",camera.getViewMatrix());
        program.uploadFloat("windowStretch", window.height/(float) window.width);

        staticBatch.render();

    }
}