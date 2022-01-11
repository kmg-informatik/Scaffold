package dev.elk.scaffold.renderer;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.joml.Vector2f;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This splices a big texture into many smaller spritesheets according to a specifcation derived from a json file.
 * @author Felix Kunze
 * @author Louis Schell
 */
public class Spritesheet<T extends Sprite>{

    final int sheetWidth, sheetHeight, tileWidth, tileHeight;

    public static final HashMap<String, AnimatedSprite> ANIMATED_SPRITES = new HashMap<>();
    public static final HashMap<String, Sprite> STATIC_SPRITES = new HashMap<>();

    private final ArrayList<T> spritesTemp = new ArrayList<>();

    public static void fromAnimated(Path path, Path texturePath) throws IOException {
        GsonBuilder gson = new GsonBuilder();
        Spritesheet<AnimatedSprite> spritesheet = gson.create().fromJson(
                new String(Files.readAllBytes(path)),
                new TypeToken<Spritesheet<AnimatedSprite>>(){}.getType()
        );
        spritesheet.init(new Texture(texturePath));

        for (int i = 0; i < spritesheet.spritesTemp.size(); i++) {
            ANIMATED_SPRITES.put(spritesheet.spritesTemp.get(i).spriteName, spritesheet.spritesTemp.get(i));
        }

    }

    public static void from(Path path, Path texturePath) throws IOException {
        GsonBuilder gson = new GsonBuilder();
        Spritesheet<Sprite> spritesheet = gson.create().fromJson(
                new String(Files.readAllBytes(path)),
                new TypeToken<Spritesheet<Sprite>>(){}.getType()
        );
        spritesheet.init(new Texture(texturePath));

        for (int i = 0; i < spritesheet.spritesTemp.size(); i++) {
            STATIC_SPRITES.put(spritesheet.spritesTemp.get(i).spriteName, spritesheet.spritesTemp.get(i));
        }
    }

    private void calculateUVCoords() {
        float yFactor = (float) tileHeight / (float) sheetHeight;
        float xFactor = (float) tileWidth / (float) sheetWidth;
        for (T sprite : spritesTemp) {

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

    public void addSprite(T... sprites) {
        this.spritesTemp.addAll(List.of(sprites));
    }

    private void init(Texture texture) {
        spritesTemp.forEach(t -> t.setTexture(texture));
        calculateUVCoords();
    }
}
