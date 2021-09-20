package ch.epfl.tc.io;

import ch.epfl.tc.process.MusicQueue;

public class PlayCommand implements MusicCommand {

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
    public MusicQueue execute(MusicQueue current, String[] args, DirectResponder responder) {

        MusicQueue result = current;

        /*String arg = String.join(" ", args);
        try {
            Process process = new ProcessBuilder("py", scriptPath, arg).start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while((line = reader.readLine()) != null) {
                result = result.withNewTrack(new SimpleTrack(line));
            }

            process.waitFor();
            process.destroy();

            if(process.exitValue() != 0) {
                System.out.println("Warning: python script did not execute with value 0");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }*/

        return result;
    }
}
