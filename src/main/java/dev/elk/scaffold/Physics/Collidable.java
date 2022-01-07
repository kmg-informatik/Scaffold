package dev.elk.scaffold.Physics;

import dev.elk.scaffold.util.Utils;
import org.joml.Vector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * An interface giving physics attribute to certain things
 * @author Felix Kunze
 */
public interface Collidable {

    ArrayList<Collidable> COLLIDABLES = new ArrayList<>();

    static int getIntAccuracy(){
        return 100000;
    }

    Shape toShape();

    default boolean hasCollision(Collidable... tester){
        return Arrays.stream(tester).anyMatch(collidable -> Utils.collides(toShape(), collidable.toShape()));
    }

    default boolean hasCollision(){
        return COLLIDABLES.stream().anyMatch(collidable -> Utils.collides(toShape(), collidable.toShape()));
    }

    default ArrayList<Collidable> collidesWith() {
        return COLLIDABLES.stream().filter(this::hasCollision).collect(Collectors.toCollection(ArrayList::new));
    }

}
