package dev.elk.scaffold.Physics;

import dev.elk.scaffold.renderer.Sprite;
import org.joml.Vector2f;

public class Ground {

    BoxCollider[] colliders;

    public Ground(float tileSide, Sprite... sprites ) {
        colliders = new BoxCollider[(int)(2/tileSide) * sprites.length];
        for (int i = 0; i < sprites.length; i++) {
            for (int j = 0; j < colliders.length / sprites.length; j++) {
                colliders[j + i * colliders.length/ sprites.length] = new SquareCollider(sprites[i],new Vector2f(tileSide * (float)j -1f,-1 + tileSide * i),tileSide);
            }
        }
    }

    public BoxCollider[] getColliders() {
        return colliders;
    }
}
