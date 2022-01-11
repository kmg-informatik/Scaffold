package dev.elk.scaffold.Physics;

import dev.elk.scaffold.gl.Geometry;
import dev.elk.scaffold.gl.Window;
import org.joml.Vector2f;

import java.util.Arrays;

public interface Physics extends Geometry {

    float NORMAL_GRAVITY = -10f;

    float getCurrentGravity();
    void setCurrentGravity(float g);

    default void fall() {
        Geometry.super.translate(new Vector2f(0,getCurrentGravity()).mul(Window.dt));
        if (intersects(Ground.getQuads())) {
            setCurrentGravity(0);

            Vector2f move = new Vector2f(0, Ground.getFloorHeight() - getMinY());

            float threshold = 0.0f;
            if (move.length() >= threshold){
                Geometry.super.translate(move);
            }
        }
        else if(getCurrentGravity() > NORMAL_GRAVITY)
            setCurrentGravity(getCurrentGravity() - 40f * Window.dt);
    }

    default boolean hasGroundContact(){
        return intersects(Ground.getQuads());
    }

    default boolean intersects(Geometry... geometries) {
        if (geometries == null)
            return false;
        return Arrays.stream(geometries).anyMatch(geometry ->
                this.getMinX() < geometry.getMaxX() &&
                        this.getMaxX() > geometry.getMinX() &&
                        this.getMaxY() > geometry.getMinY() &&
                        this.getMinY() < geometry.getMaxY()
        );
    }
}