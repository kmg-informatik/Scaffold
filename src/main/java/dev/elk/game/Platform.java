package dev.elk.game;

import dev.elk.scaffold.gl.Geometry;
import dev.elk.scaffold.gl.TexturedQuad;
import dev.elk.scaffold.gl.TexturedSquare;
import dev.elk.scaffold.gl.bindings.Vertex;
import dev.elk.scaffold.renderer.Spritesheet;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.Random;

public class Platform implements Geometry {

    public static final ArrayList<Platform> platforms = new ArrayList<>();

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
                new Vector2f(position.x + 3 * tileSize, position.y+tileSize),
                tileSize,
                2*tileSize,
                Spritesheet.STATIC_SPRITES.get(treeTypes[new Random().nextInt(treeTypes.length)])
        );

    }

    public TexturedSquare[] generateBasePlatform() {

        TexturedSquare[] platformBase = new TexturedSquare[new Random().nextInt(6) + 4];
        platformBase[0] = new TexturedSquare(
                new Vector2f(position.x , position.y ),
                tileSize,
                Spritesheet.STATIC_SPRITES.get("grass2RightBottom")
        );
        for (int i = 1; i < platformBase.length - 1; i++) {
            platformBase[i] = new TexturedSquare(
                    new Vector2f(position.x + tileSize * (i), position.y ),
                    tileSize,
                    Spritesheet.STATIC_SPRITES.get("grassTop")
            );
        }

        platformBase[platformBase.length -1] = new TexturedSquare(
                new Vector2f(position.x + tileSize * (platformBase.length - 1), position.y ),
                tileSize,
                Spritesheet.STATIC_SPRITES.get("grass2LeftBottom")
        );
        return platformBase;
    }

    @Override
    public int[] getIndices() {
        int[] indices = new int[quads.length*6];
        int addTo = 0;
        for (int i = 0; i < quads.length; i++) {
            indices[i*6] = i + addTo;
            indices[i*6+1] = i+1 + addTo;
            indices[i*6+2] = i+2 + addTo;
            indices[i*6+3] = i+2 + addTo;
            indices[i*6+4] = i+3 + addTo;
            indices[i*6+5] = i + addTo;
            addTo+=3;
        }
        return indices;
    }

    @Override
    public Vertex[] getVertices() {
        Vertex[] vertices = new Vertex[quads.length<<2];
        for (int i = 0; i < vertices.length; i+=4) {
            vertices[i] = quads[i>>2].getVertices()[0];
            vertices[i+1] = quads[i>>2].getVertices()[1];
            vertices[i+2] = quads[i>>2].getVertices()[2];
            vertices[i+3] = quads[i>>2].getVertices()[3];
        }
        return vertices;
    }

    public TexturedQuad[] getPlatformBase() {
        TexturedQuad[] arr = new TexturedQuad[quads.length -1];
        System.arraycopy(quads, 0, arr, 0, arr.length);
        return arr;
    }

    public float getFloorHeight() {
        return position.y + tileSize;
    }
}
