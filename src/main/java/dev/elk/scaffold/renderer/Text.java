package dev.elk.scaffold.renderer;

import dev.elk.game.fontSettings.FontInformation;
import org.joml.Vector3f;

import java.awt.*;

/**
 * Class that generates text
 * @author Felix Kunze
 */
public class Text {
    private FontInformation fontInformation;
    private Vector3f color;
    private String text;

    public Text(FontInformation fontInformation, Color backgroundColor) {
        this.fontInformation = fontInformation;
        color = new Vector3f(backgroundColor.getRGBColorComponents(null));
    }

    private void generateText() {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }
}
