package dev.elk.scaffold.components.userinput;

import java.util.HashSet;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * The Key-Listener listens to button-presses on the keyboard.
 * @author Louis Schell
 */
public class KeyHandler {
    private static final HashSet<Integer> keyPressed = new HashSet<>();

    private KeyHandler() {
    }

    /**
     * Checks for keypresses
     * @param window The windowID
     * @param key The Keycode
     * @param scancode the scancode
     * @param action the action that was done
     * @param mods possible modifiers
     */
    public static void keyCallback(long window, int key, int scancode, int action, int mods) {
        if (action == GLFW_PRESS) {
            keyPressed.add(key);
        } else if (action == GLFW_RELEASE) {
            keyPressed.remove(key);
        }
    }

    public static boolean isKeyPressed(int keyCode) {
        return keyPressed.contains(keyCode);
    }

}