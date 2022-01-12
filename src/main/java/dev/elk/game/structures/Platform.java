package dev.elk.game.structures;

import dev.elk.scaffold.physics.CollidableStructure;
import dev.elk.scaffold.gl.Quad;
import dev.elk.scaffold.gl.TexturedQuad;
import dev.elk.scaffold.gl.TexturedSquare;
import dev.elk.scaffold.renderer.Spritesheet;
import org.joml.Vector2f;

import java.util.Random;

/**
 * Platform that generates simple Terrain automatically
 *
 * @author Felix Kunze
 */
public class Platform implements CollidableStructure {

    private float tileSize = 2f;
    private TexturedQuad[] quads;
    private Vector2f position;

    public Platform(Vector2f position) {
        this.position = position;
        TexturedSquare[] platformBase = generateBasePlatform();
        TexturedQuad tree = generateTree();
        quads = new TexturedQuad[platformBase.length + 1];
        System.arraycopy(platformBase, 0, quads, 0, platformBase.length);
        quads[platformBase.length] = tree;
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
        TexturedSquare[] platformBase = new TexturedSquare[new Random().nextInt(6) + 4];
        platformBase[0] = new TexturedSquare(
                new Vector2f(position.x, position.y),
                tileSize,
                Spritesheet.staticSprites.get("grass2RightBottom")
        );
        for (int i = 1; i < platformBase.length - 1; i++) {
            platformBase[i] = new TexturedSquare(
                    new Vector2f(position.x + tileSize * (i), position.y),
                    tileSize,
                    Spritesheet.staticSprites.get("grassTop")
            );
        }

        platformBase[platformBase.length - 1] = new TexturedSquare(
                new Vector2f(position.x + tileSize * (platformBase.length - 1), position.y),
                tileSize,
                Spritesheet.staticSprites.get("grass2LeftBottom")
        );
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
