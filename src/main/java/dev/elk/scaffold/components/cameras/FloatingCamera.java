package dev.elk.scaffold.components.cameras;

import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

/**
 * The floating camera inherits all functionalities of the normal camera. What makes
 * it special is, that it stores the last positions of the camera. This means that one
 * can create an average of the positions and calculate the next one, leading to a smoother
 * camera feel, that makes it "feel floaty".
 * @author Louis Schell
 */
public class FloatingCamera extends Camera {

    private final List<Vector2f> lastPositions = new ArrayList<>();
    private final int originalSampleSize;
    private final float yMultiplierOriginal = 0.2f;
    private int currentSampleSize;
    private float yMultiplier = yMultiplierOriginal;

    public FloatingCamera(Vector2f position, int originalSampleSize) {
        this(position, 1f, originalSampleSize);
    }

    public FloatingCamera(Vector2f position, float zoom, int originalSampleSize) {
        super(position, zoom);
        this.originalSampleSize = originalSampleSize;
        for (int i = 0; i < originalSampleSize; i++) {
            lastPositions.add(new Vector2f());
        }
        currentSampleSize = originalSampleSize;
    }

    /**
     * Calculates next floating position of the camera
     * @param parentPos the position to take in
     * @return new position
     */
    public Vector2f getNextPosition(Vector2f parentPos) {
        lastPositions.remove(0);
        lastPositions.add(parentPos);

        Vector2f avg = new Vector2f();
        float scalarTotal = 0;
        for (int i = originalSampleSize - currentSampleSize; i < currentSampleSize; i++) {
            Vector2f lastPosition = new Vector2f(lastPositions.get(i));

            float scalar = (float) ((double) (1) / (currentSampleSize - i));
            scalarTotal += scalar;
            avg.add(lastPosition.mul(scalar));
        }
        avg.div(scalarTotal);
        avg.y *= yMultiplier;

        yMultiplier = (float) (Math.round(yMultiplier * 1000.0) / 1000.0);

        if (Math.abs(this.position.y - parentPos.y) > 2.0) {
            yMultiplier += 0.01;
        } else {
            if (yMultiplier > yMultiplierOriginal)
                yMultiplier -= 0.01;
        }
        adjustProjection();
        return avg;
    }

    /**
     * Sets the sample size of how many positions to average.
     */
    public void setCurrentSampleSize(int currentSampleSize) {
        this.currentSampleSize = currentSampleSize;
    }
}
