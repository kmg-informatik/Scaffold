package dev.elk.scaffold.al;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioClip {
    private String filePath;
    private long playbackPosition;
    private Clip clip;
    private AudioClipStatus status;
    private AudioInputStream audioInputStream;

    /**
     * get clip from filepath
     *
     * @param filepath path to soundfile (.wav)
     */
    public AudioClip(String filepath) {
        this.filePath = filepath;
        this.clip = openClip(filepath);

        status = AudioClipStatus.LOADED;

    }


    private Clip openClip(String filepath) {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(filepath).getAbsoluteFile());

            // create clip reference
            clip = AudioSystem.getClip();
            // open audioInputStream to the clip
            clip.open(audioInputStream);

            clip.loop(Clip.LOOP_CONTINUOUSLY);  //TODO make loop optional

            return clip;

        } catch (Exception e) {
            e.printStackTrace();
            return null;    //TODO make better
        }
    }

    public void play() {
        clip.start();
        status = AudioClipStatus.PLAYING;
    }

    public void pause() {
        if (!(status == AudioClipStatus.PAUSED)) {
            playbackPosition = clip.getMicrosecondPosition();   //save playback position
            clip.stop();
            status = AudioClipStatus.PAUSED;
        }
    }

    public void resume() {
        try {
            if ((status == AudioClipStatus.PLAYING)) {
                clip.close();
                resetAudioStream(); //TODO can this method be deleted and code moved here?
                clip.setMicrosecondPosition(playbackPosition);
                play();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        openClip(this.filePath);
    }
}
