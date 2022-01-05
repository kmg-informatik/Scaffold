package dev.elk.scaffold.renderer;

import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.Arrays;

/**
 * This different sprite type allows for the usage animation frames
 * by constraining each animation to a row.
 * @author Felix Kunze
 * @author Louis Schell
 */
public class AnimatedSprite extends Sprite {

    final int frameCount;
    private int currentFrame;

    private Vector2f[] originalUVCoords;

    public AnimatedSprite(Vector2i minPos, Vector2i maxPos, String spriteName, int frameCount) {
        super(minPos, maxPos, spriteName);
        this.frameCount = frameCount;
        this.currentFrame = 0;
    }

    public void nextFrame() {
        float width = uvCoords[1].x - uvCoords[0].x;


        if (currentFrame == frameCount) {
            currentFrame = 0;
            uvCoords = originalUVCoords.clone();
        }

        for (int i = 0; i < 4; i++)
            uvCoords[i].x += width;

        System.out.println(Arrays.toString(originalUVCoords));
        System.out.println(Arrays.toString(uvCoords));
        System.out.println();

        this.currentFrame++;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    @Override
    public void setUvCoords(Vector2f[] uvCoords) {
        this.uvCoords = uvCoords;
        this.originalUVCoords = uvCoords;
    }

    @Override
    public String toString() {
        return "AnimatedSprite{" +
                "frameCount=" + frameCount +
                ", currentFrame=" + currentFrame +
                ", minPos=" + minPos +
                ", maxPos=" + maxPos +
                ", spriteName='" + spriteName + '\'' +
                ", uvCoords=" + Arrays.toString(uvCoords) +
                ", texture=" + texture +
                '}';
    }
}
