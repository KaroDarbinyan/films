package am.imdb.films.controller;


import am.imdb.films.persistence.entity.GenreEntity;
import am.imdb.films.persistence.repository.GenreRepository;
import am.imdb.films.service.dto.GenreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("test")
public class TestController {

    private final GenreRepository genreRepository;



}
