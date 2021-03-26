package am.imdb.films.util.helper;

import am.imdb.films.exception.FileNotExistException;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
public class FileHelper {


    public Set<File> splitFile(File file, int sizeOfFileInMB) throws FileNotFoundException, IOException {
        int counter = 0;
        long errorRows = 0;
        Set<File> files = new HashSet<>();
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
                            try {
                                out.write(builder.toString().getBytes(Charset.defaultCharset()));
                            } catch (IOException e) {
                                log.info(String.format("Failed to write %s lines", errorRows));
                            }
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
                    files.add(newFile);
                } catch (FileNotFoundException e) {
                    log.info("Could not create the file");
                }
            }
        }
        log.info(String.format("Failed to write %s lines", errorRows));
        return files;
    }


    public Set<File> splitFile1(File file, int sizeOfFileInMB) {
        int counter = 0;
        Set<File> files = new HashSet<>();
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
                    while (Objects.nonNull(line)) {
                        byte[] bytes = (line + eof).getBytes(Charset.defaultCharset());
                        if (fileSize + bytes.length > sizeOfChunk)
                            break;
                        out.write(bytes);
                        fileSize += bytes.length;
                        line = br.readLine();
                    }
                    files.add(newFile);
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                } catch (IOException ioException) {
                    System.out.println(ioException.getMessage());
                }
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
        return files;
    }

    public Set<File> splitFiles(File file, int sizeOfFileInMB) throws FileNotFoundException, IOException, CsvValidationException {
        int counter = 1;
        Set<File> files = new HashSet<>();
        int sizeOfChunk = 1024 * 1024 * sizeOfFileInMB;
        String eof = System.lineSeparator();


        try (CSVParser csvParser = CSVFormat.DEFAULT.parse(new FileReader(file))) {
            int i = 0;
            Iterator<CSVRecord> iterator = csvParser.iterator();

            String title = iterator.hasNext() ? createRow(iterator.next()) : null;
            String line;

            while (iterator.hasNext()) {
                line = createRow(iterator.next());
                File newFile = new File(file.getParent(), String.format("%03d_%s", counter++, file.getName()));
                try (OutputStream out = new BufferedOutputStream(new FileOutputStream(newFile))) {
                    int fileSize = 0;
                    line = title + eof + line;
                    while (Objects.nonNull(line)) {
                        byte[] bytes = (line + eof).getBytes(Charset.defaultCharset());
                        if (fileSize + bytes.length > sizeOfChunk)
                            break;
                        out.write(bytes);
                        fileSize += bytes.length;
                        line = iterator.hasNext() ? createRow(iterator.next()) : null;
                    }
                }
                files.add(newFile);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


//        try (CSVReader reader = new CSVReader(new FileReader(file))) {
//            String name = file.getName();
//            String title = String.join(",", reader.readNext());
//            String line = String.join(",", reader.readNext());
//
//            while (Objects.nonNull(line)) {
//                File newFile = new File(file.getParent(), String.format("%03d_%s", counter++, name));
//                try (OutputStream out = new BufferedOutputStream(new FileOutputStream(newFile))) {
//                    int fileSize = 0;
//                    line = title + eof + line;
//                    while (Objects.nonNull(line)) {
//                        byte[] bytes = (line + eof).getBytes(Charset.defaultCharset());
//                        if (fileSize + bytes.length > sizeOfChunk)
//                            break;
//                        out.write(bytes);
//                        fileSize += bytes.length;
//                        String[] tempLineArr = reader.readNext();
//                        line = tempLineArr != null ? String.join(",", tempLineArr) : null;
//                    }
//                }
//                files.add(newFile);
//            }
//        }
        return files;
    }

    private String createRow(CSVRecord record) {
        return StreamSupport.stream(record.spliterator(), false)
                .collect(Collectors.joining(",")).replaceAll("\n", " ");
    }

    public Set<File> createFiles(Set<File> files) {
        return files.stream()
                .map(file -> {
                    try {
                        return file.createNewFile() ? file : null;
                    } catch (IOException e) {
                        return null;
                    }
                }).filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

}
