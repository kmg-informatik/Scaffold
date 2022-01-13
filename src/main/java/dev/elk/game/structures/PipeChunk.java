package dev.elk.game.structures;

import dev.elk.scaffold.physics.CollidableStructure;

import java.util.List;

/**
 * A pipe chunk is a {@link Chunk} that generates pipes. Chunks are generated
 * procedurally, so that we can have an infinite amount.
 * @author Felix Kunze
 * @author Louis Schell
 */
public class PipeChunk extends Chunk implements CollidableStructure {

    /**
     * The latest chunk that was generated
     */
    private Chunk lastChunk;

    public PipeChunk(float position, Chunk lastChunk) {
        super(position);
        this.lastChunk = lastChunk;
        generatePipes();
        this.lastChunk = null; //Clear reference for gc
    }

    /**
     * Generates pipes by performing a random walk. This is done,
     * so that the pipe gap is not too far away from the last pipe gap
     * and is passable by the player.<br>
     * The pipe gap width changes with the difficulty, which changes
     * with the pipe count, which counts how many pipes the player
     * has passed.
     * @apiNote Expand on usage of difficulty
     */
    public void generatePipes() {
        float previousPos = lastChunk.getQuads()[1].getMaxX()-lastChunk.getQuads()[1].getMinX();

        float difficulty = Math.max((lastChunk.position-50f)/10f,0f); //From 0 to n

        for (int i = 0; i < CHUNK_SIZE ; i += 10) {

            difficulty+=1;

            Pipe pipe = new Pipe(
                    position + i,
                    setGapPosition(previousPos, difficulty),
                    random.nextFloat()*2f + 12f - difficulty/10f);
            previousPos = pipe.center().y;
            collidableQuads.addAll(List.of(pipe.getQuads()));
        }
    }

    /**
     * Gets the gap position, limiting it the range [-7,7] because
     * else it would go out of the screen.
     */
    public float setGapPosition(float previous, float difficulty) {
        int sign = random.nextBoolean()?1:-1;
        float pos = previous + sign*random.nextFloat()*3f;
        if (pos >= 7) pos = 7;
        if (pos <=- 7) pos = -7;
        return pos;
    }
}