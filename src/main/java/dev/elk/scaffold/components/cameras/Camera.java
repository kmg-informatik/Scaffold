package dev.elk.scaffold.components.cameras;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * Camera object. Code copied from GamesWithGabe.
 */
public class Camera {

    private final Matrix4f projectionMatrix;
    private final Matrix4f viewMatrix;
    public Vector2f position;
    protected final float defaultZoom;
    protected float zoom;

    public Camera(Vector2f position) {
        this.position = position;
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        this.defaultZoom = 1f;
        this.zoom = defaultZoom;
        adjustProjection();
    }

    public Camera(Vector2f position, float defaultZoom) {
        this.position = position;
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        this.defaultZoom = defaultZoom;
        this.zoom = defaultZoom;
        adjustProjection();
    }

    public void adjustProjection() {
        projectionMatrix.identity();
        projectionMatrix.ortho(
                0f,
                160f,
                0f,
                100f,
                0.0f,
                100.0f
        );
        projectionMatrix.scaleXY(zoom, zoom);
    }

    public Matrix4f getViewMatrix() {
        Vector3f cameraFront = new Vector3f(0.0f,0.0f,-1.0f);
        Vector3f cameraUp = new Vector3f(0.0f,1.0f,0.0f);

        Vector3f eye = new Vector3f(position.x,position.y,20.0f);
        cameraFront.add(position.x, position.y,20.0f);

        viewMatrix.identity();
        viewMatrix.lookAt(eye,cameraFront, cameraUp);

        return viewMatrix;
    }

    public Vector2f getNextPosition(Vector2f newPos){
        return newPos;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
}

