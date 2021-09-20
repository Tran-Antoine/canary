package ch.epfl.tc.io;

import ch.epfl.tc.process.MusicQueue;

public class SkipCommand implements MusicCommand {

    @Override
    public String name() {
        return "skip";
    }

    @Override
    public String description() {
        return "Skip one or several tracks";
    }

    @Override
    public MusicQueue execute(MusicQueue current, String[] args, DirectResponder responder) {

        int count = 1;
        if(args.length != 0) {
            try {
                count = Integer.parseInt(args[0]);
            } catch (NumberFormatException ignored) {}
        }

        return current.withSkippedTracks(count);
    }
}
