package dev.elk.scaffold.al;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * MusicClip is an extension of AudioClip meant for longer audios (music) that may need be paused/stopped/restarted at certain points. <br>
 * Therefore it has an additional set of methods: <br>
 * + pause:     pause the clip at current playback position to be resumed later (e.g. when the player opens a dialogue box, talks to an NPC or pauses the game) <br>
 * + stop:      stop currently playing track and discard playback position (music is restarted when calling play) <br>
 * + restart:   restarts the audio track, plays it from the beginning immediately <br>
 * The play method has also been extended in functionality: <br>
 * - plays the saved clip ONLY if it has been properly loaded beforehand (required to prevent trying to play when track has been stopped and discarded) <br>
 * - if the status is PAUSED (i.e. if pause() has been called before), music is resumed at last playback position <br>
 * - if the status is STOPPED (i.e. stop() has been called before), the clip is reloaded/restarted and played from the beginning. <br>
 * <p>
 * For some scenarios: <br>
 * - if you want to start playing the track the first time, call play() <br>
 * - if you want to pause and later resume the track, call pause() and then play() <br>
 * - if you want to start the track from the beginning, call restart() <br>
 * - if you want to stop the music, call stop() <br>
 *
 * @author Eric Jacob
 */
public class MusicClip extends AudioClip {

    private long playbackPosition;
    private MusicClipStatus status;
    private AudioInputStream audioInputStream;
    private boolean shouldMusicClipLoop = false;

    /**
     * get clip from filepath
     *
     * @param filepath            path to soundfile (.wav)
     * @param shouldMusicClipLoop if true, music clip will loop after finished playing
     */
    public MusicClip(String filepath, boolean shouldMusicClipLoop) {
        super(filepath);
        this.shouldMusicClipLoop = shouldMusicClipLoop;
        openClip();
    }

    /**
     * open the audio clip specified in filePath, save it to this.clip
     * also saves AudioInputStream (unlike SoundClip)
     */
    private void openClip() {
        try {
            this.audioInputStream = AudioSystem.getAudioInputStream(new File(this.getFilePath()).getAbsoluteFile());
            Clip clip;
            // create clip reference
            clip = AudioSystem.getClip();
            // open audioInputStream to the clip
            clip.open(audioInputStream);

            this.setClip(clip);
            this.status = MusicClipStatus.LOADED;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * start playing the MusicClip, if status = LOADED (e.g. the music track is loaded into memory)
     * OR call resume() if status = PAUSED
     * OR call restart() if status = STOPPED
     */
    public void play() {
        if (this.status == MusicClipStatus.LOADED) { //only execute play on clip if clip is loaded, otherwise figure out whether to resume or restart

            if (this.shouldMusicClipLoop) {
                this.getClip().loop(Clip.LOOP_CONTINUOUSLY);
                /*
                    See: https://coderedirect.com/questions/283881/audio-clip-wont-loop-continuously
                    will only loop track if audio isn't the online thread alive
                 */
            }

            this.getClip().start();
            this.status = MusicClipStatus.PLAYING;
        }
        if (this.status == MusicClipStatus.PAUSED) {
            resume();
        } else if (this.status == MusicClipStatus.STOPPED) {
            restart();
        }
    }

    /**
     * "pause" this clip, save playback position to be able to resume
     */
    public void pause() {
        if (!(this.status == MusicClipStatus.PAUSED)) {
            this.playbackPosition = this.getClip().getMicrosecondPosition();   //save playback position
            this.getClip().stop();
            this.status = MusicClipStatus.PAUSED;
        }
    }

    /**
     * "resume" playing the music clip at pause position
     * this is called by play() if status = PAUSED
     * (stops track, reloads it and sets playback position before playing)
     */
    private void resume() {
        try {
            this.getClip().close();
            resetAudioStream();
            this.getClip().setMicrosecondPosition(playbackPosition);
            this.status = MusicClipStatus.LOADED;   // track has been loaded with correct playback resume position
            play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * reload the current (last played) track
     *
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    private void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.audioInputStream = AudioSystem.getAudioInputStream(new File(this.getFilePath()).getAbsoluteFile());
        this.getClip().open(audioInputStream);

        if (this.shouldMusicClipLoop) {
            this.getClip().loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * stop currently playing audio, reset playback position
     */
    public void stop() {
        this.getClip().stop();
        this.getClip().close();
        this.playbackPosition = 0L;
        this.status = MusicClipStatus.STOPPED;
    }

    /**
     * restart audio, reset playback position
     */
    public void restart() {
        try {
            stop();
            resetAudioStream();
            this.getClip().setMicrosecondPosition(0);
            this.status = MusicClipStatus.LOADED;
            this.play();
        } catch (Exception e) {  //UnsupportedAudioFileException, IOException, LineUnavailableException
            e.printStackTrace();
        }
    }

    /**
     * @return enum MusicClipStatus
     */
    public MusicClipStatus getStatus() {
        return status;
    }

}
