package dev.elk.scaffold.renderer;

import dev.elk.scaffold.gl.Geometry;
import dev.elk.scaffold.gl.bindings.Vertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

/**
 * Handles Batch rendering allowing multiple triangles and textures to be rendered
 *
 * @author Louis Schell
 */
public class Batch<E extends Geometry> {

    private final List<E> geometries;
    private final float[] vertexArray;
    private final int[] elementArray;
    private int vertexCount = -1;

    public Batch(int estimatedMeshes, int vertexArraySize, int elementArraySize) {
        geometries = new ArrayList<>(estimatedMeshes);
        vertexArray = new float[vertexArraySize];
        elementArray = new int[elementArraySize];
    }

    public void vertexCopy() {

        int vCount = 0;
        int eCount = 0;

        for (E geometry : geometries) {
            var indices = geometry.getIndices();
            for (int i = 0; i < indices.length; i++) {
                indices[i] += vCount / Vertex.STRIDE;
            }

            System.arraycopy(
                    geometry.intoFloats(),
                    0,
                    vertexArray,
                    vCount,
                    geometry.intoFloats().length
            );

            System.arraycopy(
                    indices,
                    0,
                    elementArray,
                    eCount,
                    geometry.getIndices().length);

            vCount += geometry.intoFloats().length;
            eCount += geometry.getIndices().length;
        }

        for (int i = vCount; i < vertexArray.length; i++) {
            vertexArray[i] = 0;
        }
        for (int i = eCount; i < elementArray.length; i++) {
            elementArray[i] = 0;
        }

        vertexCount = vCount;
    }

    public void render() {
        vertexCopy();
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, 0, elementArray);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertexArray);
        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
    }

    public int vertexCount() {
        return vertexCount;
    }

    public void put(E geometry) {
        geometries.add(geometry);
    }

    @SafeVarargs
    public final void putAll(E... geometries) {
        this.geometries.addAll(Arrays.stream(geometries).toList());
    }

    public float[] getVertexArray() {
        return vertexArray;
    }

    public int[] getElementArray() {
        return elementArray;
    }

    public List<E> getGeometries() {
        return geometries;
    }
}
