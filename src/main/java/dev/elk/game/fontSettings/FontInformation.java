package dev.elk.game.fontSettings;


import java.nio.file.Path;
import java.nio.file.Paths;

public class FontInformation {

    private final float heightWidthRatio;
    private final Path  jsonPath;
    private final Path  pngPath;
    private final float fontSize;

    public FontInformation(FontType fontType, float fontSize){
        String id = switch (fontType){
            case COZETTE -> "cozette";
            case JETBRAINS_MONO -> "jetbrainsMono";
            case TIMES_NEW_ROMAN -> "timesNewRoman";
        };

        jsonPath = Paths.get( "Assets/SpriteJson/fonts/" + id + ".json");
        pngPath = Paths.get( "Assets/Spritesheets/fonts/" + id + ".png");
        heightWidthRatio = switch (fontType) {
            case COZETTE -> 18f/16f;
            case JETBRAINS_MONO -> 20f/16f;
            case TIMES_NEW_ROMAN -> 20f/16f;
        };

        this.fontSize = fontSize / 100;
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
}
