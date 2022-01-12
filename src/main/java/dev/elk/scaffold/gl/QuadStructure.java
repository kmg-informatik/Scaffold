package dev.elk.scaffold.gl;

import dev.elk.scaffold.gl.bindings.Vertex;

public interface QuadStructure extends Geometry {


    Quad[] getQuads();

    default int[] getIndices() {
        int[] indices = new int[getQuads().length * 6];
        int addTo = 0;
        for (int i = 0; i < getQuads().length; i++) {
            indices[i * 6] = i + addTo;
            indices[i * 6 + 1] = i + 1 + addTo;
            indices[i * 6 + 2] = i + 2 + addTo;
            indices[i * 6 + 3] = i + 2 + addTo;
            indices[i * 6 + 4] = i + 3 + addTo;
            indices[i * 6 + 5] = i + addTo;
            addTo += 3;
        }
        return indices;
    }

    default Vertex[] getVertices() {
        Vertex[] vertices = new Vertex[getQuads().length << 2];
        for (int i = 0; i < vertices.length; i += 4) {
            vertices[i] = getQuads()[i >> 2].getVertices()[0];
            vertices[i + 1] = getQuads()[i >> 2].getVertices()[1];
            vertices[i + 2] = getQuads()[i >> 2].getVertices()[2];
            vertices[i + 3] = getQuads()[i >> 2].getVertices()[3];
        }
        return vertices;
    }

}
