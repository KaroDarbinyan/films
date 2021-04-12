package am.imdb.films.controller;


import am.imdb.films.exception.EntityNotFoundException;
import am.imdb.films.security.config.session.SessionUser;
import am.imdb.films.service.UserService;
import am.imdb.films.service.criteria.SearchCriteria;
import am.imdb.films.service.criteria.UserSearchCriteria;
import am.imdb.films.service.dto.MovieDto;
import am.imdb.films.service.dto.UserDto;
import am.imdb.films.service.model.resultset.UploadFileResponseWrapper;
import am.imdb.films.service.model.wrapper.QueryResponseWrapper;
import am.imdb.films.service.validation.model.Create;
import am.imdb.films.service.validation.model.Update;
import am.imdb.films.service.validation.validator.fileextension.UploadFileExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static am.imdb.films.security.config.session.SessionUser.SESSION_USER_KEY;
import static am.imdb.films.service.validation.model.FileExtension.*;

@Validated
@RestController
@RequestMapping("users")
@SessionAttributes(SESSION_USER_KEY)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<UserDto> addUser(@RequestBody @Validated(Create.class) UserDto userDto) {
        UserDto user = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable("id") Long id,
            @Validated(Update.class)
            @RequestBody UserDto userDto) throws EntityNotFoundException {
        UserDto user = userService.updateUser(id, userDto);

        return ResponseEntity.ok(user);
    }

    @GetMapping
    public QueryResponseWrapper<UserDto> getUsers(UserSearchCriteria criteria) {
        return userService.getUsers(criteria);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable(value = "id") Long id) throws EntityNotFoundException {
        userService.deleteUser(id);
    }

    @PostMapping("/{id}/images")
    public UploadFileResponseWrapper uploadImage(
            @RequestParam(value = "image")
            @UploadFileExtension(extensions = {JPEG, JPG, PNG, SVG, PNG}) MultipartFile image,
            @PathVariable("id") Long id) {

        return userService.addFile(image, id);
    }

    @PutMapping("/{id}/images/{imageId}/profile-pic")
    public ResponseEntity<UserDto> changeProfilePic(@PathVariable("id") Long id, @PathVariable("imageId") Long imageId) {
        UserDto userDto = userService.changeProfilePic(id, imageId);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/movies/favorite-list")
    public QueryResponseWrapper<MovieDto> getFavorites(@ModelAttribute(SESSION_USER_KEY) SessionUser sessionUser, SearchCriteria searchCriteria) {
        return userService.getFavorites(sessionUser.getId(), searchCriteria);
    }
}
