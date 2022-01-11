package dev.elk.scaffold.renderer;

import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.Arrays;

/**
 * This different sprite type allows for the usage animation frames
 * by constraining each animation to a row.
 *
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

        for (int i = 0; i < 4; i++)
            uvCoords[i].x += width;

        if (currentFrame == frameCount - 1) {
            currentFrame = 0;
            uvCoords = new Vector2f[]{
                    new Vector2f(originalUVCoords[0]),
                    new Vector2f(originalUVCoords[1]),
                    new Vector2f(originalUVCoords[2]),
                    new Vector2f(originalUVCoords[3])
            };
        } else this.currentFrame++;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    @Override
    public void setUvCoords(Vector2f[] uvCoords) {
        this.uvCoords = uvCoords;
        this.originalUVCoords = new Vector2f[]{
                new Vector2f(uvCoords[0]),
                new Vector2f(uvCoords[1]),
                new Vector2f(uvCoords[2]),
                new Vector2f(uvCoords[3])
        };
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
