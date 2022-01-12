package dev.elk.game.structures;

import dev.elk.scaffold.gl.Quad;
import dev.elk.scaffold.physics.CollidableStructure;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chunk implements CollidableStructure {

    public static final int CHUNK_SIZE = 60;
    private ArrayList<Quad> quads = new ArrayList<>();
    private ScreenBounds floor;
    private ScreenBounds ceiling;
    private float position;
    private static Random random = new Random();



    public Chunk(float position) {
        this.position = position;
        this.floor = new ScreenBounds(new Vector2f(position,13),true);
        this.ceiling = new ScreenBounds(new Vector2f(position,-15),false);
        generatePipes();
        quads.addAll(List.of(floor.getQuads()));
        quads.addAll(List.of(ceiling.getQuads()));
    }

    public void generatePipes() {
        int previousPos = random.nextInt(10);
        for (int i = 0; i < CHUNK_SIZE  - 10; i += 10) {
            Pipe pipe = new Pipe(position + i, setGapPosition(previousPos), random.nextInt(3) + 9);
            quads.addAll(List.of(pipe.getQuads()));
        }

    }

    public float setGapPosition(int previous) {
        float signum  = Math.signum(random.nextInt(2) - 1);
        float pos = signum * (random.nextInt(8) + previous);
        if (pos >= 15) pos = 14;
        if (pos <=- 15) pos = -14;
        return pos;
    }

    @Override
    public Quad[] getQuads() {
        return quads.toArray(new Quad[0]);
    }
}
