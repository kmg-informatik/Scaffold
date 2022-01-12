package dev.elk.game.scenes;

import dev.elk.game.Bird;
import dev.elk.game.fontSettings.Font;
import dev.elk.game.fontSettings.FontInformation;
import dev.elk.scaffold.components.Scene;
import dev.elk.scaffold.components.cameras.Camera;
import dev.elk.scaffold.components.cameras.FloatingCamera;
import dev.elk.scaffold.gl.Geometry;
import dev.elk.scaffold.gl.Window;
import dev.elk.scaffold.gl.bindings.ShaderProgram;
import dev.elk.scaffold.gl.bindings.Vertex;
import dev.elk.scaffold.renderer.Batch;
import dev.elk.scaffold.renderer.Text;
import dev.elk.scaffold.renderer.Texture;
import org.joml.Vector2f;

import java.awt.*;
import java.io.IOException;

import static dev.elk.game.spritesheetHandlers.SpritesheetBuilder.generateAllSpritesheets;

public class IntroScene extends Scene {

    private final ShaderProgram program;
    private final Batch<Geometry> dynamicBatch = new Batch<>(200, 20000, 7500);

    public IntroScene(Window window, ShaderProgram program) {
        super(window);
        this.program = program;
    }

    public void init() throws InstantiationException, IOException {

        window.windowColor = Color.BLACK;

        this.camera = new Camera(new Vector2f(), 1f);
        bufferInit(program, dynamicBatch);
        generateAllSpritesheets();
        Vertex.initAttributes(program);
        Texture.bindMultipleTextures();

        Text enter = new Text(
                new FontInformation(Font.COZETTE, 20f),
                new Vector2f(),
                "Press enter to play!"
        );

        Text exit = new Text(
                new FontInformation(Font.COZETTE, 20f),
                new Vector2f(),
                "Press k to exit!"
        );

        enter.translateCenterTo(new Vector2f(10f, 0));
        exit.translateCenterTo(new Vector2f(-10f, 0));

        dynamicBatch.putAll(enter, exit);
    }

    @Override
    public void update() {
        camera.adjustProjection();
        program.uploadMat4f("cameraProjection", camera.getProjectionMatrix());
        program.uploadMat4f("cameraView", camera.getViewMatrix());
        program.uploadFloat("windowStretch", Window.height / (float) Window.width);
        program.uploadTextures("texSamplers");
        dynamicBatch.render();
    }

    public boolean isFinished(){
        return window.currentFrameCount > 200;
    }
}
