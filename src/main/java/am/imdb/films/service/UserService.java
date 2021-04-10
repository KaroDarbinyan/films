package am.imdb.films.service;

import am.imdb.films.exception.EntityNotFoundException;
import am.imdb.films.persistence.entity.FileEntity;
import am.imdb.films.persistence.entity.MovieEntity;
import am.imdb.films.persistence.entity.UserEntity;
import am.imdb.films.persistence.entity.relation.UserFileEntity;
import am.imdb.films.persistence.repository.MovieRepository;
import am.imdb.films.persistence.repository.UserFileRepository;
import am.imdb.films.persistence.repository.UserRepository;
import am.imdb.films.service.criteria.SearchCriteria;
import am.imdb.films.service.criteria.UserSearchCriteria;
import am.imdb.films.service.dto.MovieDto;
import am.imdb.films.service.dto.UserDto;
import am.imdb.films.service.model.wrapper.QueryResponseWrapper;
import am.imdb.films.service.model.wrapper.UploadFileResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    @Value("${file.upload-dir}")
    String uploadDir;

    private final UserRepository userRepository;
    private final FileService fileService;
    private final UserFileRepository userFileRepository;
    private final PasswordEncoder bcryptEncoder;
    private final MovieRepository movieRepository;

    @Autowired
    public UserService(UserRepository userRepository, FileService fileService, UserFileRepository userFileRepository, PasswordEncoder bcryptEncoder, MovieRepository movieRepository) {
        this.userRepository = userRepository;
        this.fileService = fileService;
        this.userFileRepository = userFileRepository;
        this.bcryptEncoder = bcryptEncoder;
        this.movieRepository = movieRepository;
    }


    public UserDto createUser(UserDto userDto) {
        UserEntity userEntity = UserDto.toEntity(userDto, new UserEntity());
        userEntity.setStatus("ACTIVE");
        userEntity.setPasswordHash(bcryptEncoder.encode(userDto.getPassword()));

        UserEntity entity = userRepository.save(userEntity);
        return UserDto.toDto(entity);
    }

    public UserDto getUser(Long id) throws EntityNotFoundException {
        UserEntity user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        return UserDto.toDto(user);
    }

    public UserDto updateUser(Long id, UserDto userDto) throws EntityNotFoundException {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        UserDto.toEntity(userDto, userEntity);
        return UserDto.toDto(userRepository.save(userEntity));
    }

    public QueryResponseWrapper<UserDto> getUsers(UserSearchCriteria criteria) {
        Page<UserEntity> content = userRepository.findAllWithPagination(criteria, criteria.composePageRequest());
        return new QueryResponseWrapper<>(content.map(UserDto::toDto));
    }

    public void deleteUser(Long id) throws EntityNotFoundException {
        userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        userRepository.deleteById(id);
    }

    public UploadFileResponseWrapper addFile(MultipartFile file, Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        FileEntity entity = new FileEntity();

        entity.setPath(String.format("user/%s", id));
        entity.setContentType(file.getContentType());
        entity.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
        entity.setFileName(String.join(".", String.valueOf(System.currentTimeMillis()), entity.getExtension()));

        String uploadPath = Paths.get(String.join(File.separator, uploadDir, entity.getPath(), entity.getFileName()))
                .normalize().toString();

        fileService.storeFile(file, uploadPath);
        FileEntity fileEntity = fileService.save(entity);

        UserFileEntity userFileEntity = new UserFileEntity();
        userFileEntity.setFile(fileEntity);
        userFileEntity.setUser(userEntity);
        userFileRepository.save(userFileEntity);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/")
                .path(fileEntity.getId().toString())
                .toUriString();

        return UploadFileResponseWrapper.builder()
                .fileName(fileEntity.getFileName())
                .fileDownloadUri(fileDownloadUri)
                .fileType(fileEntity.getContentType())
                .size(file.getSize())
                .build();
    }

    public UserDto changeProfilePic(Long userId, Long fileId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        List<UserFileEntity> userFileEntityList = userEntity.getListOfUserFile().stream()
                .peek(entity -> entity.setGeneral(Objects.equals(entity.getId(), fileId)))
                .collect(Collectors.toList());
        userFileRepository.saveAll(userFileEntityList);

        return UserDto.toDto(userEntity);
    }

    public QueryResponseWrapper<MovieDto> getFavorites(Long id, SearchCriteria searchCriteria) {
        Page<MovieEntity> content = movieRepository.findUserFavorites(id, searchCriteria.composePageRequest());
        return new QueryResponseWrapper<>(content.map(MovieDto::toDto));
    }
}
