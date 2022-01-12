package dev.elk.game.structures;

import dev.elk.scaffold.physics.CollidableStructure;
import dev.elk.scaffold.gl.Quad;
import dev.elk.scaffold.gl.TexturedQuad;
import dev.elk.scaffold.renderer.Spritesheet;
import org.joml.Vector2f;

public class Pipe implements CollidableStructure {

    private float gapSize;
    private float gapPosition;
    private float posX;
    final float pipeWidth = 3f;

    public final TexturedQuad[] quads = new TexturedQuad[2];

    public Pipe(float posX, float gapPosition, float gapSize) {
        this.gapSize = gapSize;
        this.posX = posX;
        this.gapPosition = gapPosition;
        generatePipes();
    }

    private void generatePipes() {
        quads[0] = new TexturedQuad(
                new Vector2f(posX, gapPosition - gapSize / 2),
                pipeWidth,
                Spritesheet.staticSprites.get("pipe")
        );

        quads[1] = new TexturedQuad(
                new Vector2f(posX, gapPosition + gapSize / 2),
                pipeWidth,
                Spritesheet.staticSprites.get("pipe")
        );
        quads[1].flipX(quads[1].center());
       quads[0].translate(new Vector2f(0, -quads[0].getHeight()));
    }


    @Override
    public Quad[] getQuads() {
        return quads;
    }
}