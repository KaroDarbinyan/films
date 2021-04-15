package am.imdb.films.util.parser;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@Slf4j
public class CsvParser {

    public static <T> List<T> parse(File csvFile, Class<T> clazz) {
        try (Reader reader = new FileReader(csvFile)) {
            return new CsvToBeanBuilder<T>(reader)
                    .withType(clazz)
                    .withIgnoreEmptyLine(true)
                    .withThrowExceptions(false)
                    .build()
                    .parse();
        } catch (IOException e) {
            log.warn(e.getMessage(), clazz.getName());
        } catch (IllegalStateException ex) {
            log.warn(ex.getMessage());
        } catch (Exception exx) {
            System.out.println(exx.getMessage());
        }
        return List.of();
    }

}
