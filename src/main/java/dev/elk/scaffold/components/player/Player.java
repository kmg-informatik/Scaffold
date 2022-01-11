package dev.elk.scaffold.components.player;

import dev.elk.scaffold.components.userinput.KeyHandler;
import dev.elk.scaffold.gl.Window;
import dev.elk.scaffold.renderer.AnimatedSprite;
import dev.elk.scaffold.renderer.Spritesheet;
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
    private int animationCounter = 0;
    private int animationSpeed = 5;
    private AnimatedSprite walkSprite;
    private AnimatedSprite jumpSprite;

    public Player(AnimatedSprite walkSprite, AnimatedSprite jumpSprite, Vector2f lb, Vector2f tr) {
        super(walkSprite, lb, tr);
        this.walkSprite = walkSprite;
        this.jumpSprite = jumpSprite;
    }

    @Override
    boolean isAi() {
        return false;
    }

    @Override
    float getMovementSpeed() {
        return 10f;
    }

    @Override
    public void update() {

        this.fall();

        movementVector = new Vector2f();

        if (KeyHandler.isKeyPressed(KEY_W) && hasGroundContact()) {
            jump();
        }
        if (KeyHandler.isKeyPressed(KEY_A) || KeyHandler.isKeyPressed(KEY_D)) {
            if (KeyHandler.isKeyPressed(KEY_A)) {
                moveLeft();
            } else {
                moveRight();
            }
            nextFrame();
        }
        this.translate(movementVector);

        if (!hasGroundContact()) nextFrame();
        animationCounter++;
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
        setSprite(jumpSprite);
        animationSpeed = 3;
    }

    @Override
    public Vector2f getPosition() {
        return center();
    }

    public void nextFrame() {
        if (animationCounter %  animationSpeed == 0) {
            ((AnimatedSprite) getSprite()).nextFrame();
            setUV();
            if (hasGroundContact()){
                setSprite(walkSprite);
            }
        }
    }

}
