package dev.elk.scaffold.renderer;

import org.joml.Vector2f;
import org.joml.Vector2i;

/**
 * Instatiates an individual Sprite.
 * This is essentially a certain part of a bigger Texture, the Spritesheet
 * @author Felix Kunze
 * @author Louis Schell
 */
public class Sprite {

    final Vector2i minPos;
    final Vector2i maxPos;
    final String spriteName;
    transient protected Vector2f[] uvCoords;
    transient Texture texture;

    public Sprite(Texture texture, Vector2i minPos, Vector2i maxPos, String spriteName) {
        this.minPos = minPos;
        this.maxPos = maxPos;
        this.spriteName = spriteName;
        this.texture = texture;
    }

    public Vector2f[] getUvCoords() {
        return uvCoords;
    }

    public void setUvCoords(Vector2f[] uvCoords) {
        this.uvCoords = uvCoords;
    }

    public Texture getTexture() {
        return texture;
    }
}
