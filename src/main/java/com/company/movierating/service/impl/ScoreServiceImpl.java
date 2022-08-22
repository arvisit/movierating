package com.company.movierating.service.impl;

import java.util.List;

import com.company.movierating.dao.ScoreDao;
import com.company.movierating.dao.entity.Score;
import com.company.movierating.exception.service.NoRecordFoundException;
import com.company.movierating.service.ScoreService;
import com.company.movierating.service.converter.impl.ScoreConverter;
import com.company.movierating.service.dto.ScoreDto;
import com.company.movierating.service.util.ScoreValidator;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ScoreServiceImpl implements ScoreService {
    private final ScoreDao scoreDao;
    private final ScoreConverter scoreConverter;
    private final ScoreValidator scoreValidator;

    public ScoreServiceImpl(ScoreDao scoreDao, ScoreConverter scoreConverter, ScoreValidator scoreValidator) {
        this.scoreDao = scoreDao;
        this.scoreConverter = scoreConverter;
        this.scoreValidator = scoreValidator;
    }

    @Override
    public ScoreDto getById(Long id) {
        log.debug("Score service method _getById_ was called");
        Score entity = scoreDao.getById(id);
        if (entity == null) {
            throw new NoRecordFoundException("There is no score with id= " + id);
        }
        return scoreConverter.toDto(entity);

    }

    @Override
    public List<ScoreDto> getAll() {
        log.debug("Score service method _getAll_ was called");
        return scoreDao.getAll().stream() //
                .map(scoreConverter::toDto) //
                .toList();
    }

    @Override
    public List<ScoreDto> getAll(int limit, long offset) {
        log.debug("Score service method _getAll_ (paged) was called");
        return scoreDao.getAll(limit, offset).stream() //
                .map(scoreConverter::toDto) //
                .toList();
    }

    @Override
    public ScoreDto create(ScoreDto dto) {
        log.debug("Score service method _create_ was called");
        scoreValidator.validateScoreToCreate(dto);
        Score createdEntity = scoreDao.create(scoreConverter.toEntity(dto));
        return scoreConverter.toDto(createdEntity);
    }

    @Override
    public ScoreDto update(ScoreDto dto) {
        log.debug("Score service method _update_ was called");
        scoreValidator.validateScoreToUpdate(dto);
        Score updatedEntity = scoreDao.update(scoreConverter.toEntity(dto));
        return scoreConverter.toDto(updatedEntity);
    }

    @Override
    public boolean delete(Long id) {
        log.debug("Score service method _delete_ was called");
        return scoreDao.delete(id);
    }

    @Override
    public Long count() {
        log.debug("Score service method _count_ was called");
        return scoreDao.count();
    }

}
