package ch.epfl.tc.process;

import java.util.List;

public interface ObservableMusicQueue {

    Track currentTrack();
    List<Track> tracks();
    boolean isPaused();
}
