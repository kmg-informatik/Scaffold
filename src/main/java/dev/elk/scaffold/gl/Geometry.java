package dev.elk.scaffold.gl;

import dev.elk.scaffold.util.Utils;
import org.joml.Vector2f;

import java.util.Arrays;
import java.util.stream.IntStream;

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
    default Vector2f getOrigin(){
        return getVertices()[0].position;
    }

    /**
     * Moves the geometry to the given coordinates.
     * @apiNote The vertex provided from {@link #getOrigin()} will be translated
     * to the given coordinates.
     * @param pos the coordinates to move to
     */
    default void translateTo(Vector2f pos) {
        Vector2f translationVector = new Vector2f(pos).sub(getOrigin());
        translate(translationVector);
    }

    /**
     * Translates the geometry by the given vector.
     * @param vector the vector to move with
     */
    default void translate(Vector2f vector) {
        if (vector.x != 0.0f || vector.y != 0.0f)System.out.println(vector);
        for (Vertex vertex : getVertices()) {
            vertex.position.add(vector);
        }
    }

    /**
     * Flips the geometry on the x-axis through the center of the geometry.
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

    /**
     * Flips the geometry on the y-axis through the center of the geometry.
     */
    default void flipY(){
        Vertex[] vertices = getVertices();
        var center = centerOfMass();
        translate(new Vector2f(-center.x, 0));

        for (Vertex vertex : vertices) {
            vertex.position.x = -vertex.position.x;
        }

        translate(new Vector2f(center.x, 0));
    }

    default float getMaxY() {
        float maxY = Float.NEGATIVE_INFINITY;
        for (Vertex vertex : getVertices()) {
            if (maxY < vertex.position.y) {
                maxY = vertex.position.y;
            }
        }
        return maxY;
    }

    default float getMinY() {
        float minY = Float.POSITIVE_INFINITY;
        for (Vertex vertex : getVertices())
            if (minY > vertex.position.y)
                minY = vertex.position.y;
        return minY;
    }

    default float getMaxX() {
        float maxX = Float.NEGATIVE_INFINITY;
        for (Vertex vertex : getVertices())
            if (maxX < vertex.position.x)
                maxX = vertex.position.x;
        return maxX;
    }

    default float getMinX() {
        float minX = Float.POSITIVE_INFINITY;
        for (Vertex vertex : getVertices())
            if (minX > vertex.position.x)
                minX = vertex.position.x;
        return minX;
    }

    /**
     * Checks, if the geometry is at least partly in the bounds of the window
     * @param yxRatio the ration of width to height
     * @return true, if geometry is in bounds of window
     */
    default boolean isOnScreen(float yxRatio){
        Vertex[] vertices = getVertices();
        for (Vertex vertex : vertices) {
            if (Math.abs(vertex.position.x) * yxRatio < 1f  && Math.abs(vertex.position.y) < 1f){
                return true;
            }
        }
        return false;
    }

    @Override
    default float[] intoFloats() {
        Vertex[] vertices = getVertices();
        float[] allVertexData = new float[vertices.length * Vertex.STRIDE];

        for (int i = 0; i < vertices.length; i++)
            System.arraycopy(vertices[i].intoFloats(), 0, allVertexData, Vertex.STRIDE * i, Vertex.STRIDE);

        return allVertexData;
    }

    /**
     * Checks if a Geometry intersects with another Geometry
     * @param geometries
     * @return
     */
    default boolean intersects(Geometry... geometries) {
        return Arrays.stream(geometries).anyMatch(geometry ->
                this.getMinX() < geometry.getMaxX() &&
                this.getMaxX() > geometry.getMinX() &&
                this.getMaxY() > geometry.getMinY() &&
                this.getMinY() < geometry.getMaxY()
        );
    }



}
