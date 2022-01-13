package dev.elk.scaffold.components.player;

import dev.elk.scaffold.components.userinput.KeyHandler;
import dev.elk.scaffold.gl.Window;
import dev.elk.scaffold.renderer.AnimatedSprite;
import org.joml.Vector2f;

import static dev.elk.scaffold.util.Utils.*;

/**
 * The Base human controlled Player in a game
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
    private boolean playerDied = false;

    public boolean isDead() {
        return playerDied;
    }

    /**
     * Runs an update loop on player Handling
     */
    @Override
    public void update() {

        if (!hasCollision() && !playerDied){
            fall();

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
        }else {
            playerDied = true;
            fallNoCollide(-30f);
        }

    }

    /**
     * What the player does when moving right
     */
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

    /**
     * Goes to the next frame in the animation and sets the UV Corrds accordingly
     */
    public void nextFrame() {
        ((AnimatedSprite) getSprite()).nextFrame();
        setUV();
    }

    /**
     * Determines a change in Gravity allowing to switch animations
     * when switching from falling to climbing
     */
    public void checkGravityChange() {
        if (Math.signum(getCurrentGravity()) != Math.signum(lastGravity))
            if(getCurrentGravity() > 0)
                action(ActionType.UP);
            else action(ActionType.DOWN);

    }

    /**
     * Action that is extended by other classes
     * @param actionType The type of action
     */
    public void action(ActionType actionType) {}

    /**
     * The type of action, this is used by player extending classes to determine actinons
     */
    protected enum ActionType {
        UP, DOWN, LEFT, RIGHT;
    }

}