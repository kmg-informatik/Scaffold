package dev.elk.scaffold.renderer;

import dev.elk.game.fontSettings.FontInformation;
import dev.elk.scaffold.gl.Geometry;
import dev.elk.scaffold.gl.Quad;
import dev.elk.scaffold.gl.TexturedQuad;
import dev.elk.scaffold.gl.bindings.Vertex;
import org.joml.Vector2f;

import java.io.IOException;

/**
 * Class that generates text using multiple Quads
 * @see dev.elk.game.fontSettings.Font
 * @see FontInformation
 * @author Felix Kunze
 * @author Louis Schell
 */
public class Text implements Geometry {
    private final FontInformation fontInformation;
    private String text;
    private TexturedQuad[] quads;
    private Vector2f position;

    /**
     * Generates a Text box made out of multiple Quads
     * with texture specific glyphs determined by input Text.
     * @param fontInformation Information specific to the user chosen font.
     * @param position The Position of the text box on the screen
     * @param text The text that is given
     */
    public Text(FontInformation fontInformation, Vector2f position, String text) throws IOException {
        if (text.length()<1)
            throw new IOException("Ya cant do that");

        this.fontInformation = fontInformation;
        this.position = position;
        this.text = text;
        generateQuads();
    }

    private void generateQuads() {
        quads = new TexturedQuad[text.length()];
        for (int i = 0; i < text.length(); i++) {
            String spriteName = fontInformation.getFontID()+ "_" + text.charAt(i);
            quads[i] = new TexturedQuad(
                    new Vector2f(position.x + i * (fontInformation.getFontSize() - fontInformation.getFontWhitespace()), position.y),
                    fontInformation.getFontSize(),
                    fontInformation.getFontSize() * fontInformation.getHeightWidthRatio(),
                    Spritesheet.STATIC_SPRITES.get(spriteName)
            );
        }
        translateOriginTo(position);
    }

    public void setText(String newText) {
        if (!newText.equals(text)) {
                text = newText;
                generateQuads();
            }
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

    @Override
    public int[] getIndices() {
        int[] indices = new int[quads.length*6];
        int addTo = 0;
        for (int i = 0; i < quads.length; i++) {
            indices[i*6] = i + addTo;
            indices[i*6+1] = i+1 + addTo;
            indices[i*6+2] = i+2 + addTo;
            indices[i*6+3] = i+2 + addTo;
            indices[i*6+4] = i+3 + addTo;
            indices[i*6+5] = i + addTo;
            addTo+=3;
        }
        return indices;
    }

    @Override
    public Vertex[] getVertices() {
        Vertex[] vertices = new Vertex[quads.length<<2];
        for (int i = 0; i < vertices.length; i+=4) {
            vertices[i] = quads[i>>2].getVertices()[0];
            vertices[i+1] = quads[i>>2].getVertices()[1];
            vertices[i+2] = quads[i>>2].getVertices()[2];
            vertices[i+3] = quads[i>>2].getVertices()[3];
        }
        return vertices;
    }

    public String getText() {
        return text;
    }

    public Texture getTexture() {
        return quads[0].getSprite().getTexture();
    }
}
