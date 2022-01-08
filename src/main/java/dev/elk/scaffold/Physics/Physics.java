package dev.elk.scaffold.Physics;

import dev.elk.scaffold.components.Window;
import dev.elk.scaffold.gl.Geometry;
import org.joml.Vector2f;

public interface Physics extends Geometry {

    float NORMAL_GRAVITY = -10f;

    float getCurrentGravity();
    void setCurrentGravity(float g);

    default void fall() {
        System.out.println();
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
            setCurrentGravity(getCurrentGravity() - 1);
    }

    default boolean hasGroundContact(){
        return Ground.getFloorHeight() >= getMinY();
    }
}
