package dev.elk.scaffold.components;

import dev.elk.scaffold.gl.Window;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

public class PipelineNode {

    /**
     * Scene of the pipeline element
     */
    private final Scene scene;

    /**
     * Current window
     * @apiNote Shouldn't change
     */
    private final Window window;

    /**
     * Links are functions, that have an input of a {@link PipelineNode} and link
     * to a new node.
     */
    private final ArrayList<Function<PipelineNode, PipelineNode>> links = new ArrayList<>();

    public PipelineNode(Scene scene, Window window){
        this.scene = scene;
        this.window = window;
    }

    /**
     * Initialises the {@link Scene} of the {@link PipelineNode}
     */
    public void init() throws Exception {
        scene.init();
    }

    public void reset(){
        scene.reset();
    }

    /**
     * Adds new links to the pipeline.
     */
    @SafeVarargs
    public final void addLinks(final Function<PipelineNode, PipelineNode>...links){
        this.links.addAll(Arrays.asList(links));
    }

    /**
     * Moves to the next node in the pipeline.
     * @apiNote iterates through all existing nodes. If there is no link left,
     * null is returned, meaning that the scene should end.
     * @return null, if there is no connection to another node. Else, it returns
     * the next node which is different to the last node.
     */
    public PipelineNode nextNode(){
        for (Function<PipelineNode, PipelineNode> link : links) {
            PipelineNode node = link.apply(this);

            //if different node and node is not null
            if (node != this && node != null) return node;
        }
        return null;
    }

    /**
     * @return all links
     */
    public ArrayList<Function<PipelineNode, PipelineNode>> getLinks() {
        return links;
    }

    /**
     * @return Scene of the this node
     */
    public Scene getScene() {
        return scene;
    }

    @Override
    public String toString() {
        return "PipelineNode{" +
                "scene=" + scene +
                '}';
    }
}
