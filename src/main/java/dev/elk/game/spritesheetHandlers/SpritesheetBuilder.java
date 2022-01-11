package dev.elk.game.spritesheetHandlers;

import dev.elk.game.fontSettings.Font;
import dev.elk.scaffold.renderer.Spritesheet;
import dev.elk.scaffold.renderer.Texture;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Builds a Spritesheet according to information provided.
 * @see SpritesheetInfo
 * @auhor Felix Kunze
 */
public class SpritesheetBuilder {

    public static final ArrayList<Texture> TEXTURES = new ArrayList<>();

    public static void generateSpritesheets(SpritesheetInfo spritesheetInfo) throws IOException {
        if (spritesheetInfo.isAnimated) {
            Spritesheet.fromAnimated(spritesheetInfo.jsonPath, spritesheetInfo.texturePath);
        }
        Spritesheet.from(spritesheetInfo.jsonPath,spritesheetInfo.texturePath);
    }

    public static void generateAllSpritesheets() throws IOException {
        for (SpritesheetInfo value : SpritesheetInfo.values()) {
            generateSpritesheets(value);
        }
    }
}
