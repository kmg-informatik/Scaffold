package dev.elk.scaffold.renderer;

import org.joml.Vector2f;
import org.joml.Vector2i;

/**
 * Instatiates an individual Sprite.
 * This is essentially a certain part of a bigger Texture, the Spritesheet
 *
 * @author Felix Kunze
 */
public class Sprite {

    protected final Vector2i minPos;
    protected final Vector2i maxPos;
    protected final String spriteName;
    protected transient Vector2f[] uvCoords;
    protected transient Texture texture;
    protected transient float heightWidthRatio;

    public Sprite(Texture texture, Vector2i minPos, Vector2i maxPos, String spriteName) {
        this.minPos = minPos;
        this.maxPos = maxPos;
        this.spriteName = spriteName;
        this.texture = texture;
    }

    /**
     * Sprite constructor,
     * only used inside of the {@link dev.elk.game.spritesheetHandlers.JsonGenerator JsonGenerator}
     * @param minPos The bottom left of the sprite
     * @param maxPos The top right of the sprite
     * @param spriteName the name of the sprite
     */
    public Sprite(Vector2i minPos, Vector2i maxPos, String spriteName) {
        this.minPos = minPos;
        this.maxPos = maxPos;
        this.spriteName = spriteName;
    }

    /**
     * Returns the currently active UV coordinates
     * @return uvCoords
     */
    public Vector2f[] getUvCoords() {
        return uvCoords;
    }

    /**
     * Sets the UV Coordinates
     * @param uvCoords the uv coordinates to be set
     */
    public void setUvCoords(Vector2f[] uvCoords) {
        this.uvCoords = uvCoords;
    }

    /**
     * sets the ratio
     * @param heigthWidthRatio the ratio
     */
    protected void setHeightWidthRatio(float heigthWidthRatio) {
        this.heightWidthRatio = heigthWidthRatio;
    }

    /**
     * Gets the current active texture
     * @return texture
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * Sets a new texture
     * @param texture The new texture to be set
     */
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    /**
     * Returns the ratio of width and height.
     * Useful for creating quads in native size
     * @return The ratio
     */
    public float getHeightWidthRatio() {
        return heightWidthRatio;
    }

    /**
     * Calculates the height of the sprite
     * @return the height
     */
    public float getYDifference(){
        return this.maxPos.y - this.minPos.y;
    }
}