package am.imdb.films.util.helper;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

@Slf4j
public class FileHelper {

    public static Set<File> splitFile(File file, int sizeOfFileInMB) throws FileNotFoundException, IOException {
        int counter = 0;
        long errorRows = 0;
        Set<File> files = new TreeSet<>();
        int sizeOfChunk = 1024 * 1024 * sizeOfFileInMB;
        String eof = System.lineSeparator();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String title = br.readLine();
            String line = br.readLine();

            while (Objects.nonNull(line)) {
                File newFile = new File(file.getParent(), String.format("%03d_%s", ++counter, file.getName()));
                try (OutputStream out = new BufferedOutputStream(new FileOutputStream(newFile))) {
                    int fileSize = 0;
                    line = title + eof + line;
                    StringBuilder builder = new StringBuilder();
                    while (Objects.nonNull(line)) {
                        byte[] bytes = (line + eof).getBytes(Charset.defaultCharset());
                        if (fileSize + bytes.length > sizeOfChunk) {
                            break;
                        } else {
                            builder.append(line).append(eof);
                            fileSize += bytes.length;
                            try {
                                line = br.readLine();
                            } catch (IOException e) {
                                errorRows++;
                            }
                        }
                    }
                    try {
                        out.write(builder.toString().getBytes(Charset.defaultCharset()));
                    } catch (IOException e) {
                        log.info(String.format("Failed to write %s lines", errorRows));
                    }
                    files.add(newFile);
                } catch (FileNotFoundException e) {
                    log.info("Could not create the file");
                }
            }
        }
        log.info(String.format("Failed to write %s lines", errorRows));
        return files;
    }

}
