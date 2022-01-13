package dev.elk.scaffold.components;

import dev.elk.scaffold.gl.Window;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Function;

/**
 * Pipeline nodes make up the {@link GamePipeline}. They wrap a {@link Scene} and each contain
 * links to other nodes. These links are functions, that take a pipeline node as an input,
 * and output a new pipeline node. <br>
 * If the new pipeline node is null or the same as the input node, then the link does not trigger.
 * Else, the link triggers and the next node is used returned.<br>
 * This allows the user to create an abstract  network of logic that links scenes to eachother.
 * @param <E> Type of the wrapped Scene, allowing access to class-specific functions of E.
 */
public class PipelineNode<E extends Scene> {

    /**
     * Scene of the pipeline element
     */
    private final E scene;

    /**
     * Current window
     * @apiNote Shouldn't change, not used yet though.
     */
    private final Window window;

    /**
     * Links are functions, that have an input of a {@link PipelineNode} and link
     * to a new node.
     */
    private final HashSet<Function<PipelineNode<E>, PipelineNode<?>>> links = new HashSet<>();

    public PipelineNode(E scene){
        this.scene = scene;
        this.window = scene.window;
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
    public final void addLinks(final Function<PipelineNode<E>, PipelineNode<?>>...links){
        this.links.addAll(Arrays.asList(links));
    }

    /**
     * Moves to the next node in the pipeline.
     * @apiNote iterates through all existing nodes. If there is no link left,
     * null is returned, meaning that the scene should end.
     * @return null, if there is no connection to another node. Else, it returns
     * the next node which is different to the last node.
     */
    public PipelineNode<?> nextNode(){
        for (Function<PipelineNode<E>, PipelineNode<?>> link : links) {
            PipelineNode<?> node = link.apply(this);
            if (node != this && node != null) return node;
        }
        return null;
    }

    /**
     * @return all links
     */
    public HashSet<Function<PipelineNode<E>, PipelineNode<?>>> getLinks() {
        return links;
    }

    /**
     * @return Scene of the this node
     */
    public E getScene() {
        return scene;
    }

    @Override
    public String toString() {
        return "PipelineNode{" +
                "scene=" + scene +
                '}';
    }
}
