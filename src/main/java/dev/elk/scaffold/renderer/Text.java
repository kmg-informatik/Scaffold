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

    /**
     * Generates each quad in the text and assigns it a texture by always creating an offset
     */
    private void generateQuads() {
        quads = new TexturedQuad[text.length()];
        for (int i = 0; i < text.length(); i++) {
            String spriteName = fontInformation.getFontID() + "_" + text.charAt(i);
            quads[i] = new TexturedQuad(
                    //Kerning issues fixed via getFontWhitespace
                    new Vector2f(position.x + i * (fontInformation.getFontSize() - fontInformation.getFontWhitespace()), position.y),
                    fontInformation.getFontSize(),
                    fontInformation.getFontSize() * fontInformation.getHeightWidthRatio(),
                    Spritesheet.staticSprites.get(spriteName)
            );
        }
        translateOriginTo(position);
    }

    /**
     * Sets the position of the text and moves each of the quads contained in the text.
     * @param position The new position of the text
     */
    public void setPosition(Vector2f position) {
        if (!position.equals(this.position)) {
            this.position = position;
            for (int i = 0; i < quads.length; i++) {
                quads[i].translateOriginTo(new Vector2f(position.x + i * (fontInformation.getFontSize() - fontInformation.getFontWhitespace()), position.y));
            }
        }

    }

    /**
     * Returns all Quads
     * @return quads
     */
    public Quad[] getQuads() {
        return quads;
    }


    /**
     * Returns the displayed text as a string
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text and regenerates the quads in order to refresh it.
     * Checking here is important since the generation of many quads is rather ressource intensive.
     * @param newText
     */
    public void setText(String newText) {
        if (!newText.equals(text)) {
            text = newText;
            generateQuads();
        }
    }

    /**
     * Since all Sprites have the same texture
     * it is sufficient to return the texture of the first sprite.
     * @return texture of the first sprite
     */
    public Texture getTexture() {
        return quads[0].getSprite().getTexture();
    }
}
