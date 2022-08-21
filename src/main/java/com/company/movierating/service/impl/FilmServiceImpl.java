package com.company.movierating.service.impl;

import java.util.List;

import com.company.movierating.dao.FilmDao;
import com.company.movierating.dao.entity.Film;
import com.company.movierating.exception.service.NoRecordFoundException;
import com.company.movierating.service.FilmService;
import com.company.movierating.service.converter.impl.FilmConverter;
import com.company.movierating.service.dto.FilmDto;
import com.company.movierating.service.util.FilmValidator;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class FilmServiceImpl implements FilmService {
    private final FilmDao filmDao;
    private final FilmConverter filmConverter;
    private final FilmValidator filmValidator;
    
    public FilmServiceImpl(FilmDao filmDao, FilmConverter filmConverter, FilmValidator filmValidator) {
        this.filmDao = filmDao;
        this.filmConverter = filmConverter;
        this.filmValidator = filmValidator;
    }

    @Override
    public FilmDto getById(Long id) {
        log.debug("Film service method _getById_ was called");
        Film entity = filmDao.getById(id);
        if (entity == null) {
            throw new NoRecordFoundException("There is no film with id= " + id);
        }
        return filmConverter.toDto(entity);
    }

    @Override
    public List<FilmDto> getAll() {
        log.debug("Film service method _getAll_ was called");
        return filmDao.getAll().stream() //
                .map(filmConverter::toDto) //
                .toList();
    }

    @Override
    public List<FilmDto> getAll(int limit, long offset) {
        log.debug("Film service method _getAll_ (paged) was called");
        return filmDao.getAll(limit, offset).stream() //
                .map(filmConverter::toDto) //
                .toList();
    }

    @Override
    public FilmDto create(FilmDto dto) {
        log.debug("Film service method _create_ was called");
        filmValidator.validateFilm(dto);
        Film createdEntity = filmDao.create(filmConverter.toEntity(dto));
        return filmConverter.toDto(createdEntity);
    }

    @Override
    public FilmDto update(FilmDto dto) {
        log.debug("Film service method _update_ was called");
        filmValidator.validateFilm(dto);
        Film updatedEntity = filmDao.update(filmConverter.toEntity(dto));
        return filmConverter.toDto(updatedEntity);
    }

    @Override
    public boolean delete(Long id) {
        log.debug("Film service method _delete_ was called");
        return filmDao.delete(id);
    }

    @Override
    public Long count() {
        log.debug("Film service method _count_ was called");
        return filmDao.count();
    }

}
