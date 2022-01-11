package dev.elk.scaffold.util;

import org.joml.Math;
import org.joml.Vector2f;

import java.awt.*;

/**
 * Helper class for varried methods, mostly in relation to Math
 *
 * @author Louis Schell
 * @author Felix Kunze
 */
public final class Utils {

    public static final int
            KEY_W = 87,
            KEY_A = 65,
            KEY_S = 83,
            KEY_D = 68,
            KEY_R = 82,
            KEY_TAB = 9,
            KEY_SPACE = 32;
    public static Color FALLBACK_COLOR = new Color(218, 58, 255);
    public static Vector2f INVALID_TEXTURE_COORDS = new Vector2f(-1, -1);

    public static void rotateVector(Vector2f point, Vector2f pivot, float angle) {
        float c = Math.cos(angle);
        float s = Math.sin(angle);

        //freeze pivot point
        Vector2f pivotCopy = new Vector2f(pivot);

        // translate point to origin:
        point.x -= pivotCopy.x;
        point.y -= pivotCopy.y;

        // rotate point
        float xnew = point.x * c - point.y * s;
        float ynew = point.x * s + point.y * c;

        // translate point back
        point.x = xnew + pivotCopy.x;
        point.y = ynew + pivotCopy.y;

    }

    public static float[] findMinMaxElem(float[] arr) {
        float max = Float.NEGATIVE_INFINITY;
        float min = Float.POSITIVE_INFINITY;

        for (float elem : arr) {
            max = java.lang.Math.max(elem, max);
            min = java.lang.Math.min(min, elem);
        }
        return new float[]{min, max};
    }

}
