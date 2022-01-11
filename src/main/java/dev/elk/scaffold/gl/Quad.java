package dev.elk.scaffold.gl;

import dev.elk.scaffold.gl.bindings.Vertex;
import dev.elk.scaffold.util.Utils;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.awt.*;

/**
 * Gennerates a basic quad constructable either from Vertices or from Key points.
 * This allows for the construction of multiple geometric shapes that are made up of such quads
 *
 * @author Felix Kunze
 * @author Louis Schell
 */
public class Quad implements Geometry {

    private final Vertex[] vertices = new Vertex[4];

    public Quad(Vector2f posLB, Vector2f posTR) {
        this(
                posLB,
                posTR,
                Utils.FALLBACK_COLOR);
    }

    public Quad(Vector2f posLB, Vector2f posTR, Color color) {
        this(
                posLB,
                new Vector2f(posLB).add(new Vector2f(posTR.x - posLB.x, 0)),
                posTR,
                new Vector2f(posLB).add(new Vector2f(0, posTR.y - posLB.y)),
                color);
    }

    public Quad(Vector2f pos1, Vector2f pos2, Vector2f pos3, Vector2f pos4, Color color) {
        this(
                pos1,
                pos2,
                pos3,
                pos4,
                color,
                new Vector2f[]{Utils.INVALID_TEXTURE_COORDS, Utils.INVALID_TEXTURE_COORDS,
                        Utils.INVALID_TEXTURE_COORDS, Utils.INVALID_TEXTURE_COORDS}
        );
    }

    protected Quad(Vector2f pos1, Vector2f pos2, Vector2f pos3, Vector2f pos4, Color color, Vector2f[] texCoords) {
        this(pos1, pos2, pos3, pos4, color, texCoords, 0);

    }

    protected Quad(Vector2f pos1, Vector2f pos2, Vector2f pos3, Vector2f pos4, Color color, Vector2f[] texCoords, int texId) {
        Vector3f colorVec = new Vector3f(color.getRGBColorComponents(null));
        vertices[0] = new Vertex(new Vector2f(pos1), colorVec, texCoords[0], texId);
        vertices[1] = new Vertex(new Vector2f(pos2), colorVec, texCoords[1], texId);
        vertices[2] = new Vertex(new Vector2f(pos3), colorVec, texCoords[2], texId);
        vertices[3] = new Vertex(new Vector2f(pos4), colorVec, texCoords[3], texId);
    }

    @Override
    public Vertex[] getVertices() {
        return vertices;
    }

    @Override
    public int[] getIndices() {
        return new int[]{0, 1, 2, 2, 3, 0};
    }
}
