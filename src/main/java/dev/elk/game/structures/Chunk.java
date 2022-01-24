package dev.elk.game.structures;

import dev.elk.scaffold.gl.Quad;
import dev.elk.scaffold.gl.TexturedSquare;
import dev.elk.scaffold.physics.CollidableStructure;
import dev.elk.scaffold.renderer.Spritesheet;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Constructs a new Chunk comprised of Floors and ceilings as well as
 * a proceduraly generated backdrop of clouds.
 *
 * @author Felix Kunze
 */
public class Chunk implements CollidableStructure {

    public static final int CHUNK_SIZE = 60;

    private ScreenBounds floor;
    private ScreenBounds ceiling;
    protected ArrayList<Quad> quads = new ArrayList<>();
    protected ArrayList<Quad> collidableQuads = new ArrayList<>();

    protected float position;
    protected static Random random = new Random();


    /**
     * Generates a new Chunk in a certain position.
     * @param position The chunk position
     */
    public Chunk(float position) {
        this.position = position;
        generateBackground();
        this.floor = new ScreenBounds(new Vector2f(position,13),true);
        this.ceiling = new ScreenBounds(new Vector2f(position,-15),false);
        collidableQuads.addAll(List.of(floor.getQuads()));
        collidableQuads.addAll(List.of(ceiling.getQuads()));
    }

    /**
     * Generatesa a Background out of non collidable tiles
     * This randomly generates Clouds
     */
    private void generateBackground() {
        int tileSize = 4;
        for (int j = -13; j < 12; j+=4) {
            for (int i = 0; i < CHUNK_SIZE / tileSize; i++) {
                int seed = random.nextInt(20);
                if (seed == 0) {
                    quads.add(new TexturedSquare(
                            new Vector2f(position + tileSize * i, j),
                            tileSize,
                            Spritesheet.staticSprites.get(cloudPicker(true))
                    ));
                    i++;
                    quads.add(new TexturedSquare(
                            new Vector2f(position +  tileSize * i, j),
                            tileSize,
                            Spritesheet.staticSprites.get(cloudPicker(false))
                    ));
                }
                else {
                    TexturedSquare texturedSquare = new TexturedSquare(
                            new Vector2f(position + tileSize * i, j),
                            tileSize,
                            Spritesheet.staticSprites.get("sky")
                    );
                    quads.add(texturedSquare);
                }
            }
        }
    }

    /**
     * Picks out a random Cloud
     * @param isBeginCloud Chooses whether it should be a beginning cloud or not
     * @return The String representation of the cloud
     */
    public String cloudPicker(boolean isBeginCloud) {
        String[] clouds = {
                "cloudRightRoundSmall",
                "cloudRightTriangle",
                "cloudRightB",
                "cloudRightRoundBig",
                "cloudLeftRoundSmall",
                "cloudLeftTriangle",
                "cloudLeftB",
                "cloudLeftRoundBig"
        };
        return clouds[random.nextInt(3) + (isBeginCloud? 0:4)];

    }

    @Override
    public Quad[] getCollidableQuads() {
        return collidableQuads.toArray(new Quad[0]);
    }

    @Override
    public Quad[] getQuads() {
        ArrayList<Quad> arr = new ArrayList<>();
        arr.addAll(quads);
        arr.addAll(collidableQuads);
        return arr.toArray(new Quad[0]);
    }
}
