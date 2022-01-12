package dev.elk.game.structures;

import dev.elk.scaffold.physics.CollidableStructure;

import java.util.List;

public class PipeChunk extends Chunk implements CollidableStructure {

    public PipeChunk(float position) {
        super(position);
        generatePipes();
    }

    public void generatePipes() {
        int previousPos = random.nextInt(10);
        for (int i = 0; i < CHUNK_SIZE ; i += 10) {
            Pipe pipe = new Pipe(position + i, setGapPosition(previousPos), random.nextInt(3) + 9);
            quads.addAll(List.of(pipe.getQuads()));
        }
    }

    public float setGapPosition(int previous) {
        float signum  = Math.signum(random.nextInt(2) - 1);
        float pos = signum * (random.nextInt(8) + previous);
        if (pos >= 12) pos = 12;
        if (pos <=- 12) pos = -12;
        return pos;
    }

}
