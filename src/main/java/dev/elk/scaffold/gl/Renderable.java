package dev.elk.scaffold.gl;

/**
 * All things that are to be rendered must include the
 * renderable interface. It forces subclasses to provide
 * indices and vertices needed by OpenGL.
 * @author Louis Schell
 * @author Felix Kunze
 */
public interface Renderable extends FloatRepresentation {

    int[] getIndices();

    Vertex[] getVertices();

    default int vertexCount(){
        return getVertices().length;
    }

}
