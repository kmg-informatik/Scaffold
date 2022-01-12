package dev.elk.scaffold.renderer;

import dev.elk.game.fontSettings.FontInformation;
import dev.elk.scaffold.gl.Quad;
import dev.elk.scaffold.gl.QuadStructure;
import dev.elk.scaffold.gl.TexturedQuad;
import org.joml.Vector2f;

import java.io.IOException;

/**
 * Class that generates text using multiple Quads
 *
 * @author Felix Kunze
 * @author Louis Schell
 * @see dev.elk.game.fontSettings.Font
 * @see FontInformation
 */
public class Text implements QuadStructure {
    private final FontInformation fontInformation;
    private String text;
    private TexturedQuad[] quads;
    private Vector2f position;

    /**
     * Generates a Text box made out of multiple Quads
     * with texture specific glyphs determined by input Text.
     *
     * @param fontInformation Information specific to the user chosen font.
     * @param position        The Position of the text box on the screen
     * @param text            The text that is given
     */
    public Text(FontInformation fontInformation, Vector2f position, String text) throws IOException {
        if (text.length() < 1)
            throw new IOException("Ya cant do that");

        this.fontInformation = fontInformation;
        this.position = position;
        this.text = text;
        generateQuads();
    }

    private void generateQuads() {
        quads = new TexturedQuad[text.length()];
        for (int i = 0; i < text.length(); i++) {
            String spriteName = fontInformation.getFontID() + "_" + text.charAt(i);
            quads[i] = new TexturedQuad(
                    new Vector2f(position.x + i * (fontInformation.getFontSize() - fontInformation.getFontWhitespace()), position.y),
                    fontInformation.getFontSize(),
                    fontInformation.getFontSize() * fontInformation.getHeightWidthRatio(),
                    Spritesheet.staticSprites.get(spriteName)
            );
        }
        translateOriginTo(position);
    }

    public void setPosition(Vector2f position) {
        if (!position.equals(this.position)) {
            this.position = position;
            for (int i = 0; i < quads.length; i++) {
                quads[i].translateOriginTo(new Vector2f(position.x + i * (fontInformation.getFontSize() - fontInformation.getFontWhitespace()), position.y));
            }
        }

    }

    public Quad[] getQuads() {
        return quads;
    }


    public String getText() {
        return text;
    }

    public void setText(String newText) {
        if (!newText.equals(text)) {
            text = newText;
            generateQuads();
        }
    }

    public Texture getTexture() {
        return quads[0].getSprite().getTexture();
    }
}
