package ch.epfl.tc.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class YoutubeDLUtility {
    private YoutubeDLUtility() {}

    private static final String SCRIPT_PATH = "python/youtube.py";
    private static final String PYTHON_EXEC = "py";

    public static String run(String... args) {
        try {
            String[] allArgs = new String[args.length + 2];
            allArgs[0] = PYTHON_EXEC;
            allArgs[1] = SCRIPT_PATH;
            System.arraycopy(args, 0, allArgs, 2, args.length);

            Process process = new ProcessBuilder(allArgs).start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String lines = reader.lines().reduce((s1, s2) -> s1 + "\n" + s2).orElse("");
            System.out.println(lines);

            process.waitFor();
            process.destroy();

            if(process.exitValue() != 0) {
                System.out.println("Warning: python script did not execute with value 0");
                throw new RuntimeException("Python script failed being executed with output : " + lines);
            }

            return lines;
        } catch (IOException | InterruptedException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static List<Track> fetchPlaylist(String playlistId) {
        return Arrays.stream(run("playlist", playlistId).split(Pattern.quote("\n")))
                .map(SimpleTrack::ofRawPythonOutput)
                .collect(Collectors.toUnmodifiableList());
    }

    public static Track fetchVideo(String videoId) {
        return SimpleTrack.ofRawPythonOutput(run("video", videoId));
    }

    public static Track queryFirstVideo(String query) {
        return SimpleTrack.ofRawPythonOutput(run("query", query));
    }

}
