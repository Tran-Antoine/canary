package ch.epfl.tc.process;

import java.util.List;
import java.util.Random;

public interface Playlist {
    List<Track> tracks();
    Playlist withShuffledTracks(Random random);
    Playlist withNextAsCurrent();
    Playlist withNewTrack(Track track, int index);
    Playlist withNewTrack(Track track);
    Track currentTrack();
}
