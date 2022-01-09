package dev.elk.scaffold.al;

// following tutorial: https://www.youtube.com/watch?v=Mrcs9vIHSws&list=PLUAzd5q-x4GruPjHogbyenywl1Tz_nd2Q&index=54

/**
 * AudioSource objects are players of AudioTracks.
 */

import static org.lwjgl.openal.AL10.*;

import java.util.List;
import java.util.ArrayList;

public class AudioSource {
	public AudioTrack clip;
	public float startGain = -30;
	
	private final int id;
	
	private static List<AudioSource> sources = new ArrayList<AudioSource>();
	
	public AudioSource() {
		id = alGenSources();
		SetGain(startGain);
		sources.add(this);
	}
	
	/**
	 * starts playing loaded AudioTrack
	 */
	public void Play() {
		SetGain(startGain);
		alSourcePlay(id);
	}
	
	/**
	 * set clip to play
	 * 
	 * @param c	AudioTrack
	 */
	public void SetClip(AudioTrack c) {clip = c; alSourcei(id, AL_BUFFER, c.id);}
	
	/**
	 * pause playback (remember playback position)
	 */
	public void Pause() {alSourcePause(id);};
	
	/**
	 * stop playback (forget playback position; restarts when calling Play() after)
	 */
	public void Stop() {alSourceStop(id);}
	
	/**
	 * wonky volume control, not reliable
	 * In theory, controls the input volume of the track into the system, NOT the output volume of the speakers.
	 * depending on the system's and (if available) speaker's volume, does change the volume.
	 * not always working
	 * 
	 * @param v	float volume (normal = -30, see above)
	 */
	public void SetGain(float v) {alSourcef(id, AL_GAIN, v);}
	
	/**
	 * destroy audio source
	 */
	public void Destroy() {
		Stop();
		alDeleteSources(id);
		sources.remove(this);
	}
	
	/**
	 * destroy loaded audio sources
	 */
	public static void CleanUp() {for(int i = 0; i < sources.size(); i++) sources.get(0).Destroy();}

}
