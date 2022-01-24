package dev.elk.scaffold.components.userinput;

import java.util.HashSet;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * The Mouse-listener listens to actions taken by the mouse
 * such as button clicks or moving of the mouse.
 *
 * @author Louis Schell
 */
public class MouseListener {

    private static final HashSet<Integer> buttonsPressed = new HashSet<>(3);
    private static double scrollX, scrollY;
    private static double xPos, yPos, lastX, lastY;
    private static boolean isDragging;

    private MouseListener() {
    }

    /**
     * Updates the mousePosition
     * @param window The windowID
     * @param newXPos the new x pos
     * @param newYPos the new y pos
     */
    public static void mousePosCallback(long window, double newXPos, double newYPos) {
        lastX = xPos;
        lastY = yPos;
        xPos = newXPos;
        yPos = newYPos;
        isDragging = !buttonsPressed.isEmpty();
    }

    /**
     * Updates the mouse buttons
     * @param window The windowID
     * @param button The buttonID
     * @param action the action on the button
     * @param mods Possible button modifiers
     */
    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS) {
            buttonsPressed.add(button);
        } else if (action == GLFW_RELEASE) {
            buttonsPressed.remove(button);
            isDragging = false;
        }
    }

    /**
     * Updates the mouse scrolling
     * @param window The WindowID
     * @param xOffset The amount that was scrolled in x direction
     * @param yOffset The amount that was scrolled in y direction
     */
    public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        scrollX = xOffset;
        scrollY = yOffset;
    }

    public static void reset() {
        scrollX = 0;
        scrollY = 0;
        xPos = 0;
        yPos = 0;
        lastX = 0;
        lastY = 0;
        buttonsPressed.clear();
        isDragging = false;
    }

    public static boolean isPressed(int button) {
        return buttonsPressed.contains(button);
    }

    public static double getScrollX() {
        return scrollX;
    }

    public static double getScrollY() {
        return scrollY;
    }

    public static double getXPos() {
        return xPos;
    }

    public static double getYPos() {
        return yPos;
    }

    public static double getLastX() {
        return lastX;
    }

    public static double getLastY() {
        return lastY;
    }

    public static boolean isDragging() {
        return isDragging;
    }
}
