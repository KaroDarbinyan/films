package am.imdb.films.service;

import am.imdb.films.exception.EntityNotFoundException;
import am.imdb.films.persistence.entity.FileEntity;
import am.imdb.films.persistence.entity.UserEntity;
import am.imdb.films.persistence.entity.relation.UserFileEntity;
import am.imdb.films.persistence.repository.UserFileRepository;
import am.imdb.films.persistence.repository.UserRepository;
import am.imdb.films.service.criteria.SearchCriteria;
import am.imdb.films.service.dto.FileDto;
import am.imdb.films.service.dto.UserDto;
import am.imdb.films.service.model.wrapper.QueryResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final FileService fileService;
    private final UserFileRepository userFileRepository;
    private final PasswordEncoder bcryptEncoder;

    @Autowired
    public UserService(UserRepository userRepository, FileService fileService, UserFileRepository userFileRepository, PasswordEncoder bcryptEncoder) {
        this.userRepository = userRepository;
        this.fileService = fileService;
        this.userFileRepository = userFileRepository;
        this.bcryptEncoder = bcryptEncoder;
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

    public QueryResponseWrapper<UserDto> getUsers(SearchCriteria criteria) {
        Page<UserDto> content = userRepository.findAllWithPagination(criteria.composePageRequest());
        return new QueryResponseWrapper<>(content);
    }

    public void deleteUser(Long id) throws EntityNotFoundException {
        userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        userRepository.deleteById(id);
    }

    public FileDto addFile(MultipartFile file, Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        FileEntity fileEntity = new FileEntity();
        fileEntity.setPath(String.format("user/%s", id));
        fileEntity = fileService.storeFile(file, fileEntity);
        UserFileEntity userFileEntity = new UserFileEntity();
        userFileEntity.setFile(fileEntity);
        userFileEntity.setUser(userEntity);
        userFileRepository.save(userFileEntity);

        return FileDto.toDto(fileEntity);
    }
}
