package dev.elk.game;

import dev.elk.game.fontSettings.Font;
import dev.elk.game.fontSettings.FontInformation;
import dev.elk.game.spritesheetHandlers.SpritesheetInfo;
import dev.elk.scaffold.components.Scene;
import dev.elk.scaffold.components.cameras.FloatingCamera;
import dev.elk.scaffold.components.player.Player;
import dev.elk.scaffold.gl.Geometry;
import dev.elk.scaffold.gl.TexturedQuad;
import dev.elk.scaffold.gl.Window;
import dev.elk.scaffold.gl.bindings.ShaderProgram;
import dev.elk.scaffold.gl.bindings.Vertex;
import dev.elk.scaffold.renderer.Batch;
import dev.elk.scaffold.renderer.Spritesheet;
import dev.elk.scaffold.renderer.Text;
import dev.elk.scaffold.renderer.Texture;
import org.joml.Vector2f;

import java.io.IOException;
import java.util.ArrayList;

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
    private Player player;

    private final Batch<Geometry> dynamicBatch = new Batch<>(2000, 2_00_000, 75_000);
    private final Batch<Geometry> staticBatch = new Batch<>(2000, 2_00_000, 75_000);


    private TexturedQuad quad;
    private Text text;

    public PrimaryScene(Window window, ShaderProgram program) {
        super(window);
        this.program = program;
    }

    public void init() throws InstantiationException, IOException {

        bufferInit(program, dynamicBatch);
        generateAllSpritesheets();
        Vertex.initAttributes(program);

        generateSpritesheets(SpritesheetInfo.COZETTE);
        generateSpritesheets(SpritesheetInfo.TILES);
        generateSpritesheets(SpritesheetInfo.ANIMATIONS);

        this.camera = new FloatingCamera(new Vector2f(),1f, 20);
        player = new Player(Spritesheet.ANIMATED_SPRITES.get("maguWalk"),new Vector2f(10,10), new Vector2f(12,12));
        camera.parentTo(player);

        Platform.platforms.add(new Platform(new Vector2f(10,10)));
        Platform.platforms.add(new Platform(new Vector2f(30,10)));

        staticBatch.putAll(Platform.platforms.toArray(new Platform[0]));

        Texture.bindMultipleTextures();

    }

    @Override
    public void update() {
        dynamicBatch.getGeometries().clear();
        dynamicBatch.put(player);

        camera.position = player.center().mul(Window.height /(float) Window.width);

        player.update();

        camera.adjustProjection();
        program.uploadMat4f("cameraProjection",camera.getProjectionMatrix());
        program.uploadMat4f("cameraView",camera.getViewMatrix());
        program.uploadFloat("windowStretch", Window.height /(float) Window.width);
        program.uploadTextures("texSamplers");

        dynamicBatch.render();
        staticBatch.render();
    }
}