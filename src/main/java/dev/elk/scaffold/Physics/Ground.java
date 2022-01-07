package dev.elk.scaffold.Physics;

import dev.elk.scaffold.gl.Quad;
import dev.elk.scaffold.gl.Square;
import dev.elk.scaffold.renderer.Sprite;
import org.joml.Vector2f;

public class Ground {

    private final Quad[] colliders;
    private final float floorHeight;

    public Ground(float tileSide, Sprite... sprites ) {
        colliders = new Quad[(int)(2/tileSide) * sprites.length];
        for (int i = 0; i < sprites.length; i++) {
            for (int j = 0; j < colliders.length / sprites.length; j++) {
                colliders[j + i * colliders.length/ sprites.length] = new Square(sprites[i],new Vector2f(tileSide * (float)j -1f,-1 + tileSide * i),tileSide);
            }
        }
        floorHeight = -1f + tileSide * sprites.length;
    }

    public Quad[] getColliders() {
        return colliders;
    }

    public float getFloorHeight() {
        return floorHeight;
    }
}
