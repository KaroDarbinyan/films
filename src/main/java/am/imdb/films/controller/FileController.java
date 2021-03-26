package am.imdb.films.controller;

import am.imdb.films.exception.EntityNotFoundException;
import am.imdb.films.exception.FileNotExistException;
import am.imdb.films.persistence.entity.StorageEntity;
import am.imdb.films.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("files")
public class FileController {

    private final StorageService storageService;

    @Autowired
    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") Long id, HttpServletRequest request) throws EntityNotFoundException, FileNotExistException {

        StorageEntity entity = storageService.getMerchantDocument(id);
        Resource resource = storageService.loadFileAsResource(entity.getPath(), entity.getFileName());

        try {
            String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", resource.getFilename()))
                    .body(resource);
        } catch (IOException ioException) {
            throw new FileNotExistException();
        }
    }
}
