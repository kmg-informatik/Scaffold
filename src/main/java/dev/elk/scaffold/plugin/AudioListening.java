package dev.elk.scaffold.plugin;

import dev.elk.scaffold.events.AudioEvent;

public interface AudioListening extends EventListening{

    default void onBeginAudio(AudioEvent event){}

    default void onPauseAudio(AudioEvent event){}

    //...

}
