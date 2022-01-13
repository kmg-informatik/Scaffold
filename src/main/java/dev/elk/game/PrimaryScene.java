package dev.elk.game;

import dev.elk.game.fontSettings.Font;
import dev.elk.game.fontSettings.FontInformation;
import dev.elk.game.structures.Chunk;
import dev.elk.game.structures.ChunkGenerator;
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

import java.awt.*;
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
    private Bird player;
    private Text pipeCount;
    private Text playerDied;

    private final ChunkGenerator chunkGenerator = new ChunkGenerator();

    public PrimaryScene(Window window, ShaderProgram program) {
        super(window);
        this.program = program;
    }

    public void init() throws InstantiationException, IOException {

        bufferInit(program, dynamicBatch);
        generateAllSpritesheets();
        chunkGenerator.init();
        Vertex.initAttributes(program);

        pipeCount = new Text(
                new FontInformation(Font.COZETTE, 70f),
                new Vector2f(),
                "0");

        playerDied = new Text(
                new FontInformation(Font.COZETTE, 50f),
                new Vector2f(),
                "You died!");

        this.camera = new FloatingCamera(new Vector2f(), 0.75f, 20);
        player = new Bird(new Vector2f(30,10), 2);

        camera.parentTo(player);

        Texture.bindMultipleTextures();

    }

    private float opacity;

    @Override
    public void update() {
        dynamicBatch.getGeometries().clear();
        dynamicBatch.put(player);

        camera.position = new Vector2f(player.center().mul(Window.height / (float) Window.width).x, 0);

        player.update();
        chunkGenerator.needsChunk(player.getPosition().x);
        dynamicBatch.put(chunkGenerator);

        pipeCount.setText(Integer.toString(player.getPipesPassed()));
        pipeCount.translateCenterTo(new Vector2f(player.getPosition().x, player.getPosition().y+3f));
        dynamicBatch.put(pipeCount);

        playerDied.translateCenterTo(new Vector2f(player.getPosition().x, player.getPosition().y+30f));
        dynamicBatch.put(playerDied);

        camera.adjustProjection();
        program.uploadMat4f("cameraProjection", camera.getProjectionMatrix());
        program.uploadMat4f("cameraView", camera.getViewMatrix());
        program.uploadFloat("windowStretch", Window.height / (float) Window.width);
        program.uploadTextures("texSamplers");
        program.uploadFloat("fScreenAlpha", opacity);

        if (player.isDead()){
            opacity+=0.03f;
        }

        staticBatch.render();
        dynamicBatch.render();
    }

    public boolean isDone(){
        return opacity >=1;
    }
}