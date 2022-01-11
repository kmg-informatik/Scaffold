package dev.elk.scaffold.gl;

import dev.elk.scaffold.gl.bindings.Vertex;

/**
 * All things that are to be rendered must include the
 * renderable interface. It forces subclasses to provide
 * indices and vertices needed by OpenGL.
 *
 * @author Louis Schell
 * @author Felix Kunze
 */
public interface Renderable extends FloatRepresentation {

    /**
     * Provides the indices associated with the vertices of
     * the Renderable.
     */
    int[] getIndices();

    /**
     * Returns the vertices of the Renderable.
     */
    Vertex[] getVertices();

    /**
     * Returns the count of vertices.
     */
    default int vertexCount() {
        return getVertices().length;
    }

}
