package ch.epfl.tc.io;

import ch.epfl.tc.process.MusicQueue;

public class PauseCommand implements MusicCommand {

    @Override
    public String name() {
        return "pause";
    }

    @Override
    public String description() {
        return "Pause or resume the queue";
    }

    @Override
    public MusicQueue execute(MusicQueue current, String[] args, DirectResponder responder) {
        return current.withPaused(!current.isPaused());
    }
}
