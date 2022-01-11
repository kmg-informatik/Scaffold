package dev.elk.scaffold.Physics;

import dev.elk.game.Platform;
import dev.elk.scaffold.gl.Geometry;
import dev.elk.scaffold.gl.TexturedQuad;
import dev.elk.scaffold.gl.Window;
import org.joml.Vector2f;

import java.util.Arrays;

public interface Physics extends Geometry {

    float NORMAL_GRAVITY = -10f;

    float getCurrentGravity();
    void setCurrentGravity(float g);

    default void fall() {
        Geometry.super.translate(new Vector2f(0,getCurrentGravity()).mul(Window.dt));
        boolean intersects = false;
        for (Platform platform : Platform.platforms) {
            for(TexturedQuad quad: platform.getPlatformBase()) {
                if (intersects(quad)) {
                    intersects = true;
                    setCurrentGravity(0);

                    float threshold = 0.01f;
                    Vector2f move = new Vector2f(0, quad.getMaxY() - getMinY() - threshold);
                    Geometry.super.translate(move);
                }
            }
        }
        if(getCurrentGravity() > NORMAL_GRAVITY && !intersects)
            setCurrentGravity(getCurrentGravity() - 40f * Window.dt);
    }

    default boolean hasGroundContact(){
        for (Platform platform : Platform.platforms)
            for ( TexturedQuad quad : platform.getPlatformBase()) {
                if (intersects(quad)) return true;
            }
        return false;
    }

    default boolean intersects(Geometry geometry) {
        return this.getMinX() < geometry.getMaxX() &&
                this.getMaxX() > geometry.getMinX() &&
                this.getMaxY() > geometry.getMinY() &&
                this.getMinY() < geometry.getMaxY();
    }
}