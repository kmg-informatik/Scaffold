package dev.elk.game;

import dev.elk.scaffold.components.player.Player;
import dev.elk.scaffold.renderer.AnimatedSprite;
import dev.elk.scaffold.renderer.Spritesheet;
import org.joml.Vector2f;


public class Bird extends Player {
    private final AnimatedSprite upSprite = Spritesheet.animatedSprites.get("birdUp");
    private final AnimatedSprite downSprite = Spritesheet.animatedSprites.get("birdDown");

    public Bird(Vector2f startPosition, float size) {
        super(startPosition, size, Spritesheet.animatedSprites.get("birdUp"));
    }

    public int getPipesPassed() {
        int a = ((int)getPosition().x - 50) / 10;
        if (a < 0) return 0;
        else return a;
    }

}
