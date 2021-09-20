package ch.epfl.tc.io;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

public class ChannelResponder implements DirectResponder {

    private final TextChannel channel;

    private ChannelResponder(TextChannel channel) {
        this.channel = channel;
    }

    public static ChannelResponder fromChannel(TextChannel channel) {
        return new ChannelResponder(channel);
    }

    @Override
    public void respondText(String text) {
        channel.sendMessage(text).queue();
    }

    @Override
    public void respondText(String text, byte[] attachment, String fileName) {
        channel.sendMessage(text)
                .addFile(attachment, fileName)
                .queue();
    }

    @Override
    public void respondEmbed(MessageEmbed embed) {
        channel.sendMessage(embed).queue();
    }
}
