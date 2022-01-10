package dev.elk.scaffold.al;

import dev.elk.scaffold.events.AudioEvent;
import dev.elk.scaffold.plugin.AudioPlayer;

//TODEL testclass, can be deleted
public class AudioMain {

    public static void main(String[] args) {
        AudioPlayer ap = AudioPlayer.getSingleton();
        AudioEvent event = new AudioEvent();

        ap.onJump(event);
        ap.onGameStart(event);

        sleep(1000);

        ap.onGamePause(event);
        ap.onJump(event);

        sleep(1500);

        ap.onGameStart(event);
        ap.onJump(event);

        sleep(2000);

        ap.onGameStop(event);
        ap.onGameStart(event);




    }
    //yes I am aware this is stupid and we won't use this, but it was easier for testing TODEL
    private static void sleep(int ms){
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }
}
