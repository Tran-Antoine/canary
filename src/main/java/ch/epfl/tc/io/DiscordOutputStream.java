package ch.epfl.tc.io;

import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class DiscordOutputStream extends OutputStream {

    private final AudioManager manager;

    public DiscordOutputStream(AudioManager manager) {
        this.manager = manager;
    }

    @Override
    public void write(int b) {
        manager.setSendingHandler(new AudioSendHandler() {
            @Override
            public boolean canProvide() {
                return false;
            }

            @Nullable
            @Override
            public ByteBuffer provide20MsAudio() {
                return new DirectByteBuffer();
            }
        });
    }
}
