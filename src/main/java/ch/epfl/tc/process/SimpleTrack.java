package ch.epfl.tc.process;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Pattern;

public class SimpleTrack implements Track {
    private final String title;
    private final String extension;
    private final URL url;
    private final long fileSize;
    private final String id;

    public SimpleTrack(String title, String extension, URL url, long fileSize, String id) {
        this.title = title;
        this.extension = extension;
        this.url = url;
        this.fileSize = fileSize;
        this.id = id;
    }

    private static String decode64(String encoded) {
        return new String(Base64.getDecoder().decode(encoded));
    }

    public static SimpleTrack ofRawPythonOutput(String output) {
        String[] var = output.split(Pattern.quote(","));
        assert (var.length == 5);

        String id = decode64(var[0]);
        String title = decode64(var[1]);
        URL url;
        try {
             url = new URL(decode64(var[2]));
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        String ext = decode64(var[3]);
        long fileSize = Long.decode(var[4]);
        return new SimpleTrack(title, ext, url, fileSize, id);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getExtension() {
        return extension;
    }

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    public long getFileSize() {
        return fileSize;
    }

    @Override
    public String getId() {
        return id;
    }
}
