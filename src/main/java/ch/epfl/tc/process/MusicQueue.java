package ch.epfl.tc.process;

import java.util.List;
import java.util.Random;

public interface MusicQueue extends ObservableMusicQueue {
    MusicQueue withShuffledTracks(Random random);
    MusicQueue withNextAsCurrent();
    MusicQueue withNewTrack(Track track, int index);
    MusicQueue withNewTrack(Track track);
    MusicQueue withSkippedTracks(int count);
    MusicQueue withPaused(boolean paused);
}
