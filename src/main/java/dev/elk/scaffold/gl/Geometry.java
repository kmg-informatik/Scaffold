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
    };

    default void rotate(float radians){
        Vector2f pivot = centerOfMass();
        for (Vertex vertex : getVertices()) {
            Utils.rotate(vertex.position, pivot, radians);
        }
    }

    float getHeight();
    float getWidth();
    Vector2f getPosition();

    void moveTo(Vector2f pos);

    void moveBy(Vector2f mov);


}
