package dev.elk.scaffold.gl;

import dev.elk.scaffold.gl.bindings.Vertex;
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
    default Vector2f center() {
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
    default void rotateOrigin(float radians) {
        rotate(radians, getOrigin());
    }

    /**
     * Rotates the geometry around its center by the given amount of radians
     * @apiNote {@link org.joml.Math} provides a method to convert from
     * degrees to radians
     * @see #rotate(float, Vector2f)
     * @param radians number of radians
     */
    default void rotateCenter(float radians){
        rotate(radians, center());
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

    default void translateCenterTo(Vector2f pos) {
        translateTo(pos, center());
    }
    default void translateOriginTo(Vector2f pos) {
        translateTo(pos, getOrigin());
    }

    default void translateTo(Vector2f pos, Vector2f point) {
        Vector2f translationVector = new Vector2f(pos).sub(point);
        translate(translationVector);
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
     * Flips the geometry on the x-axis through the center of the geometry.
     */
    default void flipX(Vector2f flipAt){
        Vertex[] vertices = getVertices();
        translate(new Vector2f(0, -flipAt.y));

        for (Vertex vertex : vertices) {
            vertex.position.y = -vertex.position.y;
        }

        translate(new Vector2f(0, flipAt.y));
    }

    /**
     * Flips the geometry on the y-axis through the center of the geometry.
     */
    default void flipY(Vector2f flipAt){
        Vertex[] vertices = getVertices();
        translate(new Vector2f(-flipAt.x, 0));

        for (Vertex vertex : vertices) {
            vertex.position.x = -vertex.position.x;
        }

        translate(new Vector2f(flipAt.x, 0));
    }

    /**
     * @return the highest y-component of the geometry.
     */
    default float getMaxY() {
        Vertex[] vertices = getVertices();
        float maxY = vertices[0].position.y;
        for (int i = 1, verticesLength = vertices.length; i < verticesLength; i++) {
            Vertex vertex = vertices[i];
            if (maxY < vertex.position.y) {
                maxY = vertex.position.y;
            }
        }
        return maxY;
    }

    /**
     * @return the lowest y-component of the geometry.
     */
    default float getMinY() {
        Vertex[] vertices = getVertices();
        float minY = vertices[0].position.y;
        for (int i = 1, verticesLength = vertices.length; i < verticesLength; i++) {
            Vertex vertex = vertices[i];
            if (minY > vertex.position.y)
                minY = vertex.position.y;
        }
        return minY;
    }

    /**
     * @return the highest x-component of the geometry.
     */
    default float getMaxX() {
        Vertex[] vertices = getVertices();
        float maxX = vertices[0].position.x;
        for (int i = 1, verticesLength = vertices.length; i < verticesLength; i++) {
            Vertex vertex = vertices[i];
            if (maxX < vertex.position.x)
                maxX = vertex.position.x;
        }
        return maxX;
    }

    /**
     * @return the lowest x-component of the geometry.
     */
    default float getMinX() {
        Vertex[] vertices = getVertices();
        float minX = vertices[0].position.x;
        for (int i = 1, verticesLength = vertices.length; i < verticesLength; i++) {
            Vertex vertex = vertices[i];
            if (minX > vertex.position.x)
                minX = vertex.position.x;
        }
        return minX;
    }

    /**
     * Scales the geometry from its origin point by a given scalar
     * @param scalar the scalar to scale with
     */
    default void scaleOrigin(float scalar){
        scale(scalar, getOrigin());
    }

    /**
     * Scales the geometry from its center point by a given scalar
     * @param scalar the scalar to scale with
     */
    default void scaleCenter(float scalar){
        scale(scalar, center());
    }

    /**
     * Scales the geometry from a given point by a given scalar
     * @param scalar the scalar to scale with
     */
    default void scale(float scalar, Vector2f origin){
        Vertex[] vertices = getVertices();
        for (Vertex vertex : vertices) {
            vertex.position.sub(origin);
            vertex.position.mul(scalar);
            vertex.position.add(origin);
        }
    }

    /**
     * Turns the vertices of the mesh into an array of floats.
     * @return vertices of mesh as an array of floats
     */
    @Override
    default float[] intoFloats() {
        Vertex[] vertices = getVertices();
        float[] allVertexData = new float[vertices.length * Vertex.STRIDE];

        for (int i = 0; i < vertices.length; i++)
            System.arraycopy(vertices[i].intoFloats(), 0, allVertexData, Vertex.STRIDE * i, Vertex.STRIDE);

        return allVertexData;
    }
}
