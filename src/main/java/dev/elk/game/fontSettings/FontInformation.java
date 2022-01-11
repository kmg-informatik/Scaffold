package dev.elk.game.fontSettings;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A wrapper for information about Fonts.
 *
 * @author Felix Kunze
 * @author Louis Schell
 * @see Font
 * @see dev.elk.scaffold.renderer.Text
 */
public class FontInformation {

    private final float heightWidthRatio;
    private final Path jsonPath;
    private final Path pngPath;
    private final float fontSize;
    private final float fontWhitespace;
    private final String fontID;

    /**
     * Generates necessary font information used to render Text.
     */
    public FontInformation(Font font, float fontSize) {
        this.jsonPath = Paths.get("Assets/SpriteJson/fonts/" + font.name + ".json");
        this.pngPath = Paths.get("Assets/Spritesheets/fonts/" + font.name + ".png");
        this.heightWidthRatio = font.heightWidthRatio;
        this.fontWhitespace = fontSize * 0.04f;
        this.fontSize = fontSize / 10;
        this.fontID = font.fontID;
    }

    /**
     * Returns the ratio of height to width.
     * The ratio of height to width since most fonts have
     * glyphs are taller than they are wide.
     */
    public float getHeightWidthRatio() {
        return heightWidthRatio;
    }

    /**
     * Returns the path of the Json used to reference the Glyphs inside the Spritesheet
     */
    public Path getJsonPath() {
        return jsonPath;
    }

    /**
     * Returns the path of the png file that is used to build the Texture
     */
    public Path getPngPath() {
        return pngPath;
    }

    /**
     * Returns the size of the font
     */
    public float getFontSize() {
        return fontSize;
    }

    /**
     * Returns the whitespace of the font, necessary due to kerning being unstable
     */
    public float getFontWhitespace() {
        return fontWhitespace;
    }

    public String getFontID() {
        return fontID;
    }
}
