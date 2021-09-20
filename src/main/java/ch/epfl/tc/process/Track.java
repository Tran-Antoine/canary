package ch.epfl.tc.process;

import java.net.URL;

public interface Track {

    String getTitle();
    String getExtension();
    URL getUrl();
    long getFileSize();
    String getId();

}
