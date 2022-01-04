package dev.elk.scaffold.renderer;

import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.Arrays;

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

    public Sprite(Vector2i minPos, Vector2i maxPos, String spriteName) {
        this.minPos = minPos;
        this.maxPos = maxPos;
        this.spriteName = spriteName;
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

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    @Override
    public String toString() {
        return "Sprite{" +
                "minPos=" + minPos +
                ", maxPos=" + maxPos +
                ", spriteName='" + spriteName + '\'' +
                ", uvCoords=" + Arrays.toString(uvCoords) +
                ", texture=" + texture +
                '}';
    }
}
