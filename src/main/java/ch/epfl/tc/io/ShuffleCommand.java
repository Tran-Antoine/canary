package ch.epfl.tc.io;

import ch.epfl.tc.process.MusicQueue;

import java.util.Random;

public class ShuffleCommand implements MusicCommand {

    private static final Random RANDOM = new Random();

    @Override
    public String name() {
        return "shuffle";
    }

    @Override
    public String description() {
        return "Shuffle the current playlist";
    }

    @Override
    public MusicQueue execute(MusicQueue current, String[] args, DirectResponder responder) {
        return current.withShuffledTracks(RANDOM);
    }
}
