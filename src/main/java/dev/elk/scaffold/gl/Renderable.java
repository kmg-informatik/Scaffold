package dev.elk.scaffold.gl;

/**
 * All things that are to be rendered must include the
 * renderable interface. It forces subclasses to provide
 * indices and vertices needed by OpenGL.
 * @author Louis Schell
 */
public interface Renderable extends Floatrepresentation {

    int[] getIndices();

    Vertex[] getVertices();

}
