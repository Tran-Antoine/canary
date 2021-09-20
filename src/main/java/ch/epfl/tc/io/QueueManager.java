package ch.epfl.tc.io;

import ch.epfl.tc.process.MusicQueue;
import ch.epfl.tc.process.ObservableMusicQueue;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class QueueManager extends ListenerAdapter {

    private static final String PREFIX = "!";
    private static final String SCRIPT_PATH = "python/fetch.py";
    private static final Set<? extends MusicCommand> commands;

    private MusicQueue queue;

    static {
        commands = new HashSet<>(Arrays.asList(
                new PlayCommand(SCRIPT_PATH)
        ));
    }

    public QueueManager(MusicQueue initial) {
        this.queue = initial;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        String[] args = event
                .getMessage()
                .getContentRaw()
                .split(" ");

        if(args.length == 0) {
            return;
        }

        String[] cutArgs = new String[args.length - 1];
        System.arraycopy(args, 0, cutArgs, 0, cutArgs.length);

        for (MusicCommand command : commands) {
            if(args[0].equals(PREFIX + command.name())) {
                queue = command.execute(queue, cutArgs);
            }
        }
    }

    public ObservableMusicQueue getQueue() {
        return queue;
    }
}
