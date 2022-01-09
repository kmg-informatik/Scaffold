package dev.elk.scaffold.plugin;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;

import dev.elk.scaffold.al.AudioTrack;
import dev.elk.scaffold.events.AudioEvent;

public class AudioPlayer implements EventListening {

    private static long device;
    private static ALCCapabilities deviceCaps;
    private static long context;

    public AudioPlayer(){
        setup();
    }

    public void Play(AudioEvent event){
        event.getSource().Play();
    }

    public void Pause(AudioEvent event){
        event.getSource().Pause();
    }

    public void Stop(AudioEvent event){
        event.getSource().Stop();
    }

    @Override
    public void onPlayAudio(AudioEvent event) { //TODO not sure on how to use this/what to use it for

    }

    /**
     * Setup method for the AudioPlayer.
     * Initializes devices and speakers. Must be called before first play of any track/event.
     * TODO: could also auto-call this when event is created, based on boolean if setup has been done.
     */
    private static void setup() {
        device = ALC10.alcOpenDevice((ByteBuffer) null);
		deviceCaps = ALC.createCapabilities(device);
		context = ALC10.alcCreateContext(device, (IntBuffer) null);
		ALC10.alcMakeContextCurrent(context);
		AL.createCapabilities(deviceCaps);

    }

    /**
     * cleanup when shutting down.
     * must be called at the end of any game, otherwise Windows will complain.
     */
    public static void shutdown() {
        AudioTrack.CleanUp();
		ALC10.alcCloseDevice(device);
    }
}
