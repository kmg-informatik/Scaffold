package dev.elk.game.structures;


import dev.elk.scaffold.gl.Quad;
import dev.elk.scaffold.physics.CollidableStructure;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ChunkGenerator implements CollidableStructure {

    private LinkedList<Chunk> chunks = new LinkedList<>();
    private int chunkCounter = 0;

    public ChunkGenerator() {}

    public void init() {
        addChunk();
        addChunk();
        addChunk();
    }

    public void addChunk(){
        chunks.add(new Chunk(chunkCounter * Chunk.CHUNK_SIZE));
        CollidableStructure.collidables.add(chunks.getFirst());
        chunkCounter++;
    }

    public void removeChunk() {
        CollidableStructure.collidables.remove(chunks.getLast());
        chunks.remove();
    }


    public void needsChunk(float playerPosition) {
        if(playerPosition > ((chunkCounter-1) * Chunk.CHUNK_SIZE)) {
            removeChunk();
            addChunk();
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