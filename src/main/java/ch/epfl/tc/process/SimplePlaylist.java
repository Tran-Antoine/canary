package ch.epfl.tc.process;

import java.util.*;

public final class SimplePlaylist implements Playlist {
    private final List<Track> tracks;

    public SimplePlaylist(List<Track> tracks) {
        this.tracks = new LinkedList<>(tracks);
    }

    @Override
    public List<Track> tracks() {
        return new ArrayList<>(tracks);
    }

    @Override
    public Playlist withShuffledTracks(Random random) {
        List<Track> shuffledTracks = new ArrayList<>(tracks);
        Collections.shuffle(shuffledTracks);
        return new SimplePlaylist(shuffledTracks);
    }

    @Override
    public Playlist withNextAsCurrent() {
        List<Track> newTracks = new LinkedList<>(tracks);
        newTracks.remove(0);
        return new SimplePlaylist(newTracks);
    }

    @Override
    public Playlist withNewTrack(Track track, int index) {
        List<Track> newTracks = new ArrayList<>(tracks);
        newTracks.add(index, track);
        return new SimplePlaylist(newTracks);
    }

    public Playlist withNewTrack(Track track) {
        return withNewTrack(track, tracks.size());
    }

    @Override
    public Track currentTrack() {
        return tracks.get(0);
    }
}
