package dev.elk.scaffold.Physics;

import dev.elk.scaffold.gl.Quad;
import dev.elk.scaffold.gl.TexturedSquare;
import dev.elk.scaffold.renderer.Sprite;
import org.joml.Vector2f;

public class Ground {

    private static Quad[] quads;
    private static float floorHeight;

    public static void buildGround(float tileSide, Sprite... sprites ) {
        quads = new Quad[(int)(160/tileSide) * sprites.length];
        for (int i = 0; i < sprites.length; i++) {
            for (int j = 0; j < quads.length / sprites.length; j++) {
                quads[j + i * quads.length/ sprites.length] = new TexturedSquare(new Vector2f(tileSide * (float)j -1f,-1 + tileSide * i),tileSide,sprites[i]);
            }
        }
        floorHeight =  tileSide * sprites.length;
    }

    public static Quad[] getQuads() {
        return quads;
    }

    public static float getFloorHeight() {
        return floorHeight;
    }
}
