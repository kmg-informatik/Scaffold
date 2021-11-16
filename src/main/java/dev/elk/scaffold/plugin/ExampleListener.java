package dev.elk.scaffold.plugin;

import dev.elk.scaffold.events.TextureLoadEvent;

/**
 * Example event for demonstration of the plugin code
 */
public class ExampleListener extends EventListener{

    @Override
    public void onLoadTexture(TextureLoadEvent event) {
        System.out.printf("Loaded texture %s", event.getTexturePath());
    }

    @Override
    public void onShutdown() {
        System.out.println("Shutting down now!");
    }
}
