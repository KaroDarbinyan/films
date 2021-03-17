package am.imdb.films.controller;


import am.imdb.films.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("ratings")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }


    @PostMapping("/import-from-csv-file")
    public ResponseEntity<Map<String, Long>> uploadCSVFile(@RequestParam(name = "file") MultipartFile csvFile) throws Exception {

        if (csvFile.isEmpty()) {
            throw new Exception("Required request part 'file' is not present");
        }
        if (!Objects.equals(csvFile.getContentType(), "text/csv")) {
            throw new Exception("The file must be in csv format");
        }

        Map<String, Long> result = ratingService.parseCSV(csvFile);
        return ResponseEntity.ok().body(result);
    }


}
