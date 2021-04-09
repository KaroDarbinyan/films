package am.imdb.films.util.helper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileDownloader {

    private URL url;
    private String outputFileName;

    public FileDownloader(URL url, String outputFileName) {
        this.url = url;
        this.outputFileName = outputFileName;
    }

    public void downloadFile() throws IOException {
        try (InputStream in = url.openStream();
             ReadableByteChannel rbc = Channels.newChannel(in);
             FileOutputStream fos = new FileOutputStream(outputFileName)) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
    }

    public void downloadFile1() throws Exception {
        try (InputStream in = url.openStream()) {
            Files.copy(in, Paths.get(outputFileName));
        }
    }

}
