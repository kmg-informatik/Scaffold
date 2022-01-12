package dev.elk.game.structures;

import dev.elk.scaffold.physics.CollidableStructure;
import dev.elk.scaffold.gl.Quad;
import dev.elk.scaffold.gl.TexturedQuad;
import dev.elk.scaffold.gl.TexturedSquare;
import dev.elk.scaffold.renderer.Sprite;
import dev.elk.scaffold.renderer.Spritesheet;
import org.joml.Vector2f;

import java.util.Random;

/**
 * Platform that generates simple Terrain automatically
 *
 * @author Felix Kunze
 */
public class ScreenBounds implements CollidableStructure {

    private float tileSize = 2f;
    private TexturedQuad[] quads;
    private Vector2f position;
    private final boolean isGround;

    public ScreenBounds(Vector2f position, boolean isGround) {
        this.isGround = isGround;
        this.position = position;
        TexturedSquare[] platformBase = generateBasePlatform();
        if (isGround){
            TexturedQuad tree = generateTree();
            quads = new TexturedQuad[platformBase.length + 1];
            quads[platformBase.length] = tree;
        }
        quads = new TexturedQuad[platformBase.length];
        System.arraycopy(platformBase, 0, quads, 0, platformBase.length);
    }

    private TexturedQuad generateTree() {
        String[] treeTypes = {
                "treeCone",
                "treeBranch",
                "treeMango",
                "treeBlock",
                "treeComplicated",
                "treeCurvy",
                "treeStack",
                "treeBubbles",
                "treeLong",
        };
        return new TexturedQuad(
                new Vector2f(position.x + 3 * tileSize, position.y + tileSize),
                tileSize,
                2 * tileSize,
                Spritesheet.staticSprites.get(treeTypes[new Random().nextInt(treeTypes.length)])
        );

    }

    public TexturedSquare[] generateBasePlatform() {
        TexturedSquare[] platformBase = new TexturedSquare[Chunk.CHUNK_SIZE / (int) tileSize];
        for (int i = 0; i < platformBase.length; i++) {
            Sprite sprite = Spritesheet.staticSprites.get(isGround?"grassDown":"grassTop");
            platformBase[i] = new TexturedSquare(
                    new Vector2f(position.x + tileSize * (i), position.y),
                    tileSize,
                    sprite
            );
        }
        return platformBase;
    }

    public float getFloorHeight() {
        return position.y + tileSize;
    }

    @Override
    public Quad[] getCollidableQuads() {
        TexturedQuad[] arr = new TexturedQuad[quads.length - 1];
        System.arraycopy(quads, 0, arr, 0, arr.length);
        return arr;
    }

    @Override
    public Quad[] getQuads() {
        return quads;
    }
}
