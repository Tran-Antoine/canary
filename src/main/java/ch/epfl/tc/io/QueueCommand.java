package ch.epfl.tc.io;

import ch.epfl.tc.process.MusicQueue;
import net.dv8tion.jda.api.EmbedBuilder;

public class QueueCommand implements MusicCommand {

    @Override
    public String name() {
        return "queue";
    }

    @Override
    public String description() {
        return "Display the current queue";
    }

    @Override
    public MusicQueue execute(MusicQueue current, String[] args, DirectResponder responder) {
        EmbedBuilder builder = new EmbedBuilder();
        // TODO: construct builder properly and use the responder
        return current;
    }
}
