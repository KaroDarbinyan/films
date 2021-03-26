package am.imdb.films.util.parser;

import am.imdb.films.service.dto.RatingDto;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@Service
public class RatingParser {

    //todo rename to parseCSV
    public List<RatingDto> csvParser(MultipartFile csvFile) throws IOException {

        try (Reader reader = new BufferedReader(new InputStreamReader(csvFile.getInputStream()))) {
            List<RatingDto> parse = new CsvToBeanBuilder<RatingDto>(reader)
                    .withType(RatingDto.class)
//                    .withIgnoreEmptyLine(true)
                    .build()
                    .parse();
            return parse;
        } catch (IOException e) {
            throw new FileNotFoundException("file not found");
        }
    }

}
