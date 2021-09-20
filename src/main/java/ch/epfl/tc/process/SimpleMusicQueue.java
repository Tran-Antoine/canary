package ch.epfl.tc.process;

import java.util.*;

public final class SimpleMusicQueue implements MusicQueue {

    private final List<Track> tracks;
    private boolean paused;

    public SimpleMusicQueue(List<Track> tracks) {
        this(tracks, false);
    }

    public SimpleMusicQueue(List<Track> tracks, boolean paused) {
        this.tracks = new LinkedList<>(tracks);
        this.paused = paused;
    }

    @Override
    public List<Track> tracks() {
        return Collections.unmodifiableList(tracks);
    }

    @Override
    public MusicQueue withShuffledTracks(Random random) {
        List<Track> shuffledTracks = new ArrayList<>(tracks);
        Collections.shuffle(shuffledTracks);
        return new SimpleMusicQueue(shuffledTracks, paused);
    }

    @Override
    public MusicQueue withNextAsCurrent() {
        List<Track> newTracks = new LinkedList<>(tracks);
        newTracks.remove(0);
        return new SimpleMusicQueue(newTracks, paused);
    }

    @Override
    public MusicQueue withNewTrack(Track track, int index) {
        List<Track> newTracks = new ArrayList<>(tracks);
        newTracks.add(index, track);
        return new SimpleMusicQueue(newTracks, paused);
    }

    @Override
    public MusicQueue withNewTrack(Track track) {
        return withNewTrack(track, tracks.size());
    }

    @Override
    public MusicQueue withSkippedTracks(int count) {
        int realCount = Math.min(count, tracks.size() - 1);
        List<Track> shuffledTracks = new ArrayList<>(tracks.subList(realCount, tracks.size()-1));
        return new SimpleMusicQueue(shuffledTracks, paused);
    }

    @Override
    public Track currentTrack() {
        return tracks.get(0);
    }

    @Override
    public MusicQueue withPaused(boolean paused) {
        return null;
    }

    @Override
    public boolean isPaused() {
        return false;
    }
}
