package dev.elk.game.fontSettings;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FontInformation {

    private final float heightWidthRatio;
    private final Path jsonPath;
    private final Path pngPath;
    private final float fontSize;
    private final float fontWhitespace;

    public FontInformation(Font font, float fontSize) {
        this.jsonPath = Paths.get("Assets/SpriteJson/fonts/" + font.id + ".json");
        this.pngPath = Paths.get("Assets/Spritesheets/fonts/" + font.id + ".png");
        this.heightWidthRatio = font.heightWidthRatio;
        this.fontWhitespace = fontSize*0.04f;
        this.fontSize = fontSize / 10;
    }

    public float getHeightWidthRatio() {
        return heightWidthRatio;
    }

    public Path getJsonPath() {
        return jsonPath;
    }

    public Path getPngPath() {
        return pngPath;
    }

    public float getFontSize() {
        return fontSize;
    }

    public float getFontWhitespace() {
        return fontWhitespace;
    }
}
