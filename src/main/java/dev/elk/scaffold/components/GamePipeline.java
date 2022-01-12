package dev.elk.scaffold.components;

import dev.elk.scaffold.gl.Window;

import java.util.Arrays;
import java.util.HashSet;

public class GamePipeline {

    /**
     * Collection of all {@link PipelineNode}
     */
    private final HashSet<PipelineNode> nodes = new HashSet<>();

    /**
     * Current node that is being rendered
     */
    private final PipelineNode firstNode;

    /**
     * Window which the game is being rendered in
     */
    private final Window window;

    public GamePipeline(Window window, PipelineNode firstNode) {
        this.window = window;
        this.firstNode = firstNode;
    }

    /**
     * Starts the pipeline, enters into the first node
     */
    public void run() throws Exception {
        firstNode.init();
        window.setCurrentNode(firstNode);
        window.loop();
        window.shutdown();
    }

    /**
     * Adds a node to the pipeline
     */
    public void addNodes(PipelineNode...nodes){
        this.nodes.addAll(Arrays.asList(nodes));
    }

    /**
     * @return all registered {@link PipelineNode}
     */
    public HashSet<PipelineNode> getNodes() {
        return nodes;
    }

    /**
     * @return starting node of the pipeline
     */
    public PipelineNode getFirstNode() {
        return firstNode;
    }
}
