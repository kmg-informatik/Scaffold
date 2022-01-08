package dev.elk.scaffold.components.cameras;

import org.joml.Vector2f;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class AveragingCamera extends Camera {

    private final List<Vector2f> lastPositions = new ArrayList<>();
    private final int capacity;

    public AveragingCamera(Vector2f position, int capacity) {
        super(position);
        this.capacity = capacity;
        for (int i = 0; i < capacity; i++) {
            lastPositions.add(new Vector2f());
        }
    }

    @Override
    public Vector2f getNextPosition(Vector2f newPos, float dt) {
        if (lastPositions.size() >= capacity){
            lastPositions.remove(0);
        }
        lastPositions.add(newPos);

        Vector2f avg = new Vector2f();
        for (Vector2f lastPosition : lastPositions) {
            avg.add(lastPosition);
        }
        avg.div(lastPositions.size());
        //avg.y = (newPos.y*0.2f + avg.y + 0.8f)/2f;
        //avg.x = (newPos.x + avg.x + 0.2f)/2f;
        return avg;
    }
}
