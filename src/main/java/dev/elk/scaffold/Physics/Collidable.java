package dev.elk.scaffold.Physics;

import org.joml.Vector2f;

import java.awt.*;
import java.util.Arrays;

/**
 * An interface giving physics attribute to certain things
 * @author Felix Kunze
 */
public interface Collidable {

    default int getIntAccuracy(){
        return 1000;
    }

    Shape toShape();

    default boolean collidesWith(Collidable... tester){
        return Arrays.stream(tester).anyMatch(collidable -> toShape().getBounds2D().intersects(collidable.toShape().getBounds2D()));
    }

    Point pos2Point(Vector2f glPoint);



}
