package dev.elk.game.scenes;

import dev.elk.game.Bird;
import dev.elk.game.fontSettings.Font;
import dev.elk.game.fontSettings.FontInformation;
import dev.elk.game.structures.ChunkGenerator;
import dev.elk.scaffold.components.userinput.KeyHandler;
import dev.elk.scaffold.physics.CollidableStructure;
import dev.elk.scaffold.components.Scene;
import dev.elk.scaffold.components.cameras.FloatingCamera;
import dev.elk.scaffold.gl.Geometry;
import dev.elk.scaffold.gl.Window;
import dev.elk.scaffold.gl.bindings.ShaderProgram;
import dev.elk.scaffold.gl.bindings.Vertex;
import dev.elk.scaffold.renderer.*;
import dev.elk.scaffold.util.Utils;
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
    private final Batch<Geometry> dynamicBatch = new Batch<>(2000, 20_000, 7500);
    private final Batch<Geometry> staticBatch = new Batch<>(2000, 20_000, 7500);
    private Bird player;
    private Text pipeCount;
    private Text diedMessage;
    private Text toolTip;
    private Text howTo;
    private Text welcome;
    private Text happyBirb;
    private ChunkGenerator chunkGenerator;

    public void startOpenGL() throws InstantiationException, IOException {
        bufferInit(program, dynamicBatch);
        generateAllSpritesheets();
    }

    public PrimaryScene(Window window, ShaderProgram program) {
        super(window);
        this.program = program;
    }

    public void init() throws InstantiationException, IOException {
        chunkGenerator = new ChunkGenerator();
        chunkGenerator.init();

        Vertex.initAttributes(program);

        welcome = new Text(
                new FontInformation(Font.COZETTE, 15f),
                new Vector2f(),
                "Welcome to");


        happyBirb = new Text(
                new FontInformation(Font.COZETTE, 50f),
                new Vector2f(),
                " Happy Birb!");

        pipeCount = new Text(
                new FontInformation(Font.COZETTE, 15f),
                new Vector2f(),
                "0");

        diedMessage = new Text(
                new FontInformation(Font.COZETTE, 50f),
                new Vector2f(),
                "You died!");

        toolTip = new Text(
                new FontInformation(Font.COZETTE, 15f),
                new Vector2f(),
                "Press <SPACE> to restart!  ");

        howTo = new Text(
                new FontInformation(Font.COZETTE, 15f),
                new Vector2f(),
                "Press w to start the game!");

        this.camera = new FloatingCamera(new Vector2f(), 0.75f, 20);
        player = new Bird(new Vector2f(30,0), 2);

        camera.parentTo(player);

        Texture.bindMultipleTextures();

    }

    private boolean gameStarted;

    @Override
    public void update() {
        dynamicBatch.getGeometries().clear();

        camera.position = new Vector2f(player.center().mul(Window.height / (float) Window.width).x, 0);
        chunkGenerator.needsChunk(player.getPosition().x);
        diedMessage.translateCenterTo(new Vector2f(player.getPosition().x, player.getPosition().y+31f));
        toolTip.translateCenterTo(new Vector2f(player.getPosition().x, player.getPosition().y+28f));

        dynamicBatch.put(chunkGenerator);
        dynamicBatch.put(diedMessage);
        dynamicBatch.put(toolTip);
        dynamicBatch.put(howTo);
        dynamicBatch.put(player);

        if (!gameStarted){
            dynamicBatch.put(happyBirb);
            dynamicBatch.put(welcome);
            welcome.translateCenterTo(new Vector2f(player.getPosition().x, player.getPosition().y-5f));
            happyBirb.translateCenterTo(new Vector2f(player.getPosition().x, player.getPosition().y-9f));

            howTo.translateCenterTo(new Vector2f(player.getPosition().x, player.getPosition().y+3f));
            if (KeyHandler.isKeyPressed(Utils.KEY_W))
                gameStarted = true;
        }else {
            player.update();
            pipeCount.setText("Highscore: " + player.getPipesPassed());
            pipeCount.translateOriginTo(new Vector2f(player.getPosition().x-27f, 13f));
            dynamicBatch.put(pipeCount);
        }

        camera.adjustProjection();
        program.uploadMat4f("cameraProjection", camera.getProjectionMatrix());
        program.uploadMat4f("cameraView", camera.getViewMatrix());
        program.uploadFloat("windowStretch", Window.height / (float) Window.width);
        program.uploadTextures("texSamplers");

        staticBatch.render();
        dynamicBatch.render();
        sceneFrameCount++;
    }

    @Override
    public void reset() {
        this.dynamicBatch.getGeometries().clear();
        this.staticBatch.getGeometries().clear();
        this.player = null;
        this.chunkGenerator = null;
        this.sceneFrameCount = 0;
        this.camera = null;
        this.gameStarted = false;
        CollidableStructure.collidables.clear();

    }

    public boolean isDone(){
        return player.isDead() && (KeyHandler.isKeyPressed(Utils.KEY_SPACE));
    }
}