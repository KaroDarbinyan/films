package am.imdb.films.service;

import am.imdb.films.exception.FileNotExistException;
import am.imdb.films.exception.EntityNotFoundException;
import am.imdb.films.persistence.entity.StorageEntity;
import am.imdb.films.persistence.repository.StorageRepository;
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
public class StorageService {

    private final String uploadDir;
    private final Path fileStorageLocation;
    private final StorageRepository storageRepository;

    @Autowired
    public StorageService(@Value("${file.upload-dir}") String uploadDir, StorageRepository storageRepository) {
        this.uploadDir = uploadDir;
        this.storageRepository = storageRepository;
        this.fileStorageLocation = Paths.get(uploadDir)
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileNotExistException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public StorageEntity storeFile(MultipartFile file, StorageEntity entity) {
        try {
            entity.setContentType(file.getContentType());
            entity.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
            entity.setFileName(String.join(".", String.valueOf(System.currentTimeMillis()), entity.getExtension()));

            String filePath = Paths.get(String.join(File.separator, uploadDir, entity.getPath(), entity.getFileName()))
                    .normalize().toString();
            File newFile = new File(filePath);
            if (!newFile.mkdirs()) {
                throw new IOException();
            }

            file.transferTo(newFile);
            return storageRepository.save(entity);
        } catch (IOException ex) {
            throw new FileNotExistException("Could not store file " + file.getOriginalFilename() + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String path, String fileName) throws FileNotExistException {
        try {
            Path filePath = this.fileStorageLocation.resolve(uploadDir + path + fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) throw new MalformedURLException();

            return resource;
        } catch (MalformedURLException ex) {
            throw new FileNotExistException();
        }
    }

    public StorageEntity getMerchantDocument(Long id) throws EntityNotFoundException {
        return storageRepository.findById(id).orElseThrow(EntityNotFoundException::new);

    }
}
