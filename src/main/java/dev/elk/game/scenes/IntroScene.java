package dev.elk.game.scenes;

import dev.elk.game.Bird;
import dev.elk.scaffold.components.Scene;
import dev.elk.scaffold.components.cameras.Camera;
import dev.elk.scaffold.components.cameras.FloatingCamera;
import dev.elk.scaffold.gl.Geometry;
import dev.elk.scaffold.gl.Window;
import dev.elk.scaffold.gl.bindings.ShaderProgram;
import dev.elk.scaffold.gl.bindings.Vertex;
import dev.elk.scaffold.renderer.Batch;
import dev.elk.scaffold.renderer.Texture;
import org.joml.Vector2f;

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
        this.camera = new Camera(new Vector2f(), 1f);

        bufferInit(program, dynamicBatch);
        generateAllSpritesheets();
        Vertex.initAttributes(program);

        Texture.bindMultipleTextures();

    }

    @Override
    public void update() {
        System.out.println("Updating lol");
    }

    public boolean isFinished(){
        return window.getCurrentFrameCount() > 500;
    }
}
