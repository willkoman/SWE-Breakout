package util;

import org.lwjgl.openal.*;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;

public class SoundManager {

    private static final List<ByteBuffer> soundBuffers = new ArrayList<>();

    public static int loadSound(String resourceName) {
        int bufferId = AL10.alGenBuffers();
        ByteBuffer soundBuffer = null;
        try (STBVorbisInfo ignored = STBVorbisInfo.malloc()) {
            MemoryStack.stackPush();
            IntBuffer channelsBuffer = MemoryStack.stackMallocInt(1);
            IntBuffer sampleRateBuffer = MemoryStack.stackMallocInt(1);

            // Load the sound file into a ByteBuffer
            try {
                InputStream stream = SoundManager.class.getResourceAsStream(resourceName);
                if (stream == null) {
                    throw new IOException("Resource not found: " + resourceName);
                }

                byte[] byteArray = stream.readAllBytes();
                soundBuffer = ByteBuffer.allocateDirect(byteArray.length);
                soundBuffer.put(byteArray).flip();
            } catch (IOException e) {
                throw new RuntimeException("Failed to load a sound file: " + resourceName, e);
            }

            // Decode the sound file
            ShortBuffer pcm = STBVorbis.stb_vorbis_decode_memory(soundBuffer, channelsBuffer, sampleRateBuffer);
            int channels = channelsBuffer.get(0);
            int sampleRate = sampleRateBuffer.get(0);

            // Create a new OpenAL buffer and fill it with audio data
            int format = channels == 1 ? AL10.AL_FORMAT_MONO16 : AL10.AL_FORMAT_STEREO16;
            assert pcm != null;
            AL10.alBufferData(bufferId, format, pcm, sampleRate);

            MemoryStack.stackPop();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add the soundBuffer to the list to prevent it from being garbage collected
        if (soundBuffer != null) {
            soundBuffers.add(soundBuffer);
        }

        return bufferId;
    }


    public static void playSound(String resourceName, float volume, boolean loop) {
        int sourceId = AL10.alGenSources();

        int bufferId = loadSound(resourceName);

        // Bind the sound file to the source ID
        AL10.alSourcei(sourceId, AL10.AL_BUFFER, bufferId);

        // Set the volume and loop properties
        AL10.alSourcef(sourceId, AL10.AL_GAIN, volume);
        AL10.alSourcei(sourceId, AL10.AL_LOOPING, loop ? AL10.AL_TRUE : AL10.AL_FALSE);

        // Play the sound
        AL10.alSourcePlay(sourceId);
    }

    public static void init() {
        // Create the OpenAL context
        long device = ALC10.alcOpenDevice((ByteBuffer) null);
        ALCCapabilities deviceCaps = ALC.createCapabilities(device);
        long context = ALC10.alcCreateContext(device, (IntBuffer) null);
        ALC10.alcMakeContextCurrent(context);
        AL.createCapabilities(deviceCaps);
    }


    public static void cleanup() {
        // Delete the OpenAL context
        long context = ALC10.alcGetCurrentContext();
        long device = ALC10.alcGetContextsDevice(context);
        ALC10.alcMakeContextCurrent(0);
        ALC10.alcDestroyContext(context);
        ALC10.alcCloseDevice(device);
    }
}
