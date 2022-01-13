package dev.elk.scaffold.components.cameras;

import dev.elk.scaffold.components.player.Parentable;
import dev.elk.scaffold.gl.Component;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * The camera is a set of matrices that are multiplied with the vertex position
 * in the vertex shader. This makes the game be rendered from the view of a camera.<br>
 * The camera defined in this class scales defines the scale of the game to go
 * from -20x to 20x and -20y to 20y.<br>
 * An error arising through scaling the window led to content being stretched in a way not
 * acceptable. This was solved through the definition of another factor the xy ratio of the
 * window size. This is used in the shader
 * @author Louis Schell, heavily inspired by GamesWithGabe Yt
 */
public class Camera implements Component {

    protected final Matrix4f projectionMatrix;
    protected final Matrix4f viewMatrix;
    protected final float projectionWidth = 20;
    protected final float projectionHeight = 20;
    protected final Vector2f projectionSize = new Vector2f(projectionWidth, projectionHeight);
    public Vector2f position;
    protected Matrix4f inverseProjection;
    protected Matrix4f inverseView;
    protected float zoom;
    private Parentable parentable;

    public Camera(Vector2f position, float zoom) {
        this.zoom = zoom;
        this.position = position;
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        this.inverseProjection = new Matrix4f();
        this.inverseView = new Matrix4f();
        adjustProjection();
    }

    public void parentTo(Parentable parentable) {
        this.parentable = parentable;
    }

    public void adjustProjection() {
        projectionMatrix.identity();
        projectionMatrix.ortho(
                -projectionSize.x * this.zoom,
                projectionSize.x * this.zoom,
                -projectionSize.y * this.zoom,
                projectionSize.y * zoom,
                0.0f, 100.0f);
        inverseProjection = new Matrix4f(projectionMatrix).invert();
    }

    public Matrix4f getViewMatrix() {
        Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
        Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
        viewMatrix.identity();
        viewMatrix.lookAt(new Vector3f(position.x, position.y, 20.0f),
                cameraFront.add(position.x, position.y, 0.0f),
                cameraUp);
        inverseView = new Matrix4f(this.viewMatrix).invert();

        return this.viewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return this.projectionMatrix;
    }

    public Matrix4f getInverseProjection() {
        return this.inverseProjection;
    }

    public Matrix4f getInverseView() {
        return this.inverseView;
    }

    public Vector2f getProjectionSize() {
        return this.projectionSize;
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }

    public void addZoom(float value) {
        this.zoom += value;
    }

    @Override
    public void update() {
        if (parentable != null) {
            this.position = parentable.getPosition();
        }
    }
}

