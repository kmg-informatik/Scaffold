package dev.elk.game;

import dev.elk.scaffold.components.Window;
import dev.elk.scaffold.renderer.*;
import dev.elk.scaffold.renderer.Shader.ShaderType;

import java.awt.*;
import java.io.IOException;

/**
 * Start game here. In this case, a demonstration of the recently
 * implemented vertex object.
 */
public class Main {

    public static void main(String[] args) throws IOException {

        Shader fragment = new Shader("Assets/Shaders/fragment.glsl", ShaderType.FRAGMENT);
        Shader vertex = new Shader("Assets/Shaders/vertex.glsl", ShaderType.VERTEX);
        ShaderProgram program = new ShaderProgram();
        program.attachShaders(fragment, vertex);


        Window window = new Window("Hello!", 500, 500, Color.white);
        window.setScene(new PrimaryScene(window, program));

        window.run();


    }
}