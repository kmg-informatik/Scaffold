package dev.elk.scaffold.gl;

import dev.elk.scaffold.components.GamePipeline;
import dev.elk.scaffold.components.PipelineNode;
import dev.elk.scaffold.components.userinput.KeyHandler;
import dev.elk.scaffold.components.userinput.MouseListener;
import dev.elk.scaffold.plugin.EventListening;
import dev.elk.scaffold.plugin.PluginRepository;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.awt.*;
import java.util.LinkedList;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * The window containing the running game. Originally copied
 * from GamesWithGame.
 * @author Louis Schell
 * @author Felix Kunze
 */
public class Window {

    public static int width;
    public static int height;
    public String title;
    public long glfwWindow;
    public Color windowColor;
    public boolean gameShouldEnd;
    public static float dt;
    public long currentFrameCount;

    private final LinkedList<Float> fps = new LinkedList<>();

    private GamePipeline pipeline;

    public Window(String title, Color windowColor) {
        this.title = title;
        this.windowColor = windowColor;
    }

    public void setPipeline(GamePipeline pipeline) {
        this.pipeline = pipeline;
    }

    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize glfw");
        }

        long primary = glfwGetPrimaryMonitor();
        var videoMode = glfwGetVideoMode(primary);

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);

        glfwWindow = glfwCreateWindow(videoMode.width(), videoMode.width(), this.title, NULL, NULL);  //Returns the mem adress of the window. (kinda like a pointer)
        if (glfwWindow == NULL) {
            throw new IllegalStateException("failed to create window");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyHandler::keyCallback);

        glfwMakeContextCurrent(glfwWindow);
        glfwSwapInterval(1);
        glfwShowWindow(glfwWindow);
        PluginRepository.notifyAllOf(EventListening::onGameStart);

        //This enables LWJGL to work with opengl
        GL.createCapabilities();
        for (int i = 0; i < 20; i++) {
            fps.add(0f);
        }
    }

    public void loop() throws Exception {

        pipeline.getCurrentNode().init();

        long start = System.nanoTime();
        dt = 1/60f;
        long end;

        while (!glfwWindowShouldClose(glfwWindow) && !gameShouldEnd) {
            updateDimensions();
            fps.removeFirst();
            fps.add(1f/dt);

            glfwPollEvents();
            glClearColor(windowColor.getRed(),
                    windowColor.getGreen(),
                    windowColor.getBlue(),
                    windowColor.getAlpha());
            glClear(GL_COLOR_BUFFER_BIT);

            pipeline.getCurrentNode().getScene().update();
            pipeline.next();

            glfwSwapBuffers(glfwWindow);
            end = System.nanoTime();
            dt = (float) ((end - start) * 1E-9);
            start = end;
            currentFrameCount++;
        }
    }

    public void destroy(){
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void updateDimensions(){
        int[] windowWidth = new int[1];
        int[] windowHeight = new int[1];
        glfwGetWindowSize(glfwWindow, windowWidth, windowHeight);
        width = windowWidth[0];
        height = windowHeight[0];
    }

    public float avgFps(){
        float total = 0;
        for (float floatingFP : fps) {
            total+=floatingFP;
        }
        total/=20;
        return total;
    }

    public void setGameShouldEnd(boolean gameShouldEnd) {
        this.gameShouldEnd = gameShouldEnd;
    }

    public long getCurrentFrameCount() {
        return currentFrameCount;
    }

    public GamePipeline getPipeline() {
        return pipeline;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getID() {
        return glfwWindow;
    }
}