package dev.elk.scaffold.renderer;

import dev.elk.game.fontSettings.FontInformation;
import dev.elk.scaffold.gl.Geometry;
import dev.elk.scaffold.gl.Quad;
import dev.elk.scaffold.gl.Vertex;
import org.joml.Vector2f;

import java.awt.*;
import java.io.IOException;

/**
 * Class that generates text
 *
 * @author Felix Kunze, Louis Schell
 */
public class Text implements Geometry {
    private final FontInformation fontInformation;
    private Color color;
    private String text;
    private Quad[] quads;
    private Vector2f position;
    private Spritesheet<Sprite> spritesheet;

    public Text(FontInformation fontInformation, Color textColor, Vector2f position, String text) throws IOException {
        if (text.length()<1)
            throw new IOException("Ya cant do that");

        this.fontInformation = fontInformation;
        this.color = textColor;
        this.position = position;
        spritesheet = Spritesheet.from(fontInformation.getJsonPath(), new Texture(fontInformation.getPngPath()));
        this.text = text;
        generateQuads();
    }

    private void generateQuads() {
        quads = new Quad[text.length()];
        for (int i = 0; i < text.length(); i++) {
            quads[i] = new Quad(
                    spritesheet.getSprite(Character.toString(text.charAt(i))),
                    new Vector2f(position.x + i * (fontInformation.getFontSize() - fontInformation.getFontWhitespace()), position.y),
                    fontInformation.getFontSize(),
                    fontInformation.getFontSize() * fontInformation.getHeightWidthRatio(),
                    color
            );
        }
        translateOriginTo(position);
    }

    public String getText() {
        return text;
    }

    public void setText(String newText) {
        if (!newText.equals(text)) {
            if (newText.length() == text.length()){
                for (int i = 0; i < quads.length; i++) {
                    quads[i].setSprite(spritesheet.getSprite(Character.toString(newText.charAt(i))));
                }
                text = newText;
            }else {
                text = newText;
                generateQuads();
            }
        }
    }

    public Spritesheet<Sprite> getSpritesheet() {
        return spritesheet;
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
}
