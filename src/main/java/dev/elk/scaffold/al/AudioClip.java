package dev.elk.scaffold.al;

import javax.sound.sampled.Clip;

/**
 * AudioClip defines methods and variables for MusicClip and SoundClip.
 *
 * @author Eric Jacob
 */
public abstract class AudioClip {
    private final String filePath;
    private Clip clip;

    /**
     * get clip from filepath
     *
     * @param filepath path to soundfile (.wav)
     */
    public AudioClip(String filepath) {
        this.filePath = filepath;
    }

    public Clip getClip() {
        return this.clip;
    }

    /**
     * used by MusicClip to set the clip after going through a different openClip() method (done so MusicClip can save the AudioInputStream)
     *
     * @param clip the Clip to set this.clip to
     */
    public void setClip(Clip clip) {
        this.clip = clip;
    }

    /**
     * @return this.filePath
     */
    public String getFilePath() {
        return this.filePath;
    }

    /**
     * Plays this AudioClip
     */
    public abstract void play();

}
