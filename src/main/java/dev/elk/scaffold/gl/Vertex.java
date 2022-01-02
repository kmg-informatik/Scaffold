package dev.elk.scaffold.gl;

import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * Vertices are blobs of data, that are used by OpenGL
 * to store information about each point of a geometry. <p>
 * Vertices are made up of floats but more generally every
 * type of data can be stored.
 * @author Louis Schell
 */
public class Vertex {

    public Vector2f position;
    public Vector3f color;

    public Vertex(Vector2f position, Vector3f rgb){
        this.color = rgb;
        this.position = position;
    }

    public float[] getFloats(){
        return new float[]{position.x, position.y, color.x, color.y, color.z};
    }


    @Override
    public String toString() {
        return "Vertex{" +
                "position=" + position +
                ", color=" + color +
                '}';
    }
}
