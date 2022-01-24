package dev.elk.scaffold.physics;

import dev.elk.scaffold.gl.Geometry;
import dev.elk.scaffold.gl.Quad;
import dev.elk.scaffold.gl.Window;
import org.joml.Vector2f;

/**
 * Physics Interface that allows Geometries to fall and to collide
 * @author Felix Kunze
 * @author Louis Schell
 */
public interface Physics extends Geometry {

    float NORMAL_GRAVITY = -10f;

    float getCurrentGravity();

    void setCurrentGravity(float g);

    /**
     * Allows a Geometry to fall also considering collisions.
     */
    default void fall() {
        Geometry.super.translate(new Vector2f(0, 4 * getCurrentGravity()).mul(Window.dt));
        boolean intersects = false;
        for (CollidableStructure structure : CollidableStructure.collidables) {
            for (Quad quad : structure.getCollidableQuads()) {
                if (intersects(quad)) {
                    intersects = true;
                    setCurrentGravity(0);
                }
            }
        }
        if (getCurrentGravity() > NORMAL_GRAVITY && !intersects)
            setCurrentGravity(getCurrentGravity() - 40f * Window.dt);
    }

    /**
     * Falls without considering collisions
     * @param until the value at which it stops, fulfills the role of an actually collidable ground
     */
    default void fallNoCollide(float until) {
        if (center().y >= until) {
            Geometry.super.translate(new Vector2f(0, 4 * getCurrentGravity()).mul(Window.dt));
            if (getCurrentGravity() > NORMAL_GRAVITY)
                setCurrentGravity(getCurrentGravity() - 40f * Window.dt);
        }
    }

    /**
     * Cheks if the current Physics object has any collision at all
     */
    default boolean hasCollision() {
        for (CollidableStructure structure: CollidableStructure.collidables)
            for (Quad quad: structure.getCollidableQuads()) {
                if (intersects(quad)) return true;
            }
        return false;
    }

    /**
     * Checks if this Geometry is intersecting a specific geometry
     * @param geometry The Geometry to check
     * @return whether or not they intersect
     */
    default boolean intersects(Geometry geometry) {
        return this.getMinX() < geometry.getMaxX() &&
                this.getMaxX() > geometry.getMinX() &&
                this.getMaxY() > geometry.getMinY() &&
                this.getMinY() < geometry.getMaxY();
    }
}