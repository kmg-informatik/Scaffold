package dev.elk.scaffold.renderer;

import dev.elk.game.fontSettings.FontInformation;
import dev.elk.scaffold.gl.Quad;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.awt.*;
import java.io.IOException;

/**
 * Class that generates text
 * @author Felix Kunze
 */
public class Text {
    private FontInformation fontInformation;
    private Vector3f color;
    private String text;
    private Quad[] quads;
    private Vector2f position;
    private Spritesheet<Sprite> spritesheet;

    public Text(FontInformation fontInformation, Color backgroundColor, Vector2f position, int maxTextLength) throws IOException {
        this.fontInformation = fontInformation;
        color = new Vector3f(backgroundColor.getRGBColorComponents(null));
        this.position = position;
        spritesheet = Spritesheet.from(fontInformation.getJsonPath(),new Texture(fontInformation.getPngPath()));
        text = "";
        for (int i = 0; i < maxTextLength; i++) {
            text += " ";
        }
        generateText();
    }

    public Text(FontInformation fontInformation, Color backgroundColor, Vector2f position, String text) throws IOException {
        this.fontInformation = fontInformation;
        color = new Vector3f(backgroundColor.getRGBColorComponents(null));
        this.position = position;
        spritesheet = Spritesheet.from(fontInformation.getJsonPath(),new Texture(fontInformation.getPngPath()));
        this.text = text;
        generateText();
    }

    private void generateText() {
        quads = new Quad[text.length()];
        for (int i = 0; i < text.length(); i++) {
            quads[i] = new Quad(
                    spritesheet.getSprite(Character.toString(text.charAt(i))),
                    new Vector2f(position.x + i * fontInformation.getFontSize(),position.y),
                    fontInformation.getFontSize(),
                    fontInformation.getFontSize() * fontInformation.getHeightWidthRatio()
            );
        }
    }

    public void changeFont(FontInformation fontInformation) throws IOException {
        this.fontInformation =fontInformation;
        spritesheet = Spritesheet.from(fontInformation.getJsonPath(),new Texture(fontInformation.getPngPath()));
        generateText();
    }



    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        generateText();
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public Spritesheet<Sprite> getSpritesheet() {
        return spritesheet;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
        generateText();
    }

    public Quad[] getQuads() {
        return quads;
    }
}
