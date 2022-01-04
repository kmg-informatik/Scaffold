package dev.elk.game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.elk.scaffold.renderer.AnimatedSprite;
import dev.elk.scaffold.renderer.Sprite;
import dev.elk.scaffold.renderer.Spritesheet;
import dev.elk.scaffold.renderer.Texture;
import org.joml.Vector2i;

import java.io.FileWriter;
import java.io.IOException;

public class GsonGenerator {

    public static void generateGson() throws IOException {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();


        Spritesheet spritesheet = new Spritesheet(
                48,
                48,
                24,
                24
        );

        spritesheet.addSprite(
                new Sprite(
                        new Vector2i(0,0),
                        new Vector2i(1,1),
                        "ventilator"),
                new Sprite(
                        new Vector2i(1,0),
                        new Vector2i(2,1),
                        "grass"),
                new Sprite(
                        new Vector2i(1,1),
                        new Vector2i(2,2),
                        "tank"),
                new Sprite(
                        new Vector2i(0,1),
                        new Vector2i(1,2),
                        "coin")
        );
        spritesheet.calculateUVCoords();

        System.out.println(gson.toJson(spritesheet));


        FileWriter writer = new FileWriter("Assets/SpriteJson/2x2.json");
        gson.toJson(spritesheet, writer);
        writer.close();


    }

    public static void generateAnimatedGson() throws IOException {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();


        Spritesheet spritesheet = new Spritesheet(
                48,
                48,
                24,
                24
        );

        spritesheet.addSprite(new AnimatedSprite(new Vector2i(0,0), new Vector2i(1,1), "ventilator", 2));
        System.out.println(gson.toJson(spritesheet));
        FileWriter writer = new FileWriter("Assets/SpriteJson/animationTest.json");
        gson.toJson(spritesheet, writer);
        writer.close();
    }

}
