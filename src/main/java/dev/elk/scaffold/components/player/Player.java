package dev.elk.scaffold.components.player;

import dev.elk.scaffold.components.userinput.KeyHandler;
import dev.elk.scaffold.gl.Window;
import dev.elk.scaffold.renderer.AnimatedSprite;
import org.joml.Vector2f;

import static dev.elk.scaffold.util.Utils.*;

/**
 * @author Felix Kunze
 * @author Louis Schell
 * @since 11/01/2022
 */
public class Player extends Entity implements Actions, Parentable {

    private boolean facingRight;
    private Vector2f movementVector;
    private int jumpCounter = 0;

    public Player (Vector2f startPosition, float size, AnimatedSprite sprite) {
        super(startPosition, size, sprite);
    }

    @Override
    boolean isAi() {
        return false;
    }

    @Override
    float getMovementSpeed() {
        return 10f;
    }

    private float lastGravity = getCurrentGravity();
    @Override
    public void update() {

        //fall();

        movementVector = new Vector2f();

        if (KeyHandler.isKeyPressed(KEY_W) && jumpCounter % 20 == 0) {
            jump();
            jumpCounter++;
        }
        moveRight();
        this.translate(movementVector);
        if (jumpCounter % 20 !=0 ) {
            jumpCounter++;
        }

        checkGravityChange();
        nextFrame();
        lastGravity = getCurrentGravity();
    }

    @Override
    public void moveRight() {
        movementVector.add(new Vector2f(getMovementSpeed(), 0.0f).mul(Window.dt));
        if (facingRight) {
            flipY(this.center());
            facingRight = false;
        }
    }

    @Override
    public void moveLeft() {
        movementVector.add(new Vector2f(-getMovementSpeed(), 0.0f).mul(Window.dt));
        if (!facingRight) {
            flipY(this.center());
            facingRight = true;
        }
    }

    @Override
    public void jump() {
        setCurrentGravity(10);
    }

    @Override
    public Vector2f getPosition() {
        return center();
    }

    public void nextFrame() {
        ((AnimatedSprite) getSprite()).nextFrame();
        setUV();
    }

    public void checkGravityChange() {
        if (Math.signum(getCurrentGravity()) != Math.signum(lastGravity))
            if(getCurrentGravity() > 0)
                action(ActionType.UP);
            else action(ActionType.DOWN);

    }

    public void action(ActionType actionType) {}

    protected enum ActionType {
        UP, DOWN, LEFT, RIGHT;
    }

}