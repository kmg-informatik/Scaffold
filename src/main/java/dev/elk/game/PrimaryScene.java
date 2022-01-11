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
import dev.elk.scaffold.renderer.Batch;
import dev.elk.scaffold.renderer.Spritesheet;
import dev.elk.scaffold.renderer.Text;
import dev.elk.scaffold.renderer.Texture;
import org.joml.Vector2f;

import java.io.IOException;

import static dev.elk.game.spritesheetHandlers.SpritesheetBuilder.*;

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

    public void init() throws InstantiationException, IOException {
        bufferInit(program, dynamicBatch);

        generateAllSpritesheets();



        this.camera = new FloatingCamera(new Vector2f(), 20);

        Platform platform = new Platform(new Vector2f(10,10));
        staticBatch.put(platform);
        Texture.bindMultipleTextures();

    }

    @Override
    public void update() {
        dynamicBatch.getGeometries().clear();

        camera.adjustProjection();
        //camera.position = camera.getNextPosition(quad.center());
        program.uploadMat4f("cameraProjection",camera.getProjectionMatrix());
        program.uploadMat4f("cameraView",camera.getViewMatrix());
        program.uploadFloat("windowStretch", window.height/(float) window.width);
        program.uploadTextures("texSamplers");

        dynamicBatch.render();
        staticBatch.render();
    }
}