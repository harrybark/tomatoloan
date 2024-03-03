package com.devtomato.loan.service;

import com.devtomato.loan.domain.Counsel;
import com.devtomato.loan.dto.CounselDTO.Response;
import com.devtomato.loan.dto.CounselDTO.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CounselService
{
    Response create(Request request);

    Response get(Long counselId);

    Response update(Long counselId, Request request);

    void delete(Long counselId);
}
