package dev.elk.game.spritesheetHandlers;

import dev.elk.scaffold.renderer.Spritesheet;

import java.io.IOException;

public class SpritesheetBuilder {

    public static void generateSpriteSheets(SpritesheetInfo spritesheetInfo) throws IOException {
        if (spritesheetInfo.isAnimated) {
            Spritesheet.fromAnimated(spritesheetInfo.jsonPath, spritesheetInfo.texturePath);
        }
        Spritesheet.from(spritesheetInfo.jsonPath,spritesheetInfo.texturePath);
    }
}
