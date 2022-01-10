package dev.elk.scaffold.al;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.concurrent.CountDownLatch;

/**
 * SoundClip is meant for short sounds, e.g. when jumping, that are not to be interacted with besides being played. <br>
 * Therefore, SoundClip has no pause/stop/restart methods and AudioPlayer does not keep track of them;
 * they are instantiated to play a given sound, then garbage-collected.
 */

public class SoundClip extends AudioClip {

    /**
     * get clip from filepath
     *
     * @param filepath path to soundfile (.wav)
     */
    public SoundClip(String filepath) {
        super(filepath);
        openClip();
    }

    /**
     * open the audio clip specified in filePath, save it to this.clip
     */
    private void openClip() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(this.getFilePath()).getAbsoluteFile());

            Clip clip;
            // create clip reference
            clip = AudioSystem.getClip();
            // open audioInputStream to the clip
            clip.open(audioInputStream);

            this.setClip(clip);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * play the clip from the beginning
     * (setting frames is important, because a sound can only be played once otherwise, as it will always start at the end for each new play call)
     */
    public void play() {
        this.getClip().setMicrosecondPosition(0);
        this.getClip().start();
    }

}
