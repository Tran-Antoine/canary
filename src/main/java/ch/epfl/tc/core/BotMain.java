package ch.epfl.tc.core;

import ch.epfl.tc.io.QueueManager;
import ch.epfl.tc.process.SimpleMusicQueue;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.Collections;

public class BotMain {

    public static void main(String[] args) throws Exception {

        if(args.length == 0) {
            System.err.println("No token provided");
            return;
        }

        JDABuilder builder = JDABuilder.createDefault(args[0]);
        JDA jda = builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
                .setBulkDeleteSplittingEnabled(false)
                .setActivity(Activity.listening("weeb music :peepodreaming:"))
                .build();

        QueueManager manager = new QueueManager(new SimpleMusicQueue(Collections.emptyList()));
        jda.addEventListener(manager);
    }
}