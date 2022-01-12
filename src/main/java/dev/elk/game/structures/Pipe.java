package dev.elk.game.structures;

import dev.elk.scaffold.Physics.CollidableStructure;
import dev.elk.scaffold.gl.Quad;
import dev.elk.scaffold.gl.TexturedQuad;
import dev.elk.scaffold.renderer.Spritesheet;
import org.joml.Vector2f;

public class Pipe implements CollidableStructure {

    Vector2f position;
    float gapSize;
    float gapPosition;
    float pipeWidth = 3f;

    public final TexturedQuad[] quads = new TexturedQuad[2];

    public Pipe(Vector2f position, float gapPosition, float gapSize) {
        this.gapSize = gapSize;
        this.gapPosition = gapPosition;
        this.position = position;
        generatePipes();
    }

    private void generatePipes() {
        quads[0] = new TexturedQuad(
                new Vector2f(position),
                new Vector2f(position.x + pipeWidth, gapPosition),
                Spritesheet.staticSprites.get("pipe")
        );

        quads[1] = new TexturedQuad(
                new Vector2f(position.x, gapPosition + gapSize),
                new Vector2f(position.x + pipeWidth, position.y *-1),
                Spritesheet.staticSprites.get("pipe")
        );
        quads[1].flipX(quads[1].center());
    }


    @Override
    public Quad[] getQuads() {
        return quads;
    }
}