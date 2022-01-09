package dev.elk.scaffold.al;

// following tutorial: https://www.youtube.com/watch?v=Mrcs9vIHSws&list=PLUAzd5q-x4GruPjHogbyenywl1Tz_nd2Q&index=54

/**
 * AudioTrack objects are soundfiles (.ogg) loaded into OpenAL buffers.
 */

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;


public class AudioTrack {
	final int id;
	private ByteBuffer vorbis;
	private ShortBuffer pcm;
	
	private static List<AudioTrack> clips = new ArrayList<AudioTrack>();
	private static int i;
	
	/**
	 * Creates an AudioClip (soundtrack)
	 * 
	 * @param fileName	path to soundtrack (must be .ogg)
	 */
	public AudioTrack(String fileName) {
		id = alGenBuffers();
		
		try (STBVorbisInfo info = STBVorbisInfo.malloc()){
			ShortBuffer pcm = null;
			try {pcm = readVorbis(fileName, 32 * 1024, info);}
			catch (Exception e) {e.printStackTrace();}
			
			if (pcm == null) {
				System.err.println(fileName + " could not be imported!");
				return;
			}
			alBufferData(id, info.channels() == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16, pcm, info.sample_rate());
			clips.add(this);
		}
	}
	
	public final int getID() {return id;}
	public void Destroy() {alDeleteBuffers(id);}
	public static void CleanUp() {for (i = 0; i < clips.size(); i++) clips.get(0).Destroy();}	//destroy all clips loaded

	/**
	 * load and decode soundtrack to be used in code
	 * 
	 * @param resource		path to .ogg
	 * @param bufferSize	buffer size to load for playing audio
	 * @param info			
	 * @return				Buffer with loaded audio
	 * @throws Exception	
	 */
	private ShortBuffer readVorbis(String resource, int bufferSize, STBVorbisInfo info) throws Exception{
		try (MemoryStack stack = MemoryStack.stackPush()) {
			vorbis = ioResourceToByteBuffer(resource, bufferSize);
			IntBuffer error = stack.mallocInt(1);
			long decoder = stb_vorbis_open_memory(vorbis, error, null);
			if (decoder == 0) throw new RuntimeException("Failed to open .ogg Vorbis file. Error: " + error.get(0));
			
			stb_vorbis_get_info(decoder, info);
			
			int channels = info.channels();
			
			pcm = MemoryUtil.memAllocShort(stb_vorbis_stream_length_in_samples(decoder));
			
			pcm.limit(stb_vorbis_get_samples_short_interleaved(decoder, channels, pcm) * channels);
			stb_vorbis_close(decoder);
			
			return pcm;
		}
	}
	
	private ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException{
		ByteBuffer buffer;
		
		Path path = Paths.get(resource);
		if (Files.isReadable(path)) {
			try (SeekableByteChannel fc = Files.newByteChannel(path)){
				buffer = BufferUtils.createByteBuffer((int) fc.size() + 1);
				while (fc.read(buffer) != -1);
			}
		}
		else {
			try (InputStream source = AudioTrack.class.getResourceAsStream(resource); ReadableByteChannel rbc = Channels.newChannel(source)){
				buffer = BufferUtils.createByteBuffer(bufferSize);
				
				while (true) {
					int bytes = rbc.read(buffer);
					if (bytes == -1) {
						break;
					}
					if (buffer.remaining() == 0) {
						buffer.flip();
						buffer = BufferUtils.createByteBuffer(buffer.capacity() * 2).put(buffer);
					}
				}
			}
		}
		
		buffer.flip();
		return buffer;
	}
}

