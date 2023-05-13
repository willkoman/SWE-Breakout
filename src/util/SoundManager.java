package util;

import org.lwjgl.openal.*;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class SoundManager {

    public static void loadSound(String soundFile) {
        int bufferId = AL10.alGenBuffers();
        try (STBVorbisInfo ignored = STBVorbisInfo.malloc()) {
            MemoryStack.stackPush();
            IntBuffer channelsBuffer = MemoryStack.stackMallocInt(1);
            IntBuffer sampleRateBuffer = MemoryStack.stackMallocInt(1);

            // Load the sound file using LWJGL STB Vorbis library
            ShortBuffer pcm = STBVorbis.stb_vorbis_decode_filename(soundFile, channelsBuffer, sampleRateBuffer);
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
    }


    public static void playSound(String soundFile, float volume, boolean loop) {
        int sourceId = AL10.alGenSources();

        int bufferId = AL10.alGenBuffers();
        try (STBVorbisInfo ignored = STBVorbisInfo.malloc()) {
            MemoryStack.stackPush();
            IntBuffer channelsBuffer = MemoryStack.stackMallocInt(1);
            IntBuffer sampleRateBuffer = MemoryStack.stackMallocInt(1);

            // Load the sound file using LWJGL STB Vorbis library
            ShortBuffer pcm = STBVorbis.stb_vorbis_decode_filename(soundFile, channelsBuffer, sampleRateBuffer);
            int channels = channelsBuffer.get();
            int sampleRate = sampleRateBuffer.get();

            // Create a new OpenAL buffer and fill it with audio data
            int format = channels == 1 ? AL10.AL_FORMAT_MONO16 : AL10.AL_FORMAT_STEREO16;
            assert pcm != null;
            AL10.alBufferData(bufferId, format, pcm, sampleRate);

            MemoryStack.stackPop();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Bind the sound file to the source ID
        AL10.alSourcei(sourceId, AL10.AL_BUFFER, bufferId);

        // Set the volume and loop properties
        AL10.alSourcef(sourceId, AL10.AL_GAIN, volume);
        AL10.alSourcei(sourceId, AL10.AL_LOOPING, loop ? AL10.AL_TRUE : AL10.AL_FALSE);

        // Play the sound
        AL10.alSourcePlay(sourceId);

    }

//    public static void pauseSound(int sourceId) {
//        AL10.alSourcePause(sourceId);
//    }
//
//    public static void stopSound(int sourceId) {
//        AL10.alSourceStop(sourceId);
//    }

    public static void init() {
        // Create the OpenAL context
        long device = ALC10.alcOpenDevice((ByteBuffer) null);
        ALCCapabilities deviceCaps = ALC.createCapabilities(device);
        long context = ALC10.alcCreateContext(device, (IntBuffer) null);
        ALC10.alcMakeContextCurrent(context);
        AL.createCapabilities(deviceCaps);
    }

    // ...

    public static void cleanup() {
        // Delete the OpenAL context
        long context = ALC10.alcGetCurrentContext();
        long device = ALC10.alcGetContextsDevice(context);
        ALC10.alcMakeContextCurrent(0);
        ALC10.alcDestroyContext(context);
        ALC10.alcCloseDevice(device);
    }
}
