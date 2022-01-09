package dev.elk.scaffold.events;

import dev.elk.scaffold.al.AudioSource;
import dev.elk.scaffold.al.AudioTrack;

public class AudioEvent extends Event{

    private final String audioName; //TODO final might not be as useful if track should be changeable later on
    private AudioTrack track;
    private AudioSource source;

    public AudioEvent(String audioName){
        this.audioName = audioName;
        this.track = new AudioTrack(audioName);
        
        this.source = new AudioSource();
        this.source.SetClip(this.track);
    }

    public AudioTrack getTrack(){
        return this.track;
    }

    public AudioSource getSource(){
        return this.source;
    }


}
