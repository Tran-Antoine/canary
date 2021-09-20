package ch.epfl.tc.io;

import net.dv8tion.jda.api.audio.AudioSendHandler;

import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Queue;

public class SimpleAudioSendHandler implements AudioSendHandler {

    private final Queue<Byte> dataQueue;

    public SimpleAudioSendHandler() {
        this.dataQueue = new ArrayDeque<>();
    }

    @Override
    public boolean canProvide() {
        return dataQueue.size() >= 20;
    }

    @Override
    public ByteBuffer provide20MsAudio() {
        byte[] data = new byte[20];
        for(int i = 0; i < 20; i++) {
            data[i] = dataQueue.remove();
        }
        return ByteBuffer.wrap(data);
    }
}
