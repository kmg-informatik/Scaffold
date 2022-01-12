package dev.elk.scaffold.gl;

import dev.elk.scaffold.gl.bindings.Vertex;
import dev.elk.scaffold.renderer.Sprite;
import org.joml.Vector2f;

import java.awt.*;

/**
 * A Quad that implements a Sprite with a texture in addition to the normal Quad information
 *
 * @author Louis Schell
 * @author Felix Kunze
 */
public class TexturedQuad extends Quad {

    private Sprite sprite;

    public TexturedQuad(Vector2f posLB, float size, Sprite sprite){
        this(
                posLB,
                new Vector2f(posLB.x + size, posLB.y + size * (sprite.getHeightWidthRatio())),
                sprite);
        System.out.println(sprite.getHeightWidthRatio());
    }

    public TexturedQuad(Vector2f posLB, Vector2f posTR, Sprite sprite) {
        this(posLB,
                new Vector2f(posLB).add(new Vector2f(posTR.x - posLB.x, 0)),
                posTR,
                new Vector2f(posLB).add(new Vector2f(0, posTR.y - posLB.y)),
                sprite);
    }

    public TexturedQuad(Vector2f posLB, float width, float height, Sprite sprite) {
        this(posLB, new Vector2f(posLB).add(width, height), sprite);
    }

    public TexturedQuad(Vector2f pos1, Vector2f pos2, Vector2f pos3, Vector2f pos4, Sprite sprite) {
        super(
                pos1,
                pos2,
                pos3,
                pos4,
                Color.BLACK,
                sprite.getUvCoords(),
                sprite.getTexture().getTexID()
        );
        this.sprite = sprite;
    }

    public void normalise(){
        float ratio = sprite.getHeightWidthRatio();
        Vertex[] vertices = getVertices();

        Vector2f lb = vertices[0].position;
        Vector2f tr = vertices[1].position;

        float width = Math.abs(tr.x-lb.x);

        tr.y = lb.y/ratio;

        tr.x -= lb.x;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        setUV();
    }

    public void setUV() {
        for (int i = 0; i < getVertices().length; i++)
            getVertices()[i].uvCoord = sprite.getUvCoords()[i];
    }
}