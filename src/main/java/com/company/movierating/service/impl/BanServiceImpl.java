package com.company.movierating.service.impl;

import java.util.List;

import com.company.movierating.dao.BanDao;
import com.company.movierating.dao.entity.Ban;
import com.company.movierating.exception.service.NoRecordFoundException;
import com.company.movierating.service.BanService;
import com.company.movierating.service.converter.impl.BanConverter;
import com.company.movierating.service.dto.BanDto;
import com.company.movierating.service.util.BanValidator;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BanServiceImpl implements BanService {
    private final BanDao banDao;
    private final BanConverter banConverter;
    private final BanValidator validator;

    public BanServiceImpl(BanDao banDao, BanConverter banConverter, BanValidator validator) {
        this.banDao = banDao;
        this.banConverter = banConverter;
        this.validator = validator;
    }

    @Override
    public BanDto getById(Long id) {
        log.debug("Ban service method _getById_ was called");
        Ban entity = banDao.getById(id);
        if (entity == null) {
            throw new NoRecordFoundException("There is no ban with id= " + id);
        }
        return banConverter.toDto(entity);
    }

    @Override
    public List<BanDto> getAll() {
        log.debug("Ban service method _getAll_ was called");
        return banDao.getAll().stream() //
                .map(e -> banConverter.toDto(e)) //
                .toList();
    }

    @Override
    public List<BanDto> getAll(int limit, long offset) {
        log.debug("Ban service method _getAll_ (paged) was called");
        return banDao.getAll(limit, offset).stream() //
                .map(e -> banConverter.toDto(e)) //
                .toList();
    }

    @Override
    public List<BanDto> getAllByUser(Long id, int limit, long offset) {
        log.debug("Ban service method _getAllByUser_ (paged) was called");
        return banDao.getAllByUser(id, limit, offset).stream() //
                .map(e -> banConverter.toDto(e)) //
                .toList();
    }

    @Override
    public List<BanDto> getAllByAdmin(Long id, int limit, long offset) {
        log.debug("Ban service method _getAllByAdmin_ (paged) was called");
        return banDao.getAllByAdmin(id, limit, offset).stream() //
                .map(e -> banConverter.toDto(e)) //
                .toList();
    }

    @Override
    public BanDto create(BanDto dto) {
        log.debug("Ban service method _create_ was called");
        validator.validateBanToCreate(dto);
        Ban createdEntity = banDao.create(banConverter.toEntity(dto));
        return banConverter.toDto(createdEntity);
    }

    @Override
    public BanDto update(BanDto dto) {
        log.debug("Ban service method _update_ was called");
        BanDto dtoToUpdate = banConverter.toDto(banDao.getById(dto.getId()));

        dtoToUpdate.setEndDate(dto.getEndDate());
        validator.validateBanToUpdate(dtoToUpdate);

        Ban updatedEntity = banDao.update(banConverter.toEntity(dtoToUpdate));
        return banConverter.toDto(updatedEntity);
    }

    @Override
    public boolean delete(Long id) {
        log.debug("Ban service method _delete_ was called");
        return banDao.delete(id);
    }

    @Override
    public Long count() {
        log.debug("Ban service method _count_ was called");
        return banDao.count();
    }

    @Override
    public Long countByUser(Long id) {
        log.debug("Ban service method _countByUser_ was called");
        return banDao.countByUser(id);
    }

    @Override
    public Long countByAdmin(Long id) {
        log.debug("Ban service method _countByAdmin_ was called");
        return banDao.countByAdmin(id);
    }

}
