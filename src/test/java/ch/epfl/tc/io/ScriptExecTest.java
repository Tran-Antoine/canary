package ch.epfl.tc.io;

import ch.epfl.tc.process.MusicQueue;
import ch.epfl.tc.process.Track;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

public class ScriptExecTest {

    @Test
    public void python_script_is_successfully_executed_and_future_action_is_performed() {

        PlayCommand command = new PlayCommand("src/test/resources/test_script.py");
        System.out.println("Launching script");
        command.execute(new MockMusicQueue(), new String[]{}, null);
        System.out.println("Script executed");
    }

    private static class MockMusicQueue implements MusicQueue {
        @Override
        public List<Track> tracks() {
            return null;
        }

        @Override
        public boolean isPaused() {
            return false;
        }

        @Override
        public MusicQueue withShuffledTracks(Random random) {
            return null;
        }

        @Override
        public MusicQueue withNextAsCurrent() {
            return null;
        }

        @Override
        public MusicQueue withNewTrack(Track track, int index) {
            return null;
        }

        @Override
        public MusicQueue withNewTrack(Track track) {
            return null;
        }

        @Override
        public MusicQueue withSkippedTracks(int count) {
            return null;
        }

        @Override
        public MusicQueue withPaused(boolean paused) {
            return null;
        }

        @Override
        public Track currentTrack() {
            return null;
        }
    }
}
