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
    private final int animationSpeed;

    /**
     * Used to reset the sprite to the origin once it has done all the animation frames.
     */
    private Vector2f[] originalUVCoords;

    public AnimatedSprite(Vector2i minPos, Vector2i maxPos, String spriteName, int frameCount, int animationSpeed) {
        super(minPos, maxPos, spriteName);
        this.frameCount = frameCount;
        this.currentFrame = 0;
        this.animationSpeed = animationSpeed;
    }

    public AnimatedSprite(Vector2i minPos, Vector2i maxPos, String spriteName, int frameCount) {
        super(minPos, maxPos, spriteName);
        this.frameCount = frameCount;
        this.currentFrame = 0;
        this.animationSpeed = 2;
    }

    /**
     * Transitions to next frame with consideration the animation speed.
     * Therefore, it may take multiple  nextFrame calls before the frame is actually switched
     */
    private int animationCounter = 0;
    public void nextFrame() {
        if ((animationCounter %= animationSpeed) ==0) {
            float width = uvCoords[1].x - uvCoords[0].x;

            for (int i = 0; i < 4; i++)
                uvCoords[i].x += width;

            if (currentFrame % frameCount == 0) {
                uvCoords = new Vector2f[]{
                        new Vector2f(originalUVCoords[0]),
                        new Vector2f(originalUVCoords[1]),
                        new Vector2f(originalUVCoords[2]),
                        new Vector2f(originalUVCoords[3])
                };
            }
            this.currentFrame++;
        }
        animationCounter++;
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
