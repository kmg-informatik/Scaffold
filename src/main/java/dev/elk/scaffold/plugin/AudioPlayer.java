package dev.elk.scaffold.plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import dev.elk.scaffold.al.AudioClip;
import dev.elk.scaffold.events.AudioEvent;

public class AudioPlayer implements EventListening {

    private static final String soundJump = "resources/sfx_movement_jump7.wav";
    private static final String soundSceneStart = "";

    private static AudioPlayer singleton;
    private String status;
    private ArrayList<AudioClip> currentlyPlaying = new ArrayList<AudioClip>();

    private AudioPlayer(){

    }

    public static AudioPlayer getSingleton(){
        return singleton;
    }

    @Override
    public void onJump(AudioEvent event) {
        AudioPlayer.getSingleton().play(soundJump);
    }

    private void play(String filepath) {
        AudioClip clip = new AudioClip(filepath);
        currentlyPlaying.add(clip);
        clip.play();
    }

    private void pause(String filepath) {   //TODO prob needs id to work properly and select correct audio

    }
}
