package dev.elk.scaffold.components;

import dev.elk.scaffold.components.userinput.KeyListener;
import dev.elk.scaffold.components.userinput.MouseListener;
import dev.elk.scaffold.plugin.EventListener;
import dev.elk.scaffold.plugin.PluginRepository;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.windows.DISPLAY_DEVICE;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

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

    private int width;
    private int height;
    private final String title;
    private long glfwWindow;
    public static float dt;

    private Scene currentScene;

    public float r, g, b, a;

    public void setScene(Scene newScene) {
        this.currentScene = newScene;
    }

    public Window(String title, Color color) {
        this.title = title;

        //var screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
        //this.height = screenSize.getHeight();
        //this.width =screenSize.getWidth();
        this.r = color.getRed();
        this.b = color.getBlue();
        this.g = color.getGreen();
        this.a = color.getAlpha();
    }

    public void run() throws InstantiationException, IOException {
        this.init();
        currentScene.init();
        this.loop();

        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
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
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        glfwMakeContextCurrent(glfwWindow);
        glfwSwapInterval(1);
        glfwShowWindow(glfwWindow);
        PluginRepository.notifyAllOf(EventListener::onShowWindow);

        //This enables LWJGL to work with opengl
        GL.createCapabilities();
        PluginRepository.notifyAllOf(EventListener::onCreateGLCapabilities);
    }

    private void updateDimensions(){
        int[] windowWidth = new int[1];
        int[] windowHeight = new int[1];
        glfwGetWindowSize(glfwWindow, windowWidth, windowHeight);
        width = windowWidth[0];
        height = windowHeight[0];
    }

    public long getID() {
        return glfwWindow;
    }

    private void loop() {

        long start = System.nanoTime();
        dt = -1.0f;
        long end;

        while (!glfwWindowShouldClose(glfwWindow)) {
            updateDimensions();
            glfwPollEvents();
            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            if(dt >= 0)
                currentScene.onUpdate();

            glfwSwapBuffers(glfwWindow);
            end = System.nanoTime();
            dt = (float) ((end - start) * 1E-9);
            start = end;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}