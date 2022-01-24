package dev.elk.scaffold.renderer;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dev.elk.game.spritesheetHandlers.SpritesheetBuilder;
import org.joml.Vector2f;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This splices a big texture into many smaller sprites according to a specifcation derived from a json file.
 *
 * @author Felix Kunze
 */
public class Spritesheet<T extends Sprite> {

    public static final HashMap<String, AnimatedSprite> animatedSprites = new HashMap<>();
    public static final HashMap<String, Sprite> staticSprites = new HashMap<>();

    final int sheetWidth, sheetHeight, tileWidth, tileHeight;
    private final ArrayList<T> spritesTemp = new ArrayList<>();

    /**
     * Generates a spritesheet with all basic propperties
     * @param sheetWidth The width of the spritesheet
     * @param sheetHeight The height of the spritesheet
     * @param tileWidth The width of a single tile in a spritesheet
     * @param tileHeight The height of a single tile in a spritesheet
     */
    public Spritesheet(int sheetWidth, int sheetHeight, int tileWidth, int tileHeight) {
        this.sheetWidth = sheetWidth;
        this.sheetHeight = sheetHeight;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    /**
     * Reads out a json file, creates a spritesheet from the given specifications and adds all the sprites from that spritesheet
     * into the static spritesheet Hashmap for global referencing
     * @param path the path of the json
     * @param texturePath The path of the texture
     * @throws IOException if any of the paths do not exist
     */
    public static void from(Path path, Path texturePath) throws IOException {
        GsonBuilder gson = new GsonBuilder();
        Spritesheet<Sprite> spritesheet = gson.create().fromJson(
                new String(Files.readAllBytes(path)),
                new TypeToken<Spritesheet<Sprite>>() {
                }.getType()
        );
        spritesheet.init(new Texture(texturePath));

        for (int i = 0; i < spritesheet.spritesTemp.size(); i++) {
            staticSprites.put(spritesheet.spritesTemp.get(i).spriteName, spritesheet.spritesTemp.get(i));
        }
    }

    /**
     * Same as the {@link Spritesheet#from(Path, Path) from} method except
     * it adds it to the animated sprites
     * @param path The path of the json
     * @param texturePath The path of the texture
     * @throws IOException
     */
    public static void fromAnimated(Path path, Path texturePath) throws IOException {
        GsonBuilder gson = new GsonBuilder();
        Spritesheet<AnimatedSprite> spritesheet = gson.create().fromJson(
                new String(Files.readAllBytes(path)),
                new TypeToken<Spritesheet<AnimatedSprite>>() {
                }.getType()
        );
        spritesheet.init(new Texture(texturePath));

        for (int i = 0; i < spritesheet.spritesTemp.size(); i++) {
            animatedSprites.put(spritesheet.spritesTemp.get(i).spriteName, spritesheet.spritesTemp.get(i));
        }

    }


    /**
     * Calculates the UVCoordinate of each sprite and passes that information.
     * UVCoordinates are from 0 to 1 in each direction
     */
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
            //Sets the width to height ratio in pixels.
            //This is important since the sprite only knows its position in relation
            //to the spritesheet and not its absolute dimensions
            sprite.setHeightWidthRatio((float) tileHeight / (float) tileWidth);
        }
    }

    /**
     * Adds sprites to a Spritesheet.
     * Only public due to the {@link dev.elk.game.spritesheetHandlers.JsonGenerator JsonGenerator}.
     * @param sprites The sprites to be added
     */
    public void addSprites(T... sprites) {
        this.spritesTemp.addAll(List.of(sprites));
    }

    /**
     * Initializes a spritesheets by setting textures and UVCoordinates
     * @param texture The texture of the sprites
     */
    private void init(Texture texture) {
        SpritesheetBuilder.TEXTURES.add(texture);
        spritesTemp.forEach(t -> t.setTexture(texture));
        calculateUVCoords();
    }
}
