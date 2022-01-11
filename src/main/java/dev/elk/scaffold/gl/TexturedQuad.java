package dev.elk.scaffold.gl;

import dev.elk.scaffold.renderer.Sprite;
import org.joml.Vector2f;

import java.awt.*;

public class TexturedQuad extends Quad{

    private Sprite sprite;

    public TexturedQuad(Vector2f posLB, Vector2f posTR, Sprite sprite) {
        this(posLB,
                new Vector2f(posLB).add(new Vector2f(posTR.x-posLB.x, 0)),
                posTR,
                new Vector2f(posLB).add(new Vector2f(0, posTR.y-posLB.y)),
                sprite);
    }

    public TexturedQuad(Sprite sprite, Vector2f posLB, float width, float height){
        this(posLB, new Vector2f(posLB).add(width, height), sprite);
    }

    public TexturedQuad(Vector2f pos1, Vector2f pos2, Vector2f pos3, Vector2f pos4, Sprite sprite) {
        super(pos1, pos2, pos3, pos4, Color.BLACK, sprite.getUvCoords());
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}