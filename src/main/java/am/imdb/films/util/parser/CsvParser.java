package am.imdb.films.util.parser;

import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@Service
public class CsvParser<T> {

    private final Logger logger = LoggerFactory.getLogger(CsvParser.class);

    public List<T> parse(File csvFile, Class<T> type) {
        try (Reader reader = new FileReader(csvFile)) {
            return new CsvToBeanBuilder<T>(reader)
                    .withType(type)
                    .withIgnoreEmptyLine(true)
                    .withThrowExceptions(false)
                    .build()
                    .parse();
        } catch (IOException e) {
            logger.warn(e.getMessage(), type.getName());
        } catch (IllegalStateException ex) {
            logger.warn(ex.getMessage());
        }catch (Exception exx) {
            System.out.println(exx.getMessage());
        }
        return List.of();
    }

}
