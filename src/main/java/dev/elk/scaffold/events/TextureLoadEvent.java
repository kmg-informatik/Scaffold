package dev.elk.scaffold.events;

/**
 * Example event for demonstration of the plugin code
 */

public class TextureLoadEvent extends Event {

    private final String texturePath;

    public TextureLoadEvent(String texturePath) {
        this.texturePath = texturePath;
    }

    public String getTexturePath() {
        return texturePath;
    }
}
