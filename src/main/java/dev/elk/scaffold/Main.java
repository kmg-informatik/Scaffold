package dev.elk.scaffold;

import dev.elk.scaffold.events.TextureLoadEvent;
import dev.elk.scaffold.plugin.EventListener;
import dev.elk.scaffold.plugin.ExampleListener;
import dev.elk.scaffold.plugin.PluginRepository;

public class Main {

    public static void main(String[] args) {

        //Registers the listener to the repository
        PluginRepository.addListener(new ExampleListener());


        //Create a TextureLoadEvent
        TextureLoadEvent event = new TextureLoadEvent("assets/example.png");

        //Call the function #onLoadTexture() for each EventListener in the repository
        //using the basic lambda syntax (input)->doSomethingWith(input)
        PluginRepository.notifyAllOf(eventListener -> eventListener.onLoadTexture(event));

        //Calls the function #onShutdown() for each EventListener in the repository using
        //the prettier lambda syntax, because there are no parameters to #onShutdown()
        PluginRepository.notifyAllOf(EventListener::onShutdown);
    }
}