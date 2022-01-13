package dev.elk.game;

import dev.elk.game.scenes.PrimaryScene;
import dev.elk.scaffold.components.GamePipeline;
import dev.elk.scaffold.components.PipelineNode;
import dev.elk.scaffold.gl.Window;
import dev.elk.scaffold.gl.bindings.Shader;
import dev.elk.scaffold.gl.bindings.ShaderProgram;

import java.awt.*;

/**
 * Start game here. In this case, a demonstration of the recently
 * implemented vertex object.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        Shader fragment = new Shader("Assets/Shaders/fragment.glsl", Shader.ShaderType.FRAGMENT);
        Shader vertex = new Shader("Assets/Shaders/vertex.glsl", Shader.ShaderType.VERTEX);
        ShaderProgram program = new ShaderProgram();
        program.attachShaders(fragment, vertex);

        Window window = new Window("Welcome to Flappy Birb!", Color.CYAN);
        window.init();

        ///////////////////////////////////GAME_LOGIC///////////////////////////////////

        //Add a node with the primary scene
        PipelineNode<PrimaryScene> gameNode = new PipelineNode<>(
                new PrimaryScene(window, program));

        gameNode.getScene().startOpenGL();

        PipelineNode<PrimaryScene> end = new PipelineNode<>(
                new PrimaryScene(window, program));

        //Add a link, that if the windowFrameCount is above 200,
        //advance to the end node.
        gameNode.addLinks(game->{
            if (game.getScene().isDone())
                return end;
            else return null;
        });

        end.addLinks(game->{
            if (game.getScene().isDone())
                return gameNode;
            else return null;
        });
        //Add the nodes to the pipeline
        GamePipeline pipeline = new GamePipeline(window, gameNode);
        window.loop();

        //destroy glfw when program done.
        window.destroy();


    }
}