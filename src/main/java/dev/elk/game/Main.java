package dev.elk.game;

import dev.elk.scaffold.gl.Window;
import dev.elk.scaffold.gl.bindings.Shader;
import dev.elk.scaffold.gl.bindings.ShaderProgram;

import java.awt.*;
import java.io.IOException;

/**
 * Start game here. In this case, a demonstration of the recently
 * implemented vertex object.
 */
public class Main {

    public static void main(String[] args) throws IOException, InstantiationException {

        Shader fragment = new Shader("Assets/Shaders/fragment.glsl", Shader.ShaderType.FRAGMENT);
        Shader vertex = new Shader("Assets/Shaders/vertex.glsl", Shader.ShaderType.VERTEX);
        ShaderProgram program = new ShaderProgram();
        program.attachShaders(fragment, vertex);

        Window window = new Window("Hello!", Color.CYAN);
        window.setScene(new PrimaryScene(window, program));

        window.run();


    }
}