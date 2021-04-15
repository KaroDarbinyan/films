package am.imdb.films.service.control;

import am.imdb.films.exception.FileNotExistException;
import am.imdb.films.util.helper.FileHelper;
import am.imdb.films.util.parser.CsvParser;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CsvControl<T> {

    @Value("${csv.upload.dir}")
    private String uploadDir;
    private final int SIZE_OF_FILE_IN_MB = 5;


    public List<List<T>> getEntitiesFromCsv(MultipartFile csvFile, Class<T> type) throws FileNotExistException {
        String pathname = String.join(File.separator, uploadDir, UUID.randomUUID().toString(), csvFile.getOriginalFilename());
        File parentCsv = new File(pathname);
        List<List<T>> list = List.of();
        if (parentCsv.mkdirs()) {
            try {
                csvFile.transferTo(parentCsv);
                list = FileHelper.splitFile(parentCsv, SIZE_OF_FILE_IN_MB)
                        .stream()
                        .map(file -> CsvParser.parse(file, type))
                        .filter(ts -> !ts.isEmpty())
                        .collect(Collectors.toList());
                FileUtils.deleteDirectory(parentCsv.getParentFile());
            } catch (Exception e) {
                throw new FileNotExistException(String.format("Could not create the file %s", csvFile.getOriginalFilename()));
            }
        }
        return list;
    }

}
