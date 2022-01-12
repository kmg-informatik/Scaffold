package dev.elk.game.structures;

import java.util.List;

public class PipeChunk extends Chunk {

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

}
