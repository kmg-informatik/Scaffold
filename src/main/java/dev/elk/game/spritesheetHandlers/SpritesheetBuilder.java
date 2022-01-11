package dev.elk.game.spritesheetHandlers;

import dev.elk.scaffold.renderer.Spritesheet;
import dev.elk.scaffold.renderer.Texture;

import java.io.IOException;
import java.util.ArrayList;

public class SpritesheetBuilder {

    public static final ArrayList<Texture> TEXTURES = new ArrayList<>();

    public static void generateSpritesheets(SpritesheetInfo spritesheetInfo) throws IOException {
        if (spritesheetInfo.isAnimated) {
            Spritesheet.fromAnimated(spritesheetInfo.jsonPath, spritesheetInfo.texturePath);
        }
        Spritesheet.from(spritesheetInfo.jsonPath,spritesheetInfo.texturePath);
    }
}
