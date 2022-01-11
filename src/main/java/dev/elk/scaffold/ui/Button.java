package dev.elk.scaffold.ui;

import dev.elk.scaffold.gl.Quad;
import dev.elk.scaffold.plugin.PluginRepository;
import org.joml.Vector2f;

import java.awt.*;

/**
 * @author Louis Schell
 * @since 11/01/2022
 */
public class Button extends Quad /*implements Clickable*/{

    public Button(Vector2f posLB, Vector2f posTR) {
        super(posLB, posTR);
    }

    public Button(Vector2f posLB, Vector2f posTR, Color color) {
        super(posLB, posTR, color);
    }

    public Button(Vector2f pos1, Vector2f pos2, Vector2f pos3, Vector2f pos4, Color color) {
        super(pos1, pos2, pos3, pos4, color);
    }

    protected Button(Vector2f pos1, Vector2f pos2, Vector2f pos3, Vector2f pos4, Color color, Vector2f[] texCoords) {
        super(pos1, pos2, pos3, pos4, color, texCoords);
    }

    public void onClick(){
        PluginRepository.notifyAllOf(e->e.onButtonClicked(this));
    }

}