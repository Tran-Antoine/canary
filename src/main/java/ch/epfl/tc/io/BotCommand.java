package ch.epfl.tc.io;

public interface BotCommand {

    String name();
    String description();

    void execute(String[] args);
}
