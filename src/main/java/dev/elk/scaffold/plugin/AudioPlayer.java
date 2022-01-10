package dev.elk.scaffold.plugin;

import java.util.ArrayList;

import dev.elk.scaffold.al.AudioClip;
import dev.elk.scaffold.al.MusicClip;
import dev.elk.scaffold.al.SoundClip;
import dev.elk.scaffold.events.AudioEvent;

/**
 * Manages all audio being played, keeps track of MusicClips, as to pause/stop/restart them, only starts SoundClips.
 * Only works if every one MusicClip is only played once at a time (e.g.: music1, music 2: can play simultaneously;
 *                                                                  music 1, music 1: can't play simultaneously)
 */

public class AudioPlayer implements EventListening {

    private static AudioPlayer singleton = new AudioPlayer();
    private static ArrayList<MusicClip> musicClipsPlaying = new ArrayList<MusicClip>();    //saves currently playing music

    // #####################################################################################
    // define AudioClips (Sound/Music) with filepaths
    // #####################################################################################
    private static AudioClip soundJump = new SoundClip("Assets/Audios/sfx_movement_jump7.wav");
    private static MusicClip caveTheme = new MusicClip("Assets/Audios/cave_theme.wav", true);
    private static MusicClip melaTheme = new MusicClip("Assets/Audios/Ove Melaa -Supa Powa C.wav", false);

    private AudioPlayer(){

    }

    /**
     * ensure only one AudioPlayer exists, as only one is needed to manage all audio
     *
     * @return
     */
    public static AudioPlayer getSingleton(){
        return singleton;
    }

    // #####################################################################################
    // define methods that play audiofiles
    // #####################################################################################
    @Override
    public void onJump(AudioEvent event) {
        AudioPlayer.getSingleton().play(soundJump);
    }

    @Override
    public void onGameStart(AudioEvent event){
        AudioPlayer.getSingleton().play(caveTheme);
    }

    @Override
    public void onGamePause(AudioEvent event){
        AudioPlayer.getSingleton().pause(caveTheme);
    }

    @Override
    public void onGameStop(AudioEvent event){
        AudioPlayer.getSingleton().stop(caveTheme);
    }

    // #####################################################################################
    // define methods that interact with audiofiles
    // #####################################################################################
    /**
     * Plays an AudioClip
     * if it is a MusicClip, also adds it to the currently playing list so it can be stopped when quitting the program
     *
     * @param ac    AudioClip to play
     */
    private static void play(AudioClip ac) {
        if (ac instanceof MusicClip) {
            MusicClip mc = (MusicClip) ac;

            //check if MusicClip is in currently playing list; if not, add it
            boolean mcInMusicClipsPlaying = false;
            for (MusicClip mc2 : musicClipsPlaying){
                if (mc.equals(mc2)){
                    mcInMusicClipsPlaying = true;
                    break;
                }
            }
            if (!mcInMusicClipsPlaying){
                musicClipsPlaying.add(mc);
            }
        }
        ac.play();
    }

    /**
     * Pauses a MusicCLip
     *
     * @param mc    MusicClip
     */
    private static void pause(MusicClip mc) {
        mc.pause();
    }

    /**
     * Stops a MusicClip
     *
     * @param mc    MusicClip
     */
    private static void stop(MusicClip mc){
        mc.stop();
        musicClipsPlaying.remove(mc);
    }

    /**
     * Restarts a MusicClip
     *
     * @param mc    MusicClip
     */
    private static void restart(MusicClip mc){
        mc.restart();
    }

    /**
     * Destroys the AudioPlayer
     */
    public static void destroy(){
        for (MusicClip mc : musicClipsPlaying){
            mc.stop();
        }
        musicClipsPlaying.clear();
        singleton = null;
    }
}
