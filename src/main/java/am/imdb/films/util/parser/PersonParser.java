package am.imdb.films.util.parser;

import am.imdb.films.service.dto.PersonDto;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@Service
public class PersonParser {

    public List<PersonDto> csvParser(MultipartFile csvFile) throws IOException {

        try (Reader reader = new BufferedReader(new InputStreamReader(csvFile.getInputStream()))) {
            return new CsvToBeanBuilder<PersonDto>(reader)
                    .withType(PersonDto.class)
                    .build()
                    .parse();
        } catch (IOException e) {
            throw new FileNotFoundException("file not found");
        }
    }

}
