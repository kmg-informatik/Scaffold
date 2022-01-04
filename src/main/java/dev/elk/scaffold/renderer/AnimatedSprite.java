package dev.elk.scaffold.renderer;

import org.joml.Vector2f;
import org.joml.Vector2i;

/**
 * This different sprite type allows for the usage animation frames
 * by constraining each animation to a row.
 * @author Felix Kunze
 * @author Louis Schell
 */
public class AnimatedSprite extends Sprite {

    final int frameCount;
    private int currentFrame;

    public AnimatedSprite(Vector2i minPos, Vector2i maxPos, String spriteName, int frameCount) {
        super(minPos, maxPos, spriteName);
        this.frameCount = frameCount;
        this.currentFrame = 0;
    }

    public void nextFrame() {
        currentFrame %= frameCount;

        for (int i = 0; i < 4; i++) {
            this.uvCoords[i] = new Vector2f(
                    this.uvCoords[i].x + (maxPos.x - minPos.x),
                    this.uvCoords[i].y);
        }
        this.currentFrame++;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }
}
