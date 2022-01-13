package dev.elk.game;

import dev.elk.scaffold.components.player.Player;
import dev.elk.scaffold.renderer.Spritesheet;
import org.joml.Vector2f;


/**
 * An extension of the Player for the Happy Birb game
 */
public class Bird extends Player {

    /**
     * Generates the bird, assumes a fixed sprite.
     * @param startPosition The starting position of the bird
     * @param size The size of the bird.
     */
    public Bird(Vector2f startPosition, float size) {
        super(startPosition, size, Spritesheet.animatedSprites.get("birdUp"));
    }

    /**
     * Counts pipes passed.
     * @return The amount of Pipes that the bird has passed since the begining
     */
    public int getPipesPassed() {
        int a = ((int)getPosition().x - 50) / 10;
        return Math.max(a, 0);
    }

}
