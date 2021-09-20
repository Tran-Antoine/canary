package ch.epfl.tc.io;

import net.dv8tion.jda.api.entities.MessageEmbed;

public interface DirectResponder {

    void respondText(String text);
    void respondText(String text, byte[] attachment, String fileName);
    void respondEmbed(MessageEmbed embed);
}
