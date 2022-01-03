package dev.elk.scaffold.util;

import org.joml.Math;
import org.joml.Vector2f;

/**
 * Helper class for varried methods, mostly in relation to Math
 * @author Louis Schell
 * @author Felix Kunze
 */
public final class Utils {

    public static void rotate(Vector2f point, Vector2f pivPoint, float angle){
        float c = Math.cos(angle);
        float s = Math.sin(angle);

        // translate point back to origin:
        point.sub(pivPoint);

        // rotate point
        float xnew = point.x * c - point.y * s;
        float ynew = point.x * s + point.y * c;

        point.x = xnew + pivPoint.x;
        point.y = ynew + pivPoint.y;

    }

    public static float[] findMinMaxElem(float[] arr){
        float max = Float.MIN_VALUE;
        float min = Float.MAX_VALUE;

        for (float elem : arr) {
            max = java.lang.Math.max(elem, max);
            min = java.lang.Math.min(min, elem);
        }
        return new float[]{min,max};
    }

}
