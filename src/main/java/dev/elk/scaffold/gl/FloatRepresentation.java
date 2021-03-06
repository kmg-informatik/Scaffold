package dev.elk.scaffold.gl;

/**
 * Forces subclasses to provide a representation
 * as floats, needed specifically for OpenGL. Probably
 * going to get rid of this anyway, it belongs into the
 * {@link Renderable} interface.
 *
 * @author Louis Schell
 * @author Felix Kunze
 */
public interface FloatRepresentation {

    /**
     * Returns a representation as floats.
     */
    float[] intoFloats();

}
