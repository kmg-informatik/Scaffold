package dev.elk.scaffold.components.player;

import dev.elk.scaffold.components.userinput.KeyHandler;
import dev.elk.scaffold.gl.Window;
import dev.elk.scaffold.renderer.AnimatedSprite;
import static dev.elk.scaffold.util.Utils.*;

import org.joml.Vector2f;

/**
 * @author Louis Schell
 * @since 11/01/2022
 */
public class Player extends Entity implements Actions, Parentable{

    private boolean facingRight;
    private Vector2f movementVector;

    public Player(AnimatedSprite sprite, Vector2f lb, Vector2f tr) {
        super(sprite, lb, tr);
    }

    @Override
    boolean isAi() {
        return false;
    }

    @Override
    float getMovementSpeed() {
        return 2f;
    }

    @Override
    public void update() {

        this.fall();

        movementVector = new Vector2f();

        if (KeyHandler.isKeyPressed(KEY_W)/* && hasGroundContact()*/) {
            jump();
        }
        if (KeyHandler.isKeyPressed(KEY_A) || KeyHandler.isKeyPressed(KEY_D)) {
            if (KeyHandler.isKeyPressed(KEY_A)) {
                moveLeft();
            } else {
                moveRight();
            }
        }
        this.translate(movementVector);
    }

    @Override
    public void moveRight() {
        movementVector.add(new Vector2f(getMovementSpeed(), 0.0f).mul(Window.dt));
        if (!facingRight) {
            flipY(this.center());
            facingRight = true;
        }
    }

    @Override
    public void moveLeft() {
        movementVector.add(new Vector2f(-getMovementSpeed(), 0.0f).mul(Window.dt));
        if (facingRight) {
            flipY(this.center());
            facingRight = false;
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
}
