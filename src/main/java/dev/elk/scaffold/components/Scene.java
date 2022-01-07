package dev.elk.scaffold.components;

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

    public void init() {}

    public abstract void onUpdate(float dt);


}
