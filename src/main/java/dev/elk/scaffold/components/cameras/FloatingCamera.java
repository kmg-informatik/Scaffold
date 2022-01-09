package dev.elk.scaffold.components.cameras;

import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class FloatingCamera extends Camera {

    private final List<Vector2f> lastPositions = new ArrayList<>();
    private final int originalCapacity;
    private int currentCapacity;

    public FloatingCamera(Vector2f position, int originalCapacity) {
        super(position);
        this.originalCapacity = originalCapacity;
        for (int i = 0; i < originalCapacity; i++) {
            lastPositions.add(new Vector2f());
        }
        currentCapacity = originalCapacity;
    }

    public FloatingCamera(Vector2f position, float zoom, int originalCapacity) {
        super(position, zoom);
        this.originalCapacity = originalCapacity;
        for (int i = 0; i < originalCapacity; i++) {
            lastPositions.add(new Vector2f());
        }
        currentCapacity = originalCapacity;
    }

    final float yMultiplierOriginal = 0.2f;
    float yMultiplier = yMultiplierOriginal;

    @Override
    public Vector2f getNextPosition(Vector2f parentPos) {
        lastPositions.remove(0);
        lastPositions.add(parentPos);

        Vector2f avg = new Vector2f();
        float scalarTotal = 0;
        for (int i = originalCapacity-currentCapacity; i < currentCapacity; i++) {
            Vector2f lastPosition = new Vector2f(lastPositions.get(i));

            float scalar = (float) ((double)(1)/(currentCapacity-i));
            scalarTotal += scalar;
            avg.add(lastPosition.mul(scalar));
        }
        avg.div(scalarTotal);
        avg.y *= yMultiplier;

        yMultiplier = (float) (Math.round(yMultiplier * 1000.0) / 1000.0); // => 1.23

        if (Math.abs(this.position.y - parentPos.y) > 2.0){
            yMultiplier+= 0.01;
        }else{
            if (yMultiplier > yMultiplierOriginal)
                yMultiplier-= 0.01;
        }
        adjustProjection();
        return avg;
    }

    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
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
