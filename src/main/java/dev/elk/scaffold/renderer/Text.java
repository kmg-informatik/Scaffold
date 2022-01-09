package dev.elk.scaffold.renderer;

import com.google.gson.stream.JsonToken;
import dev.elk.game.fontSettings.FontInformation;
import dev.elk.scaffold.gl.Quad;
import org.joml.Vector2f;

import java.awt.*;
import java.io.IOException;

/**
 * Class that generates text
 * @author Felix Kunze
 */
public class Text {
    private FontInformation fontInformation;
    private Color color;
    private String text;
    private Quad[] quads;
    private Vector2f position;
    private Spritesheet<Sprite> spritesheet;

    public Text(FontInformation fontInformation, Color backgroundColor, Vector2f position, int maxTextLength) throws IOException {
        this.fontInformation = fontInformation;
        this.color = backgroundColor;
        this.position = position;
        spritesheet = Spritesheet.from(fontInformation.getJsonPath(),new Texture(fontInformation.getPngPath()));
        text = "";
        for (int i = 0; i < maxTextLength; i++) {
            text += "a";
        }
        initText();
    }

    public Text(FontInformation fontInformation, Color backgroundColor, Vector2f position, String text) throws IOException {
        this.fontInformation = fontInformation;
        this.color = backgroundColor;
        this.position = position;
        spritesheet = Spritesheet.from(fontInformation.getJsonPath(),new Texture(fontInformation.getPngPath()));
        this.text = text;
        initText();
    }

    private void initText() {
        quads = new Quad[text.length()];
        for (int i = 0; i < text.length(); i++) {
            quads[i] = new Quad(
                    spritesheet.getSprite(Character.toString(text.charAt(i))),
                    new Vector2f(position.x + i * (fontInformation.getFontSize() - fontInformation.getFontWhitspace()),position.y),
                    fontInformation.getFontSize(),
                    fontInformation.getFontSize() * fontInformation.getHeightWidthRatio(),
                    color
            );
        }
    }

    private void updateText() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if (!text.equals(this.text)) {
            this.text = text;
            for (int i = 0; i < text.length(); i++) {
                quads[i].setSprite(spritesheet.getSprite(Character.toString(text.charAt(i))));
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
                quads[i].translateTo(new Vector2f(position.x+ i * (fontInformation.getFontSize() - fontInformation.getFontWhitspace()), position.y));
            }
        }

    }

    public Quad[] getQuads() {
        return quads;
    }
}
