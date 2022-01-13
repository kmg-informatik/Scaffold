package dev.elk.scaffold.components;

import dev.elk.scaffold.gl.Window;

import java.util.Arrays;
import java.util.HashSet;

public class GamePipeline {

    /**
     * Collection of all {@link PipelineNode}
     */
    private final HashSet<PipelineNode<?>> nodes = new HashSet<>();

    /**
     * Current node that is being rendered
     */
    private PipelineNode<?> currentNode;

    /**
     * Window which the game is being rendered in
     */
    private final Window window;

    public GamePipeline(Window window, PipelineNode<?> firstNode) {
        this.window = window;
        this.currentNode = firstNode;
        window.setPipeline(this);
    }

    /**
     * Gets the next node in the pipeline
     * @return new node
     */
    public PipelineNode<?> next() throws Exception {
        PipelineNode<?> newNode;
        if ((newNode = currentNode.nextNode()) != null) {
            currentNode.reset(); //Reset old node
            currentNode = newNode; //set new node
            currentNode.init(); //Init new node
        }
        return currentNode;
    }

    /**
     * Adds a node to the pipeline
     */
    public final void addNodes(PipelineNode<?>... nodes){
        this.nodes.addAll(Arrays.asList(nodes));
    }

    /**
     * @return all registered {@link PipelineNode}
     */
    public HashSet<PipelineNode<?>> getNodes() {
        return nodes;
    }

    /**
     * @return starting node of the pipeline
     */
    public PipelineNode<?> getCurrentNode() {
        return currentNode;
    }
}
