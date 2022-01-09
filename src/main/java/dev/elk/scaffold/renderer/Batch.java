package dev.elk.scaffold.renderer;

import dev.elk.scaffold.gl.Geometry;
import dev.elk.scaffold.gl.Vertex;

import java.util.*;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.*;

public class Batch {

    private final List<Geometry> geometries;
    private final float[] vertexArray;
    private final int[] elementArray;
    private int vertexCount = -1;

    public Batch(int estimatedMeshes, int vertexArraySize, int elementArraySize){
        geometries = new ArrayList<>(estimatedMeshes);
        vertexArray = new float[vertexArraySize];
        elementArray = new int[elementArraySize];
    }

    public void update() {

        int vCount = 0; //The count of vertex floats
        int eCount = 0; //The count of element floats

        for (Geometry geometry : geometries) {
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

    public void render(){
        update();
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, 0, elementArray);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertexArray);
        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
    }

    public int vertexCount() {
        return vertexCount;
    }

    public void put(Geometry geometry) {
        geometries.add(geometry);
    }

    public void putAll(Geometry... geometries) {
        this.geometries.addAll(Arrays.stream(geometries).toList());
    }

    public float[] getVertexArray() {
        return vertexArray;
    }

    public int[] getElementArray() {
        return elementArray;
    }

    public List<Geometry> getGeometries() {
        return geometries;
    }

}
