package dev.elk.game;

import dev.elk.game.structures.Pipe;
import dev.elk.scaffold.physics.CollidableStructure;
import dev.elk.scaffold.components.Scene;
import dev.elk.scaffold.components.cameras.FloatingCamera;
import dev.elk.scaffold.components.player.Player;
import dev.elk.scaffold.gl.Geometry;
import dev.elk.scaffold.gl.TexturedQuad;
import dev.elk.scaffold.gl.Window;
import dev.elk.scaffold.gl.bindings.ShaderProgram;
import dev.elk.scaffold.gl.bindings.Vertex;
import dev.elk.scaffold.renderer.*;
import org.joml.Vector2f;

import java.io.IOException;

import static dev.elk.game.spritesheetHandlers.SpritesheetBuilder.generateAllSpritesheets;

/**
 * Test scene for stuff. Mainly first tests of OpenGL and textures and stuff.
 *
 * @author Louis Schell
 * @author Felix Kunze
 */
public class PrimaryScene extends Scene {

    private final ShaderProgram program;
    private final Batch<Geometry> dynamicBatch = new Batch<>(2000, 200_000, 75_000);
    private final Batch<Geometry> staticBatch = new Batch<>(2000, 200_000, 75_000);
    private Player player;

    public PrimaryScene(Window window, ShaderProgram program) {
        super(window);
        this.program = program;
    }

    public void init() throws InstantiationException, IOException {

        bufferInit(program, dynamicBatch);
        generateAllSpritesheets();
        Vertex.initAttributes(program);

        this.camera = new FloatingCamera(new Vector2f(), 1f, 20);
        player = new Bird(new Vector2f(), 2);

                Pipe pipe = new Pipe(
                10,
                0,
                5);
        CollidableStructure.collidables.add(pipe);
        staticBatch.put(pipe);

        camera.parentTo(player);

        Texture.bindMultipleTextures();

    }

    int counter;
    @Override
    public void update() {
        dynamicBatch.getGeometries().clear();
        dynamicBatch.put(player);

        camera.position = new Vector2f(player.center().mul(Window.height / (float) Window.width).x, 0);

        player.update();

        camera.adjustProjection();
        program.uploadMat4f("cameraProjection", camera.getProjectionMatrix());
        program.uploadMat4f("cameraView", camera.getViewMatrix());
        program.uploadFloat("windowStretch", Window.height / (float) Window.width);
        program.uploadTextures("texSamplers");

        staticBatch.render();
        dynamicBatch.render();
    }
}