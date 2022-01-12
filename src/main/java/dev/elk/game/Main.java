package dev.elk.game;

import dev.elk.game.scenes.IntroScene;
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

        Window window = new Window("Hello!", Color.CYAN);
        window.init();

        ///////////////////////////////////GAME_LOGIC///////////////////////////////////

        PipelineNode<IntroScene> introNode = new PipelineNode<>(
                new IntroScene(window, program),
                window
        );

        //Add a node with the primary scene
        PipelineNode<PrimaryScene> gameNode = new PipelineNode<>(
                new PrimaryScene(window, program),
                window);

        //Add a node with the end scene
        PipelineNode<EndGameScene> end = new PipelineNode<>(
                new EndGameScene(window),
                window);

        //Add a link, that if the windowFrameCount is above 200,
        //advance to the end node.

        introNode.addLinks(intro->{
            if (introNode.getScene().isFinished())
                return gameNode;
            else return null;
        });

        gameNode.addLinks(game->{
            if (window.getCurrentFrameCount()>20000) {
                return end;
            }else return null;
        });

        //Add the nodes to the pipeline
        GamePipeline pipeline = new GamePipeline(window, introNode);

        //Run the pipeline
        pipeline.run();

        //destroy glfw when program done.
        window.destroy();


    }
}