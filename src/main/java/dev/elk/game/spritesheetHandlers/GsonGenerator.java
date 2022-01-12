package dev.elk.game.spritesheetHandlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.elk.game.fontSettings.Font;
import dev.elk.scaffold.renderer.AnimatedSprite;
import dev.elk.scaffold.renderer.Sprite;
import dev.elk.scaffold.renderer.Spritesheet;
import dev.elk.scaffold.renderer.Texture;
import org.joml.Vector2i;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Generates the Json necessary to read sprites.
 *
 * @author Felix Kunze
 */
public class GsonGenerator {

    public static void main(String[] args) throws IOException {
        generateSpritesheets();
        generateFonts();
    }

    public static void generateSpritesheets() throws IOException {
        generateAnimated();
        geneteBird();
        generateTiles();
        generateTrees();
        generatePipeSprite();
    }

    public static void generateFonts() throws IOException {
        generateCozetteFont();
        generateJetbrainsMonoFont();
        generateTimesNewRomanFont();
    }

    public static void generateTiles() throws IOException {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();


        Spritesheet<Sprite> spritesheet = new Spritesheet<>(
                432,
                120,
                24,
                24
        );

        spritesheet.addSprites(
                new Sprite(new Vector2i(0, 0), new Vector2i(1, 1), "barrel3"),
                new Sprite(new Vector2i(1, 0), new Vector2i(2, 1), "barrel2"),
                new Sprite(new Vector2i(2, 0), new Vector2i(3, 1), "barrel1"),
                new Sprite(new Vector2i(3, 0), new Vector2i(4, 1), "barrelFire"),
                new Sprite(new Vector2i(4, 0), new Vector2i(5, 1), "trash"),
                new Sprite(new Vector2i(5, 0), new Vector2i(6, 1), "tank"),
                new Sprite(new Vector2i(6, 0), new Vector2i(7, 1), "gear"),
                new Sprite(new Vector2i(7, 0), new Vector2i(8, 1), "spikes")
        );
        int a = 0;
        int b = 1;
        spritesheet.addSprites(
                new Sprite(new Vector2i(a++, 1), new Vector2i(b++, 2), "dirtLight"),
                new Sprite(new Vector2i(a++, 1), new Vector2i(b++, 2), "grass4"),
                new Sprite(new Vector2i(a++, 1), new Vector2i(b++, 2), "grass3down"),
                new Sprite(new Vector2i(a++, 1), new Vector2i(b++, 2), "grass3left"),
                new Sprite(new Vector2i(a++, 1), new Vector2i(b++, 2), "grass3up"),
                new Sprite(new Vector2i(a++, 1), new Vector2i(b++, 2), "grass3side"),
                new Sprite(new Vector2i(a++, 1), new Vector2i(b++, 2), "grass2RightBottom"),
                new Sprite(new Vector2i(a++, 1), new Vector2i(b++, 2), "grass2LeftBottom"),
                new Sprite(new Vector2i(a++, 1), new Vector2i(b++, 2), "grass2LeftTop"),
                new Sprite(new Vector2i(a++, 1), new Vector2i(b++, 2), "grass2RightTop"),
                new Sprite(new Vector2i(a++, 1), new Vector2i(b++, 2), "grassTop"),
                new Sprite(new Vector2i(a++, 1), new Vector2i(b++, 2), "grassRight"),
                new Sprite(new Vector2i(a++, 1), new Vector2i(b++, 2), "grassDown"),
                new Sprite(new Vector2i(a++, 1), new Vector2i(b++, 2), "grassLeft"),
                new Sprite(new Vector2i(a++, 1), new Vector2i(b++, 2), "grassCornerLeftTop"),
                new Sprite(new Vector2i(a++, 1), new Vector2i(b++, 2), "grassCornerRightTop"),
                new Sprite(new Vector2i(a++, 1), new Vector2i(b++, 2), "grassCornerRightBottom"),
                new Sprite(new Vector2i(a, 1), new Vector2i(b, 2), "grassCornerLeftBottom")
        );
        a = 0;
        b = 1;

        spritesheet.addSprites(
                new Sprite(new Vector2i(a++, 2), new Vector2i(b++, 3), "dirtDark"),
                new Sprite(new Vector2i(a++, 2), new Vector2i(b++, 3), "dirt4"),
                new Sprite(new Vector2i(a++, 2), new Vector2i(b++, 3), "dirt3down"),
                new Sprite(new Vector2i(a++, 2), new Vector2i(b++, 3), "dirt3left"),
                new Sprite(new Vector2i(a++, 2), new Vector2i(b++, 3), "dirt3up"),
                new Sprite(new Vector2i(a++, 2), new Vector2i(b++, 3), "dirt3side"),
                new Sprite(new Vector2i(a++, 2), new Vector2i(b++, 3), "dirt2RightBottom"),
                new Sprite(new Vector2i(a++, 2), new Vector2i(b++, 3), "dirt2LeftBottom"),
                new Sprite(new Vector2i(a++, 2), new Vector2i(b++, 3), "dirt2LeftTop"),
                new Sprite(new Vector2i(a++, 2), new Vector2i(b++, 3), "dirt2RightTop"),
                new Sprite(new Vector2i(a++, 2), new Vector2i(b++, 3), "dirtTop"),
                new Sprite(new Vector2i(a++, 2), new Vector2i(b++, 3), "dirtRight"),
                new Sprite(new Vector2i(a++, 2), new Vector2i(b++, 3), "dirtDown"),
                new Sprite(new Vector2i(a++, 2), new Vector2i(b++, 3), "dirtLeft"),
                new Sprite(new Vector2i(a++, 2), new Vector2i(b++, 3), "dirtCornerLeftTop"),
                new Sprite(new Vector2i(a++, 2), new Vector2i(b++, 3), "dirtCornerRightTop"),
                new Sprite(new Vector2i(a++, 2), new Vector2i(b++, 3), "dirtCornerRightBottom"),
                new Sprite(new Vector2i(a, 2), new Vector2i(b, 3), "dirtCornerLeftBottom")
        );

        a = 0;
        b = 1;

        spritesheet.addSprites(
                new Sprite(new Vector2i(a++, 3), new Vector2i(b++, 4), "sky"),
                new Sprite(new Vector2i(a++, 3), new Vector2i(b++, 4), "cloudLeftRoundSmall"),
                new Sprite(new Vector2i(a++, 3), new Vector2i(b++, 4), "cloudRightRoundSmall"),
                new Sprite(new Vector2i(a++, 3), new Vector2i(b++, 4), "cloudLeftTriangle"),
                new Sprite(new Vector2i(a++, 3), new Vector2i(b++, 4), "cloudRightTriangle"),
                new Sprite(new Vector2i(a++, 3), new Vector2i(b++, 4), "cloudLeftB"),
                new Sprite(new Vector2i(a++, 3), new Vector2i(b++, 4), "cloudRightB"),
                new Sprite(new Vector2i(a++, 3), new Vector2i(b++, 4), "cloudLeftRoundBig"),
                new Sprite(new Vector2i(a++, 3), new Vector2i(b++, 4), "cloudRightRoundBig"),
                new Sprite(new Vector2i(a, 3), new Vector2i(b, 4), "cloudCenter")
        );
        a = 0;
        b = 1;

        spritesheet.addSprites(

                new Sprite(new Vector2i(a++, 4), new Vector2i(b++, 5), "brick"),
                new Sprite(new Vector2i(a++, 4), new Vector2i(b++, 5), "stoneBrick"),
                new Sprite(new Vector2i(a++, 4), new Vector2i(b++, 5), "cobblestone"),
                new Sprite(new Vector2i(a++, 4), new Vector2i(b++, 5), "pavement"),
                new Sprite(new Vector2i(a++, 4), new Vector2i(b++, 5), "marble"),
                new Sprite(new Vector2i(a++, 4), new Vector2i(b++, 5), "iron"),
                new Sprite(new Vector2i(a++, 4), new Vector2i(b++, 5), "steel"),
                new Sprite(new Vector2i(a++, 4), new Vector2i(b++, 5), "copper"),
                new Sprite(new Vector2i(a++, 4), new Vector2i(b++, 5), "ironPlate"),
                new Sprite(new Vector2i(a++, 4), new Vector2i(b++, 5), "ironBolt"),
                new Sprite(new Vector2i(a++, 4), new Vector2i(b++, 5), "ironBars"),
                new Sprite(new Vector2i(a++, 4), new Vector2i(b++, 5), "window"),
                new Sprite(new Vector2i(a++, 4), new Vector2i(b++, 5), "wood"),
                new Sprite(new Vector2i(a, 4), new Vector2i(b, 5), "pattern")
        );


        FileWriter writer = new FileWriter("Assets/SpriteJson/tiles.json");
        gson.toJson(spritesheet, writer);
        writer.close();


    }

    public static void generateTrees() throws IOException {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Spritesheet<Sprite> spritesheet = new Spritesheet<>(
                288,
                48,
                24,
                24
        );

        int a = 0;
        int b = 1;

        spritesheet.addSprites(
                new Sprite(new Vector2i(a++, 0), new Vector2i(b++, 2), "tree"),
                new Sprite(new Vector2i(a++, 0), new Vector2i(b++, 2), "treeCone"),
                new Sprite(new Vector2i(a++, 0), new Vector2i(b++, 2), "treeBranch"),
                new Sprite(new Vector2i(a++, 0), new Vector2i(b++, 2), "treeMango"),
                new Sprite(new Vector2i(a++, 0), new Vector2i(b++, 2), "treeBlock"),
                new Sprite(new Vector2i(a++, 0), new Vector2i(b++, 2), "treeFlower"),
                new Sprite(new Vector2i(a++, 0), new Vector2i(b++, 2), "treeComplicated"),
                new Sprite(new Vector2i(a++, 0), new Vector2i(b++, 2), "treeCurvy"),
                new Sprite(new Vector2i(a++, 0), new Vector2i(b++, 2), "treeStack"),
                new Sprite(new Vector2i(a++, 0), new Vector2i(b++, 2), "treeBubbles"),
                new Sprite(new Vector2i(a++, 0), new Vector2i(b++, 2), "treeLong"),
                new Sprite(new Vector2i(a++, 0), new Vector2i(b++, 2), "tree"),
                new Sprite(new Vector2i(a, 0), new Vector2i(b, 2), "treeStep")
        );

        FileWriter writer = new FileWriter("Assets/SpriteJson/trees.json");
        gson.toJson(spritesheet, writer);
        writer.close();
    }

    public static void generateAnimated() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Spritesheet<AnimatedSprite> spritesheet = new Spritesheet<>(
                224,
                82,
                28,
                41
        );

        spritesheet.addSprites(
                new AnimatedSprite(new Vector2i(0, 0), new Vector2i(1, 1), "maguJump", 8),
                new AnimatedSprite(new Vector2i(0, 1), new Vector2i(1, 2), "maguWalk", 3)
        );

        FileWriter writer = new FileWriter("Assets/SpriteJson/animations.json");
        gson.toJson(spritesheet, writer);
        writer.close();
    }

    public static void geneteBird() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Spritesheet<AnimatedSprite> spritesheet = new Spritesheet<>(
                261,
                261,
                87,
                87
        );

        spritesheet.addSprites(
                new AnimatedSprite(new Vector2i(0, 2), new Vector2i(1, 3), "bird", 1),
                new AnimatedSprite(new Vector2i(0, 1), new Vector2i(1, 2), "birdDown", 3),
                new AnimatedSprite(new Vector2i(0, 0), new Vector2i(1, 1), "birdUp", 3)
        );

        FileWriter writer = new FileWriter("Assets/SpriteJson/animations.json");
        gson.toJson(spritesheet, writer);
        writer.close();
    }

    public static void generatePipeSprite() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Spritesheet<Sprite> spritesheet = new Spritesheet<>(
                150,
                1080,
                150,
                1080
        );
        spritesheet.addSprites(new Sprite(
                new Vector2i(0,0),
                new Vector2i(1,1),
                "pipe"
        ));

        FileWriter writer = new FileWriter("Assets/SpriteJson/pipe.json");
        gson.toJson(spritesheet, writer);
        writer.close();
    }

    public static Spritesheet<Sprite> generateFont(String fontPrefix, int sheetWidth, int sheetHeight, int tileWidth, int tileHeight) {

        Spritesheet<Sprite> spritesheet = new Spritesheet<>(
                sheetWidth,
                sheetHeight,
                tileWidth,
                tileHeight
        );


        int k = 8;
        int j = 9;
        int a = 0;
        int b = 1;
        spritesheet.addSprites(
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_p"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_q"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_r"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_s"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_t"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_u"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_v"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_w"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_x"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_y"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_z"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_{"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_|"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_}"),
                new Sprite(new Vector2i(a, k++), new Vector2i(b, j++), fontPrefix + "_~")
        );

        a = 0;
        b = 1;
        spritesheet.addSprites(
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_'"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_a"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_b"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_c"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_d"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_e"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_f"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_g"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_h"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_i"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_j"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_k"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_l"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_m"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_n"),
                new Sprite(new Vector2i(a, k++), new Vector2i(b, j++), fontPrefix + "_o")
        );

        a = 0;
        b = 1;
        spritesheet.addSprites(
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_P"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_Q"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_R"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_S"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_T"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_U"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_V"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_W"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_X"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_Y"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_Z"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_["),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_backSlash"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_]"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_^"),
                new Sprite(new Vector2i(a, k++), new Vector2i(b, j++), fontPrefix + "__")
        );

        a = 0;
        b = 1;
        spritesheet.addSprites(
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_@"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_A"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_B"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_C"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_D"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_E"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_F"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_G"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_H"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_I"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_J"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_K"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_L"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_M"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_N"),
                new Sprite(new Vector2i(a, k++), new Vector2i(b, j++), fontPrefix + "_O")
        );

        a = 0;
        b = 1;
        spritesheet.addSprites(
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_0"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_1"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_2"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_3"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_4"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_5"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_6"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_7"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_8"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_9"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_:"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_;"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_<"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_="),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_>"),
                new Sprite(new Vector2i(a, k++), new Vector2i(b, j++), fontPrefix + "_?")
        );

        a = 0;
        b = 1;
        spritesheet.addSprites(
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_ "),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_!"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_doubleQuote"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_#"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_$"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_%"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_&"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_'"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_("),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_)"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_*"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_+"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_,"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_-"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), fontPrefix + "_."),
                new Sprite(new Vector2i(a, k), new Vector2i(b, j), fontPrefix + "_/")
        );
        return spritesheet;
    }

    public static void generateCozetteFont() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Spritesheet<Sprite> spritesheet = generateFont(
                Font.COZETTE.fontID,
                256,
                288,
                16,
                18
        );

        FileWriter writer = new FileWriter("Assets/SpriteJson/fonts/cozette.json");
        gson.toJson(spritesheet, writer);
        writer.close();
    }

    public static void generateTimesNewRomanFont() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Spritesheet<Sprite> spritesheet = generateFont(
                Font.TIMES_NEW_ROMAN.fontID,
                256,
                320,
                16,
                20
        );

        FileWriter writer = new FileWriter("Assets/SpriteJson/fonts/timesNewRoman.json");
        gson.toJson(spritesheet, writer);
        writer.close();
    }

    public static void generateJetbrainsMonoFont() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Spritesheet<Sprite> spritesheet = generateFont(
                Font.JETBRAINS_MONO.fontID,
                256,
                320,
                16,
                20
        );

        FileWriter writer = new FileWriter("Assets/SpriteJson/fonts/jetbrainsMono.json");
        gson.toJson(spritesheet, writer);
        writer.close();
    }
}