package dev.elk.scaffold.util;

import org.joml.Math;
import org.joml.Vector2f;

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

}
