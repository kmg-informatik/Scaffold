package dev.elk.scaffold.components.cameras;

import org.joml.Vector2f;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class FloatingCamera extends Camera {

    private final List<Vector2f> lastPositions = new ArrayList<>();
    private final int capacity;

    public FloatingCamera(Vector2f position, int capacity) {
        super(position);
        this.capacity = 20;
        for (int i = 0; i < capacity; i++) {
            lastPositions.add(new Vector2f());
        }
    }

    public FloatingCamera(Vector2f position, float zoom, int capacity) {
        super(position, zoom);
        this.capacity = 20;
        for (int i = 0; i < capacity; i++) {
            lastPositions.add(new Vector2f());
        }
    }

    @Override
    public Vector2f getNextPosition(Vector2f parentPos) {
        lastPositions.remove(0);
        lastPositions.add(parentPos);

        Vector2f avg = new Vector2f();
        float scalarTotal = 0;
        for (int i = 0; i < lastPositions.size(); i++) {
            Vector2f lastPosition = new Vector2f(lastPositions.get(i));
            float scalar = (lastPositions.size())/(float) (lastPositions.size()-i);
            scalarTotal += scalar;
            avg.add(lastPosition.mul(scalar));
        }
        avg.div(scalarTotal);
        avg.y *= 0.2;
        //avg.y = 0.4f* avg.y + 0.4f*(averageMovement().y + lastPositions.get(lastPositions.size()-1).y) + 0.2f*parentPos.y;

        adjustProjection();
        return avg;
    }

    private Vector2f averageMovement(){
        Vector2f avgMovement = new Vector2f();
        float scalarTotal = 0;
        for (int i = 1, lastPositionsSize = lastPositions.size(); i < lastPositionsSize; i++) {
            Vector2f move = new Vector2f(lastPositions.get(i)).sub(lastPositions.get(i-1));
            float scalar = (lastPositions.size())/(float) (lastPositions.size()-i);
            scalarTotal += scalar;
            move.mul(scalar);
            avgMovement.add(move);
        }
        avgMovement.div(scalarTotal);
        return avgMovement;
    }
}
