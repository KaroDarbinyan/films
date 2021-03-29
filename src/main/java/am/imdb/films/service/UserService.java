package am.imdb.films.service;

import am.imdb.films.exception.EntityNotFoundException;
import am.imdb.films.persistence.entity.FileEntity;
import am.imdb.films.persistence.entity.UserEntity;
import am.imdb.films.persistence.entity.relation.UserFileEntity;
import am.imdb.films.persistence.repository.UserFileRepository;
import am.imdb.films.persistence.repository.UserRepository;
import am.imdb.films.service.criteria.SearchCriteria;
import am.imdb.films.service.dto.base.BaseFileDto;
import am.imdb.films.service.dto.base.BaseUserDto;
import am.imdb.films.service.model.wrapper.QueryResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final FileService fileService;
    private final UserFileRepository userFileRepository;

    @Autowired
    public UserService(UserRepository userRepository, FileService fileService, UserFileRepository userFileRepository) {
        this.userRepository = userRepository;
        this.fileService = fileService;
        this.userFileRepository = userFileRepository;
    }


    public BaseUserDto createUser(BaseUserDto baseUserDto) {
        UserEntity userEntity = BaseUserDto.toEntity(baseUserDto, new UserEntity());
        UserEntity entity = userRepository.save(userEntity);
        return BaseUserDto.toBaseDto(entity);
    }

    public BaseUserDto getUser(Long id) throws EntityNotFoundException {
        UserEntity user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        return BaseUserDto.toBaseDto(user);
    }

    public BaseUserDto updateUser(Long id, BaseUserDto baseUserDto) throws EntityNotFoundException {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        BaseUserDto.toEntity(baseUserDto, userEntity);
        return BaseUserDto.toBaseDto(userRepository.save(userEntity));
    }

    public QueryResponseWrapper<BaseUserDto> getUsers(SearchCriteria criteria) {
        Page<BaseUserDto> content = userRepository.findAllWithPagination(criteria.composePageRequest());
        return new QueryResponseWrapper<>(content);
    }

    public void deleteUser(Long id) throws EntityNotFoundException {
        userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        userRepository.deleteById(id);
    }

    public BaseFileDto addFile(MultipartFile file, Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        FileEntity fileEntity = new FileEntity();
        fileEntity.setPath(String.format("user/%s", id));
        fileEntity = fileService.storeFile(file, fileEntity);
        UserFileEntity userFileEntity = new UserFileEntity();
        userFileEntity.setFile(fileEntity);
        userFileEntity.setUser(userEntity);
        userFileRepository.save(userFileEntity);

        return BaseFileDto.toBaseDto(fileEntity);
    }
}
