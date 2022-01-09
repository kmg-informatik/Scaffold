package dev.elk.game.generators;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.elk.scaffold.renderer.*;
import org.joml.Vector2i;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Generates the Json necessary to read sprites.
 * @author Felix Kunze
 */
public class GsonGenerator {

    public static void main(String[] args) throws IOException {
        generateFonts();
    }

    public static void generateSpritesheets() throws IOException {
        generateAnimatedJson();
        generateTiles();
        generateTrees();
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

        spritesheet.addSprite(
               new Sprite(new Vector2i(0,0), new Vector2i(1,1), "barrel3"),
               new Sprite(new Vector2i(1,0), new Vector2i(2,1), "barrel2"),
               new Sprite(new Vector2i(2,0), new Vector2i(3,1), "barrel1"),
               new Sprite(new Vector2i(3,0), new Vector2i(4,1), "barrelFire"),
               new Sprite(new Vector2i(4,0), new Vector2i(5,1), "trash"),
               new Sprite(new Vector2i(5,0), new Vector2i(6,1), "tank"),
               new Sprite(new Vector2i(6,0), new Vector2i(7,1), "gear"),
               new Sprite(new Vector2i(7,0), new Vector2i(8,1), "spikes")
        );
        int a = 0;
        int b = 1;
        spritesheet.addSprite(
                new Sprite(new Vector2i(a++,1), new Vector2i(b++,2), "dirtLight"),
                new Sprite(new Vector2i(a++,1), new Vector2i(b++,2), "grass4"),
                new Sprite(new Vector2i(a++,1), new Vector2i(b++,2), "grass3down"),
                new Sprite(new Vector2i(a++,1), new Vector2i(b++,2), "grass3left"),
                new Sprite(new Vector2i(a++,1), new Vector2i(b++,2), "grass3up"),
                new Sprite(new Vector2i(a++,1), new Vector2i(b++,2), "grass3side"),
                new Sprite(new Vector2i(a++,1), new Vector2i(b++,2), "grass2RightBottom"),
                new Sprite(new Vector2i(a++,1), new Vector2i(b++,2), "grass2LeftBottom"),
                new Sprite(new Vector2i(a++,1), new Vector2i(b++,2), "grass2LeftTop"),
                new Sprite(new Vector2i(a++,1), new Vector2i(b++,2), "grass2RightTop"),
                new Sprite(new Vector2i(a++,1), new Vector2i(b++,2), "grassTop"),
                new Sprite(new Vector2i(a++,1), new Vector2i(b++,2), "grassRight"),
                new Sprite(new Vector2i(a++,1), new Vector2i(b++,2), "grassDown"),
                new Sprite(new Vector2i(a++,1), new Vector2i(b++,2), "grassLeft"),
                new Sprite(new Vector2i(a++,1), new Vector2i(b++,2), "grassCornerLeftTop"),
                new Sprite(new Vector2i(a++,1), new Vector2i(b++,2), "grassCornerRightTop"),
                new Sprite(new Vector2i(a++,1), new Vector2i(b++,2), "grassCornerRightBottom"),
                new Sprite(new Vector2i(a,1), new Vector2i(b,2), "grassCornerLeftBottom")
        );
        a = 0;
        b = 1;

        spritesheet.addSprite(
                new Sprite(new Vector2i(a++,2), new Vector2i(b++,3), "dirtDark"),
                new Sprite(new Vector2i(a++,2), new Vector2i(b++,3), "dirt4"),
                new Sprite(new Vector2i(a++,2), new Vector2i(b++,3), "dirt3down"),
                new Sprite(new Vector2i(a++,2), new Vector2i(b++,3), "dirt3left"),
                new Sprite(new Vector2i(a++,2), new Vector2i(b++,3), "dirt3up"),
                new Sprite(new Vector2i(a++,2), new Vector2i(b++,3), "dirt3side"),
                new Sprite(new Vector2i(a++,2), new Vector2i(b++,3), "dirt2RightBottom"),
                new Sprite(new Vector2i(a++,2), new Vector2i(b++,3), "dirt2LeftBottom"),
                new Sprite(new Vector2i(a++,2), new Vector2i(b++,3), "dirt2LeftTop"),
                new Sprite(new Vector2i(a++,2), new Vector2i(b++,3), "dirt2RightTop"),
                new Sprite(new Vector2i(a++,2), new Vector2i(b++,3), "dirtTop"),
                new Sprite(new Vector2i(a++,2), new Vector2i(b++,3), "dirtRight"),
                new Sprite(new Vector2i(a++,2), new Vector2i(b++,3), "dirtDown"),
                new Sprite(new Vector2i(a++,2), new Vector2i(b++,3), "dirtLeft"),
                new Sprite(new Vector2i(a++,2), new Vector2i(b++,3), "dirtCornerLeftTop"),
                new Sprite(new Vector2i(a++,2), new Vector2i(b++,3), "dirtCornerRightTop"),
                new Sprite(new Vector2i(a++,2), new Vector2i(b++,3), "dirtCornerRightBottom"),
                new Sprite(new Vector2i(a,2), new Vector2i(b,3), "dirtCornerLeftBottom")
        );

        a = 0;
        b = 1;

        spritesheet.addSprite(
                new Sprite(new Vector2i(a++,3), new Vector2i(b++,4), "sky"),
                new Sprite(new Vector2i(a++,3), new Vector2i(b++,4), "cloudLeftRoundSmall"),
                new Sprite(new Vector2i(a++,3), new Vector2i(b++,4), "cloudRightRoundSmall"),
                new Sprite(new Vector2i(a++,3), new Vector2i(b++,4), "cloudLeftTriangle"),
                new Sprite(new Vector2i(a++,3), new Vector2i(b++,4), "cloudRightTriangle"),
                new Sprite(new Vector2i(a++,3), new Vector2i(b++,4), "cloudLeftB"),
                new Sprite(new Vector2i(a++,3), new Vector2i(b++,4), "cloudRightB"),
                new Sprite(new Vector2i(a++,3), new Vector2i(b++,4), "cloudLeftRoundBig"),
                new Sprite(new Vector2i(a++,3), new Vector2i(b++,4), "cloudRightRoundBig"),
                new Sprite(new Vector2i(a,3), new Vector2i(b,4), "cloudCenter")
        );
        a = 0;
        b = 1;

        spritesheet.addSprite(

                new Sprite(new Vector2i(a++,4), new Vector2i(b++,5), "brick"),
                new Sprite(new Vector2i(a++,4), new Vector2i(b++,5), "stoneBrick"),
                new Sprite(new Vector2i(a++,4), new Vector2i(b++,5), "cobblestone"),
                new Sprite(new Vector2i(a++,4), new Vector2i(b++,5), "pavement"),
                new Sprite(new Vector2i(a++,4), new Vector2i(b++,5), "marble"),
                new Sprite(new Vector2i(a++,4), new Vector2i(b++,5), "iron"),
                new Sprite(new Vector2i(a++,4), new Vector2i(b++,5), "steel"),
                new Sprite(new Vector2i(a++,4), new Vector2i(b++,5), "copper"),
                new Sprite(new Vector2i(a++,4), new Vector2i(b++,5), "ironPlate"),
                new Sprite(new Vector2i(a++,4), new Vector2i(b++,5), "ironBolt"),
                new Sprite(new Vector2i(a++,4), new Vector2i(b++,5), "ironBars"),
                new Sprite(new Vector2i(a++,4), new Vector2i(b++,5), "window"),
                new Sprite(new Vector2i(a++,4), new Vector2i(b++,5), "wood"),
                new Sprite(new Vector2i(a,4), new Vector2i(b,5), "pattern")
        );


        FileWriter writer = new FileWriter("Assets/SpriteJson/tiles.json");
        gson.toJson(spritesheet, writer);
        writer.close();


    }

    public static void generateTrees() throws IOException{

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Spritesheet<Sprite> spritesheet = new Spritesheet<>(
                288,
                48,
                24,
                24
        );

        int a = 0;
        int b = 1;

        spritesheet.addSprite(
                new Sprite(new Vector2i(a++,0), new Vector2i(b++,2), "tree"),
                new Sprite(new Vector2i(a++,0), new Vector2i(b++,2), "treeCone"),
                new Sprite(new Vector2i(a++,0), new Vector2i(b++,2), "treeBranch"),
                new Sprite(new Vector2i(a++,0), new Vector2i(b++,2), "treeMango"),
                new Sprite(new Vector2i(a++,0), new Vector2i(b++,2), "treeBlock"),
                new Sprite(new Vector2i(a++,0), new Vector2i(b++,2), "treeFlower"),
                new Sprite(new Vector2i(a++,0), new Vector2i(b++,2), "treeComplicated"),
                new Sprite(new Vector2i(a++,0), new Vector2i(b++,2), "treeCurvy"),
                new Sprite(new Vector2i(a++,0), new Vector2i(b++,2), "treeStack"),
                new Sprite(new Vector2i(a++,0), new Vector2i(b++,2), "treeBubbles"),
                new Sprite(new Vector2i(a++,0), new Vector2i(b++,2), "treeLong"),
                new Sprite(new Vector2i(a++,0), new Vector2i(b++,2), "tree"),
                new Sprite(new Vector2i(a,0), new Vector2i(b,2), "treeStep")
        );

        FileWriter writer = new FileWriter("Assets/SpriteJson/trees.json");
        gson.toJson(spritesheet, writer);
        writer.close();
    }

    public static void generateAnimatedJson() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Spritesheet<AnimatedSprite> spritesheet = new Spritesheet<>(
                288,
                120,
                24,
                24
        );

        spritesheet.addSprite(
                new AnimatedSprite(new Vector2i(0,0), new Vector2i(1,2), "einrad", 12),
                new AnimatedSprite(new Vector2i(0,2),new Vector2i(1,3),"monsterYellow",3),
                new AnimatedSprite(new Vector2i(0,3),new Vector2i(1,4),"goomba",3),
                new AnimatedSprite(new Vector2i(0,4),new Vector2i(1,5),"hydra",4)
        );

        FileWriter writer = new FileWriter("Assets/SpriteJson/animations.json");
        gson.toJson(spritesheet, writer);
        writer.close();
    }

    public static Spritesheet<Sprite> generateFont(int sheetWidth, int sheetHeight, int tileWidth, int tileHeight) {

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
        spritesheet.addSprite(
                new Sprite(new Vector2i(a++,k), new Vector2i(b++, j), "p"),
                new Sprite(new Vector2i(a++,k), new Vector2i(b++,j), "q"),
                new Sprite(new Vector2i(a++,k), new Vector2i(b++,j), "r"),
                new Sprite(new Vector2i(a++,k), new Vector2i(b++, j), "s"),
                new Sprite(new Vector2i(a++,k), new Vector2i(b++, j ), "t"),
                new Sprite(new Vector2i(a++,k), new Vector2i(b++, j), "u"),
                new Sprite(new Vector2i(a++,k), new Vector2i(b++, j), "v"),
                new Sprite(new Vector2i(a++,k), new Vector2i(b++, j), "w"),
                new Sprite(new Vector2i(a++,k), new Vector2i(b++, j), "x"),
                new Sprite(new Vector2i(a++,k), new Vector2i(b++, j), "y"),
                new Sprite(new Vector2i(a++,k), new Vector2i(b++, j), "z"),
                new Sprite(new Vector2i(a++,k), new Vector2i(b++, j), "{"),
                new Sprite(new Vector2i(a++,k), new Vector2i(b++, j), "|"),
                new Sprite(new Vector2i(a++,k), new Vector2i(b++, j), "}"),
                new Sprite(new Vector2i(a,k++), new Vector2i(b,j++), "~")
        );

        a = 0;
        b = 1;
        spritesheet.addSprite(
                new Sprite(new Vector2i(a++,k), new Vector2i(9,b++), "'"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "a"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "b"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "c"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "d"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "e"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "f"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "g"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "h"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "i"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "j"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "k"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "l"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "m"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "n"),
                new Sprite(new Vector2i(a, k++), new Vector2i(b, j++), "o")
        );

        a = 0;
        b = 1;
        spritesheet.addSprite(
                new Sprite(new Vector2i(a++,k), new Vector2i(b++, j), "P"),
                new Sprite(new Vector2i(a++,k), new Vector2i(b++, j), "Q"),
                new Sprite(new Vector2i(a++,k), new Vector2i(b++, j), "R"),
                new Sprite(new Vector2i(a++,k), new Vector2i(b++, j), "S"),
                new Sprite(new Vector2i(a++,k), new Vector2i(b++, j), "T"),
                new Sprite(new Vector2i(a++,k), new Vector2i(b++, j), "U"),
                new Sprite(new Vector2i(a++,k), new Vector2i(b++, j), "V"),
                new Sprite(new Vector2i(a++,k), new Vector2i(b++, j), "W"),
                new Sprite(new Vector2i(a++,k), new Vector2i(b++, j), "X"),
                new Sprite(new Vector2i(a++,k), new Vector2i(b++, j), "Y"),
                new Sprite(new Vector2i(a++,k), new Vector2i(b++, j), "Z"),
                new Sprite(new Vector2i(a++,k), new Vector2i(b++, j), "["),
                new Sprite(new Vector2i(a++,k), new Vector2i(b++, j), "backSlash"),
                new Sprite(new Vector2i(a++,k), new Vector2i(b++, j), "]"),
                new Sprite(new Vector2i(a++,k), new Vector2i(b++, j), "^"),
                new Sprite(new Vector2i(a,k++), new Vector2i(b,j++), "_")
        );

        a = 0;
        b = 1;
        spritesheet.addSprite(
                new Sprite(new Vector2i(a++,k), new Vector2i(9,b++), "@"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "A"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "B"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "C"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "D"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "E"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "F"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "G"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "H"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "I"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "J"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "K"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "L"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "M"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "N"),
                new Sprite(new Vector2i(a, k++), new Vector2i(b, j++), "O")
        );

        a = 0;
        b = 1;
        spritesheet.addSprite(
                new Sprite(new Vector2i(a++,k), new Vector2i(9,b++), "0"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "1"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "2"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "3"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "4"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "5"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "6"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "7"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "8"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "9"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), ":"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), ";"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "<"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "="),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), ">"),
                new Sprite(new Vector2i(a, k++), new Vector2i(b, j++), "?")
        );

        a = 0;
        b = 1;
        spritesheet.addSprite(
                new Sprite(new Vector2i(a++,k), new Vector2i(9,b++), " "),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "!"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "doubleQuote"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "#"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "$"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "%"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "&"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "'"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "("),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), ")"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "*"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "+"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), ","),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "-"),
                new Sprite(new Vector2i(a++, k), new Vector2i(b++, j), "."),
                new Sprite(new Vector2i(a, k), new Vector2i(b, j), "/")
        );
        return spritesheet;
    }

    public static void generateCozetteFont() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Spritesheet<Sprite> spritesheet = generateFont(
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

        Spritesheet<AnimatedSprite> spritesheet = new Spritesheet<>(
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
