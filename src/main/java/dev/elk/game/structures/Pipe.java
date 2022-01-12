package dev.elk.game.structures;

import dev.elk.scaffold.physics.CollidableStructure;
import dev.elk.scaffold.gl.Quad;
import dev.elk.scaffold.gl.TexturedQuad;
import dev.elk.scaffold.renderer.Spritesheet;
import org.joml.Vector2f;

public class Pipe implements CollidableStructure {

    Vector2f position;
    float gapSize;
    float gapPosition;
    final float pipeWidth = 3f;

    public final TexturedQuad[] quads = new TexturedQuad[2];

    public Pipe(Vector2f position, float gapPosition, float gapSize) {
        this.gapSize = gapSize;
        this.gapPosition = gapPosition;
        this.position = position;
        generatePipes();
    }

    private void generatePipes() {
//        quads[0] = new TexturedQuad(
//                //new Vector2f(position),
//                new Vector2f(position.x, gapPosition-Spritesheet.staticSprites.get("pipe").getYDifference()),
//                new Vector2f(position.x + pipeWidth, gapPosition),
//                //new Vector2f(position.x + pipeWidth, gapPosition/Spritesheet.staticSprites.get("pipe").getWidthHeightRatio()),    //TODO fix this
//                Spritesheet.staticSprites.get("pipe")
//        );
        quads[0] = new TexturedQuad(
                new Vector2f(position),
                pipeWidth,
                Spritesheet.staticSprites.get("pipe")
        );

        quads[1] = new TexturedQuad(
                new Vector2f(position.x, gapPosition + gapSize),
                pipeWidth,
                Spritesheet.staticSprites.get("pipe")
        );
        quads[1].translate(new Vector2f(0,quads[1].getHeight()));
        quads[1].flipX(quads[1].center());
    }


    @Override
    public Quad[] getQuads() {
        return quads;
    }
}