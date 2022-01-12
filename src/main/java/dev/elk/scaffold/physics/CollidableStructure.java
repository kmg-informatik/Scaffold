package dev.elk.scaffold.physics;

import dev.elk.scaffold.gl.Quad;
import dev.elk.scaffold.gl.QuadStructure;

import java.util.ArrayList;

public interface CollidableStructure extends QuadStructure {

    ArrayList<CollidableStructure> collidables = new ArrayList<>();

    default Quad[] getCollidableQuads(){
        return getQuads();
    }
}
