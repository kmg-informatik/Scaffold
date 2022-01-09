package dev.elk.scaffold.plugin;

import dev.elk.scaffold.events.AudioEvent;

public interface AudioListening extends EventListening{

    default void onBeginAudio(AudioEvent event){ //falls das die Play()-Funktion sein soll...

    }

    default void onPauseAudio(AudioEvent event){}

    //...

}
