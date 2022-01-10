package dev.elk.scaffold.renderer;

import dev.elk.game.fontSettings.FontInformation;
import dev.elk.scaffold.gl.Geometry;
import dev.elk.scaffold.gl.Quad;
import dev.elk.scaffold.gl.Vertex;
import org.joml.Vector2f;

import java.awt.*;
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
    private Color color;
    private String text;
    private Quad[] quads;
    private Vector2f position;

    /**
     * Generates a Text box made out of multiple Quads
     * with texture specific glyphs determined by input Text.
     * @param fontInformation Information specific to the user chosen font.
     * @param textColor The color of the Text as is displayed.
     * @param position The Position of the text box on the screen
     * @param text The text that is given
     */
    public Text(FontInformation fontInformation, Color textColor, Vector2f position, String text) throws IOException {
        if (text.length()<1)
            throw new IOException("Ya cant do that");

        this.fontInformation = fontInformation;
        this.color = textColor;
        this.position = position;
        //from(fontInformation.getJsonPath(), fontInformation.getPngPath());
        this.text = text;
        generateQuads();
    }

    private void generateQuads() {
        quads = new Quad[text.length()];
        for (int i = 0; i < text.length(); i++) {
            String spriteName = fontInformation.getFontID()+ "_" + text.charAt(i);
            System.out.println(spriteName);
            quads[i] = new Quad(
                    Spritesheet.STATIC_SPRITES.get(spriteName),
                    new Vector2f(position.x + i * (fontInformation.getFontSize() - fontInformation.getFontWhitespace()), position.y),
                    fontInformation.getFontSize(),
                    fontInformation.getFontSize() * fontInformation.getHeightWidthRatio(),
                    color
            );
        }
        translateOriginTo(position);
    }

    public void setText(String newText) {
        if (!newText.equals(text)) {
            if (newText.length() == text.length()){
                for (int i = 0; i < quads.length; i++) {
                    quads[i].setSprite(Spritesheet.STATIC_SPRITES.get(fontInformation.getFontID() + "_" + text.charAt(i)));
                }
                text = newText;
            }else {
                text = newText;
                generateQuads();
            }
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

    public Texture getTexture() {
        return quads[0].getSprite().getTexture();
    }
}
