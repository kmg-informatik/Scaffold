package dev.elk.scaffold.renderer;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.joml.Vector2f;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

/**
 * This splices a big texture into many smaller spritesheets according to a specifcation derived from a json file.
 * @author Felix Kunze
 * @author Louis Schell
 */
public class Spritesheet<T extends Sprite>{

    final int sheetWidth, sheetHeight, tileWidth, tileHeight;
    transient Texture texture;

    private final HashMap<String, T> sprites = new HashMap<>();

    public static Spritesheet<AnimatedSprite> fromAnimated(Path path, Texture texture) throws IOException {
        GsonBuilder gson = new GsonBuilder();
        Spritesheet<AnimatedSprite> spritesheet = gson.create().fromJson(new String(Files.readAllBytes(path)),new TypeToken<Spritesheet<AnimatedSprite>>(){}.getType());
        return spritesheet.init(texture);
    }

    public static Spritesheet<Sprite> from(Path path, Texture texture) throws IOException {
        GsonBuilder gson = new GsonBuilder();

        Spritesheet<Sprite> spritesheet = gson.create().fromJson(new String(Files.readAllBytes(path)),new TypeToken<Spritesheet<Sprite>>(){}.getType());

        spritesheet.init(texture);
        return spritesheet;
    }

    public Spritesheet(){
        tileWidth = tileHeight = sheetWidth = sheetHeight = 0;
    }

    public void calculateUVCoords() {
        float yFactor = (float) tileHeight / (float) sheetHeight;
        float xFactor = (float) tileWidth / (float) sheetWidth;
        for (T sprite : sprites.values()) {

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

    @SafeVarargs
    public final void addSprite(T... sprites) {
        for (T sprite : sprites) {
            this.sprites.put(sprite.spriteName, sprite);
        }
    }

    public T getSprite(String spriteName) {
        return sprites.get(spriteName);
    }

    public Spritesheet<T> init(Texture texture) {
        this.texture = texture;
        System.out.println(sprites.values());

        for (T sprite : sprites.values()) {
            sprite.setTexture(texture);
        }

        calculateUVCoords();
        return this;
    }
}
