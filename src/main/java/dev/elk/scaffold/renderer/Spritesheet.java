package dev.elk.scaffold.renderer;

import com.google.gson.Gson;
import org.joml.Vector2f;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This splices a big texture into many smaller spritesheets according to a specifcation derived from a json file.
 * @author Felix Kunze
 * @author Louis Schell
 */
public class Spritesheet {

    final int sheetWidth, sheetHeight, tileWidth, tileHeight;
    transient Texture texture;

    private final ArrayList<Sprite> sprites = new ArrayList<>();

    public static Spritesheet from(Path path, Texture texture) throws IOException {
        Gson gson = new Gson();
        return gson
                .fromJson(
                        new String(Files.readAllBytes(path)),
                        Spritesheet.class
                )
                .init(texture);
    }

    public void calculateUVCoords() {
        float yFactor = (float) tileHeight / (float) sheetHeight;
        float xFactor = (float) tileWidth / (float) sheetWidth;
        for (Sprite sprite : sprites) {

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

    public Spritesheet(int sheetWidth, int sheetHeight, int tileWidth, int tileHeight) {
        this.sheetWidth = sheetWidth;
        this.sheetHeight = sheetHeight;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public void addSprite(Sprite... sprites) {
        this.sprites.addAll(Arrays.asList(sprites));
        this.sprites.forEach(sprite -> sprite.texture = this.texture);
    }

    public ArrayList<Sprite> getSprites() {
        return sprites;
    }

    public Spritesheet init(Texture texture) {
        this.texture = texture;
        sprites.forEach(sprite -> sprite.setTexture(texture));
        calculateUVCoords();
        return this;
    }
}
