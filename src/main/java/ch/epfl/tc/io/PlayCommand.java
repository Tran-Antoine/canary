package ch.epfl.tc.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PlayCommand implements BotCommand {

    private final String scriptPath;

    public PlayCommand(String scriptPath) {
        this.scriptPath = scriptPath;
    }

    @Override
    public String name() {
        return "play";
    }

    @Override
    public String description() {
        return "Locally download data of track/playlist then adds it to the queue";
    }

    @Override
    public void execute(String[] args) {

        String arg = String.join(" ", args);
        try {
            Process process = new ProcessBuilder("python3", scriptPath, arg).start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            List<String> paths = new ArrayList<>();

            String line;
            while((line = reader.readLine()) != null) {
                paths.add(line);
            }

            process.waitFor();
            process.destroy();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
