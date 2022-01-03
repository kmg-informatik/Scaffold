package dev.elk.scaffold.gl;

import dev.elk.scaffold.util.Utils;
import org.joml.Vector2f;

/**
 * Methods concerning geometry.
 * @author Louis Schell
 * @author Felix Kunze
 */
public interface Geometry extends Renderable{

    default Vector2f centerOfMass(){
        Vector2f pos = new Vector2f();
        for (Vertex vertex : getVertices()) {
            pos.add(vertex.position);
        }
        pos.div(getVertices().length);
        return pos;
    }

    default void rotate(float radians){
        Vector2f pivot = centerOfMass();
        for (Vertex vertex : getVertices()) {
            Utils.rotate(vertex.position, pivot, radians);
        }
    }

    default float getHeight(){
        Vertex[] vertices = getVertices();
        float[] yPos = new float[vertices.length];

        for (int i = 0; i < vertices.length; i++)  yPos[i] = vertices[i].position.y;

        float[] minMax = Utils.findMinMaxElem(yPos);
        return minMax[1]- minMax[0];
    }

    default float getWidth(){
        Vertex[] vertices = getVertices();
        float[] xPos = new float[vertices.length];

        for (int i = 0; i < vertices.length; i++) xPos[i] = vertices[i].position.x;

        float[] minMax = Utils.findMinMaxElem(xPos);
        return minMax[1]- minMax[0];
    }


    Vector2f getPosition();

    void moveTo(Vector2f pos);

    void moveBy(Vector2f mov);


}
