package ch.epfl.tc.process;

import java.util.*;

public final class SimpleMusicQueue implements MusicQueue {
    private final List<Track> tracks;

    public SimpleMusicQueue(List<Track> tracks) {
        this.tracks = new LinkedList<>(tracks);
    }

    @Override
    public List<Track> tracks() {
        return new ArrayList<>(tracks);
    }

    @Override
    public MusicQueue withShuffledTracks(Random random) {
        List<Track> shuffledTracks = new ArrayList<>(tracks);
        Collections.shuffle(shuffledTracks);
        return new SimpleMusicQueue(shuffledTracks);
    }

    @Override
    public MusicQueue withNextAsCurrent() {
        List<Track> newTracks = new LinkedList<>(tracks);
        newTracks.remove(0);
        return new SimpleMusicQueue(newTracks);
    }

    @Override
    public MusicQueue withNewTrack(Track track, int index) {
        List<Track> newTracks = new ArrayList<>(tracks);
        newTracks.add(index, track);
        return new SimpleMusicQueue(newTracks);
    }

    public MusicQueue withNewTrack(Track track) {
        return withNewTrack(track, tracks.size());
    }

    @Override
    public Track currentTrack() {
        return tracks.get(0);
    }
}
