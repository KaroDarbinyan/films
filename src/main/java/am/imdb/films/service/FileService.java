package am.imdb.films.service;

import am.imdb.films.exception.FileNotCreateException;
import am.imdb.films.exception.FileNotExistException;
import am.imdb.films.exception.EntityNotFoundException;
import am.imdb.films.persistence.entity.FileEntity;
import am.imdb.films.persistence.repository.FileRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    private final String uploadDir;
    private final Path fileStorageLocation;
    private final FileRepository fileRepository;

    @Autowired
    public FileService(@Value("${file.upload-dir}") String uploadDir, FileRepository fileRepository) {
        this.uploadDir = uploadDir;
        this.fileRepository = fileRepository;
        this.fileStorageLocation = Paths.get(uploadDir)
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileNotExistException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }


    public FileEntity save(FileEntity entity) {
        return fileRepository.save(entity);
    }


    public void storeFile(MultipartFile file, String uploadPath) {
        try {
            File newFile = new File(uploadPath);
            if (!newFile.mkdirs()) {
                throw new IOException();
            }
            file.transferTo(newFile);
        } catch (IOException ex) {
            throw new FileNotCreateException("Could not store file " + file.getOriginalFilename() + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String path, String fileName) throws FileNotExistException {
        try {
            path = String.join(File.separator, uploadDir, path, fileName);
            Path filePath = this.fileStorageLocation.resolve(path).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) throw new MalformedURLException();

            return resource;
        } catch (MalformedURLException ex) {
            throw new FileNotExistException();
        }
    }

    public FileEntity getMerchantDocument(Long id) throws EntityNotFoundException {
        return fileRepository.findById(id).orElseThrow(EntityNotFoundException::new);

    }
}
