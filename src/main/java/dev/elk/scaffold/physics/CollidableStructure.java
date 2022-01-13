package dev.elk.scaffold.physics;

import dev.elk.scaffold.gl.Quad;
import dev.elk.scaffold.gl.QuadStructure;

import java.util.ArrayList;

/**
 * An interface allowing for structures to differentiate between
 * Quads that are to be only rendered and Quads that are to be rendered and collided with
 * @author Felix Kunze
 */
public interface CollidableStructure extends QuadStructure {

    /**
     * A static arraylist of all Collidable Structures to check for collisions
     */
    ArrayList<CollidableStructure> collidables = new ArrayList<>();

    /**
     * All Quads that are Collidable.
     * By default, all Quads are also Collidable
     * @return All quads
     */
    default Quad[] getCollidableQuads(){
        return getQuads();
    }
}
