package dev.elk.scaffold.renderer;

import com.google.gson.Gson;
import org.joml.Vector2f;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This is the Spritesheet for Animated sprites.
 * This should in theory expand the spritesheet but whatever.
 * @author Felix Kunze
 */
public class AnimatedSpritesheet {

    final int sheetWidth, sheetHeight, tileWidth, tileHeight;
    transient Texture texture;

    private final  ArrayList<AnimatedSprite> sprites = new ArrayList<>();

    public static AnimatedSpritesheet from(Path path, Texture texture) throws IOException {
        Gson gson = new Gson();
        return gson
                .fromJson(
                        new String(Files.readAllBytes(path)),
                        AnimatedSpritesheet.class
                )
                .init(texture);
    }
    public AnimatedSpritesheet(int sheetWidth, int sheetHeight, int tileWidth, int tileHeight) {
        this.sheetWidth = sheetWidth;
        this.sheetHeight = sheetHeight;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public void addAnimatedSprite(AnimatedSprite... sprites) {
        this.sprites.addAll(Arrays.asList(sprites));
    }

    public void calculateUVCoords() {
        float yFactor = (float) tileHeight / (float) sheetHeight;
        float xFactor = (float) tileWidth / (float) sheetWidth;
        for (AnimatedSprite sprite: sprites) {

            float minY = (float) sprite.minPos.y * yFactor;
            float minX = (float) sprite.minPos.x * xFactor;
            float maxY = (float) sprite.maxPos.y * yFactor;
            float maxX = (float) sprite.maxPos.x * xFactor;

            Vector2f[] uvCoords = {
                    new Vector2f(minX, minY),
                    new Vector2f(maxX, minY),
                    new Vector2f(maxX, maxY),
                    new Vector2f(minX, maxY)
            };

            sprite.setUvCoords(uvCoords);
        }
    }

    public AnimatedSpritesheet init(Texture texture) {
        this.texture = texture;
        sprites.forEach(sprite -> sprite.setTexture(texture));
        calculateUVCoords();
        return this;
    }

    public ArrayList<AnimatedSprite> getSprites() {
        return sprites;
    }
}
