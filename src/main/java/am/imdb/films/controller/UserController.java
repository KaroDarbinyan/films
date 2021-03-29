package am.imdb.films.controller;


import am.imdb.films.exception.EntityNotFoundException;
import am.imdb.films.service.UserService;
import am.imdb.films.service.criteria.SearchCriteria;
import am.imdb.films.service.dto.base.BaseFileDto;
import am.imdb.films.service.dto.base.BaseUserDto;
import am.imdb.films.service.model.validation.Create;
import am.imdb.films.service.model.validation.Update;
import am.imdb.films.service.model.wrapper.QueryResponseWrapper;
import am.imdb.films.service.model.wrapper.UploadFileResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

//@Validated
@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<BaseUserDto> addUser(@RequestBody @Validated(Create.class) BaseUserDto userDto) {
        BaseUserDto user = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseUserDto> getUser(@PathVariable("id") Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseUserDto> updateUser(
            @PathVariable("id") Long id,
            @Validated(Update.class)
            @RequestBody BaseUserDto userDto) throws EntityNotFoundException {
        BaseUserDto user = userService.updateUser(id, userDto);

        return ResponseEntity.ok(user);
    }

    @GetMapping
    public QueryResponseWrapper<BaseUserDto> getUsers(SearchCriteria criteria) {
        return userService.getUsers(criteria);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable(value = "id") Long id) throws EntityNotFoundException {
        userService.deleteUser(id);
    }

    @PostMapping("/upload-file")
    public UploadFileResponseWrapper uploadFile(@RequestParam("file") MultipartFile file,
                                                @RequestParam("userId") Long userId) {

        BaseFileDto fileDto = userService.addFile(file, userId);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/")
                .path(fileDto.getId().toString())
                .toUriString();

        return UploadFileResponseWrapper.builder()
                .fileName(fileDto.getFileName())
                .fileDownloadUri(fileDownloadUri)
                .fileType(file.getContentType())
                .size(file.getSize())
                .build();
    }
}
