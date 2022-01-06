package dev.elk.scaffold.gl;

import dev.elk.scaffold.util.Utils;
import org.joml.Vector2f;

/**
 * Methods concerning geometry.
 *
 * @author Louis Schell
 * @author Felix Kunze
 */
public interface Geometry extends Renderable {

    /**
     * Calculates the center of the Geometry.
     * @apiNote Returns the average point of each Vertex
     */
    default Vector2f centerOfMass() {
        Vector2f pos = new Vector2f();
        for (Vertex vertex : getVertices()) {
            pos.add(vertex.position);
        }
        pos.div(getVertices().length);
        return pos;
    }

    /**
     * Rotates the geometry around the origin by the given amount of radians
     * @apiNote {@link org.joml.Math} provides a method to convert from
     * degrees to radians
     * @see #rotate(float, Vector2f)
     * @param radians number of radians
     */
    default void rotate_origin(float radians) {
        rotate(radians, getOrigin());
    }

    /**
     * Rotates the geometry around its center by the given amount of radians
     * @apiNote {@link org.joml.Math} provides a method to convert from
     * degrees to radians
     * @see #rotate(float, Vector2f)
     * @param radians number of radians
     */
    default void rotate_center(float radians){
        rotate(radians, centerOfMass());
    }

    /**
     * Rotates the geometry around the given pivot point by the given
     * amount of radians
     * @apiNote {@link org.joml.Math} provides a method to convert from
     * degrees to radians
     * @param radians number of radians
     * @param pivot pivot point to rotate around
     */
    default void rotate(float radians, Vector2f pivot) {
        for (Vertex vertex : getVertices()) {
            Utils.rotateVector(vertex.position, pivot, radians);
        }
    }

    /**
     * Measures the maximum height of the geometry.
     * @apiNote Does not return the dimensions of the geometry!
     */
    default float getHeight() {
        Vertex[] vertices = getVertices();
        float[] yPos = new float[vertices.length];

        for (int i = 0; i < vertices.length; i++) yPos[i] = vertices[i].position.y;

        float[] minMax = Utils.findMinMaxElem(yPos);
        return minMax[1] - minMax[0];
    }

    /**
     * Measures the maximum width of the geometry.
     * @apiNote Does not return the dimensions of the geometry!
     */
    default float getWidth() {
        Vertex[] vertices = getVertices();
        float[] xPos = new float[vertices.length];

        for (int i = 0; i < vertices.length; i++) xPos[i] = vertices[i].position.x;

        float[] minMax = Utils.findMinMaxElem(xPos);
        return minMax[1] - minMax[0];
    }

    /**
     * Returns the origin point of the object.
     * @apiNote Not the center of mass, but a defined origin
     */
    Vector2f getOrigin();

    /**
     * Moves the geometry to the given coordinates.
     * @apiNote The vertex provided from {@link #getOrigin()} will be translated
     * to the given coordinates.
     * @param pos the coordinates to move to
     */
    default void translateTo(Vector2f pos) {
        Vector2f translationVector = new Vector2f(pos).sub(getOrigin());
    }

    /**
     * Translates the geometry by the given vector.
     * @param vector the vector to move with
     */
    default void translate(Vector2f vector) {
        for (Vertex vertex : getVertices()) {
            vertex.position.add(vector);
        }
    }

    /**
     * Flip on the x-Axis
     */
    default void flipX(){
        Vertex[] vertices = getVertices();
        var center = centerOfMass();
        translate(new Vector2f(0, -center.y));
        for (Vertex vertex : vertices) {
            vertex.position.y = -vertex.position.y;
        }
        translate(new Vector2f(0, center.y));
    }

    default void flipY(){
        Vertex[] vertices = getVertices();
        var center = centerOfMass();
        translate(new Vector2f(-center.x, 0));

        for (Vertex vertex : vertices) {
            vertex.position.x = -vertex.position.x;
        }

        translate(new Vector2f(center.x, 0));
    }

    @Override
    default float[] intoFloats() {
        Vertex[] vertices = getVertices();
        float[] allVertexData = new float[vertices.length * Vertex.STRIDE];

        for (int i = 0; i < vertices.length; i++)
            System.arraycopy(vertices[i].intoFloats(), 0, allVertexData, Vertex.STRIDE * i, Vertex.STRIDE);

        return allVertexData;
    }
}
