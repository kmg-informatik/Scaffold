package dev.elk.scaffold.components;

import dev.elk.scaffold.gl.Geometry;
import dev.elk.scaffold.gl.Vertex;

import java.util.LinkedList;

public class MeshRepository {

    private static final LinkedList<Geometry> geometries = new LinkedList<>();
    private static final float[] vertexArray = new float[2000];
    private static final int[] elementArray = new int[750];
    private static int vertexCount = -1;

    public static void update(){

        int vCount = 0;
        int eCount = 0;

        for (Geometry geometry : geometries) {
            var indices = geometry.getIndices();
            for (int i = 0; i < indices.length; i++) {
                indices[i] += vCount/Vertex.STRIDE;
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

        vertexCount = vCount;
    }

    public static int vertexCount(){
        return vertexCount;
    }

    public static void put(Geometry geometry){
        geometries.add(geometry);
    }

    public static float[] getVertexArray() {
        return vertexArray;
    }

    public static int[] getElementArray() {
        return elementArray;
    }
}
