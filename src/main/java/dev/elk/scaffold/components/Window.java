package dev.elk.scaffold.components;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.awt.*;

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

    private Scene currentScene;

    public float r, g, b, a;

    public void setScene(Scene newScene) {
        this.currentScene = newScene;
    }

    public Window(String title, int width, int height, Color color) {
        this.title = title;
        this.height = height;
        this.width = width;
        this.r = color.getRed();
        this.b = color.getBlue();
        this.g = color.getGreen();
        this.a = color.getAlpha();
    }

    public void run() {
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

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);

        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);  //Returns the mem adress of the window. (kinda like a pointer)
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

        //This enables LWJGL to work with opengl
        GL.createCapabilities();
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
        float dt = -1.0f;
        long end;

        while (!glfwWindowShouldClose(glfwWindow)) {
            updateDimensions();
            glfwPollEvents();
            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            if(dt >= 0)
                currentScene.onUpdate(dt);

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