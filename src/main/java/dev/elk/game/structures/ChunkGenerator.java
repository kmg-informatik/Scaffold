package dev.elk.game.structures;


import dev.elk.scaffold.gl.Quad;
import dev.elk.scaffold.physics.CollidableStructure;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Generates Chunks with ground and backdrop.
 * Handles swapping of chunks via a Linkedlist that removes a chunk once a new one is added
 * @author Felix Kunze
 * @author Eric Jacob
 * @author Louis Schell
 */
public class ChunkGenerator implements CollidableStructure {

    private LinkedList<Chunk> chunks = new LinkedList<>();
    private int chunkCounter = 0;

    public ChunkGenerator() {}

    public void init() {
        addChunk();
        addPipeChunk();
        addPipeChunk();
    }

    /**
     * Generates a chunk with pipes
     */
    public void addPipeChunk(){
        chunks.add(new PipeChunk(chunkCounter * Chunk.CHUNK_SIZE, chunks.getFirst()));
        CollidableStructure.collidables.add(chunks.getLast());
        chunkCounter++;
    }

    /**
     * Adds a new Chunk and its Collision
     */
    public void addChunk(){
        chunks.add(new Chunk(chunkCounter * Chunk.CHUNK_SIZE));
        CollidableStructure.collidables.add(chunks.getFirst());
        chunkCounter++;
    }

    /**
     * Removes a chunk and its Collision
     */
    public void removeChunk() {
        CollidableStructure.collidables.remove(chunks.getFirst());
        chunks.remove();
    }


    /**
     * Determines if a chunk is needed by checking if a new one is needed.
     * A new chunk is needed whenever a chunk is one chunk sizew away from the player.
     * @param playerPosition The position of the player
     */
    public void needsChunk(float playerPosition) {
        if(playerPosition > ((chunkCounter-1) * Chunk.CHUNK_SIZE)) {
            removeChunk();
            addPipeChunk();
        }

    }


    @Override
    public Quad[] getQuads() {
        ArrayList<Quad> quads= new ArrayList<>();
        for (Chunk chunk : chunks) {
            quads.addAll(List.of(chunk.getQuads()));
        }
        return quads.toArray(new Quad[0]);
    }
}
