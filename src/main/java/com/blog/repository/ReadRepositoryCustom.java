package com.blog.repository;

import com.blog.dto.MainReadDto;
import com.blog.dto.ReadSearchDto;
import com.blog.entity.Read;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadRepositoryCustom {
    Page<Read> getAdminReadPage(ReadSearchDto readSearchDto, Pageable pageable);

    Page<MainReadDto> getMainReadPage(ReadSearchDto readSearchDto, Pageable pageable);
}
