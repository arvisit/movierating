package com.company.movierating.service.impl;

import java.util.List;

import com.company.movierating.dao.BanDao;
import com.company.movierating.dao.entity.Ban;
import com.company.movierating.exception.service.NoRecordFoundException;
import com.company.movierating.service.BanService;
import com.company.movierating.service.dto.BanDto;
import com.company.movierating.service.util.BanValidator;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BanServiceImpl implements BanService {
    private final BanDao banDao;
    private final BanValidator validator;

    public BanServiceImpl(BanDao banDao, BanValidator validator) {
        this.banDao = banDao;
        this.validator = validator;
    }

    @Override
    public BanDto getById(Long id) {
        log.debug("Ban service method _getById_ was called");
        Ban entity = banDao.getById(id);
        if (entity == null) {
            throw new NoRecordFoundException("There is no ban with id= " + id);
        }
        return toDto(entity);
    }

    @Override
    public List<BanDto> getAll() {
        log.debug("Ban service method _getAll_ was called");
        return banDao.getAll().stream() //
                .map(this::toDto) //
                .toList();
    }

    @Override
    public List<BanDto> getAll(int limit, long offset) {
        log.debug("Ban service method _getAll_ (paged) was called");
        return banDao.getAll(limit, offset).stream() //
                .map(this::toDto) //
                .toList();
    }

    @Override
    public List<BanDto> getAllByUser(Long id, int limit, long offset) {
        log.debug("Ban service method _getAllByUser_ (paged) was called");
        return banDao.getAllByUser(id, limit, offset).stream() //
                .map(this::toDto) //
                .toList();
    }

    @Override
    public List<BanDto> getAllByAdmin(Long id, int limit, long offset) {
        log.debug("Ban service method _getAllByAdmin_ (paged) was called");
        return banDao.getAllByAdmin(id, limit, offset).stream() //
                .map(this::toDto) //
                .toList();
    }

    @Override
    public BanDto create(BanDto dto) {
        log.debug("Ban service method _create_ was called");
        validator.validateBanToCreate(dto);
        Ban createdEntity = banDao.create(toEntity(dto));
        return toDto(createdEntity);
    }

    @Override
    public BanDto update(BanDto dto) {
        log.debug("Ban service method _update_ was called");
        BanDto dtoToUpdate = toDto(banDao.getById(dto.getId()));

        dtoToUpdate.setEndDate(dto.getEndDate());
        validator.validateBanToUpdate(dtoToUpdate);

        Ban updatedEntity = banDao.update(toEntity(dtoToUpdate));
        return toDto(updatedEntity);
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

    private BanDto toDto(Ban entity) {
        BanDto dto = new BanDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setAdminId(entity.getAdminId());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setReason(entity.getReason());
        return dto;
    }

    private Ban toEntity(BanDto dto) {
        Ban entity = new Ban();
        entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        entity.setAdminId(dto.getAdminId());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setReason(dto.getReason());
        return entity;
    }
}
