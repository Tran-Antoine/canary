package ch.epfl.tc.io;

import ch.epfl.tc.process.MusicQueue;

public class PlaytopCommand implements MusicCommand {

    @Override
    public String name() {
        return "playtop";
    }

    @Override
    public String description() {
        return "Put a song or playlist at the head of the queue";
    }

    @Override
    public MusicQueue execute(MusicQueue current, String[] args, DirectResponder responder) {
        return current;
    }
}
