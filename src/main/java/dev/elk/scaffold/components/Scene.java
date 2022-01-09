package dev.elk.scaffold.components;

import dev.elk.scaffold.components.cameras.Camera;

import java.io.IOException;

/**
 * Copied directly from GamesWithGabe.
 * Probably should rewrite, for better control of
 * all the buffers. They are the same for every Scene.
 */
public abstract class Scene {

    protected Camera camera;
    private boolean isRunning;
    protected Window window;

    public Scene(Window window) {
        this.window = window;
    }

    public void init() throws InstantiationException, IOException {}

    public abstract void onUpdate();


}
