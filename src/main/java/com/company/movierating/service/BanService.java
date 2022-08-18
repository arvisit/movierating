package com.company.movierating.service;

import java.util.List;

import com.company.movierating.service.dto.BanDto;

public interface BanService extends AbstractService<Long, BanDto> {
    List<BanDto> getAllByUser(Long id, int limit, long offset);

    List<BanDto> getAllByAdmin(Long id, int limit, long offset);

    Long countByUser(Long id);

    Long countByAdmin(Long id);
}
