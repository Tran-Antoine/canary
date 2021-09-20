package ch.epfl.tc.io;

import ch.epfl.tc.process.MusicQueue;

public interface MusicCommand {

    String name();
    String description();

    MusicQueue execute(MusicQueue current, String[] args, DirectResponder responder);
}
