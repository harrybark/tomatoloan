package com.devtomato.loan.service;

import com.devtomato.loan.domain.Application;
import com.devtomato.loan.dto.ApplicationDTO;
import com.devtomato.loan.dto.ApplicationDTO.Request;
import com.devtomato.loan.dto.ApplicationDTO.Response;
import com.devtomato.loan.exception.BaseException;
import com.devtomato.loan.exception.ResultType;
import com.devtomato.loan.repository.ApplicationRespository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationServiceImpl implements ApplicationService {

    private final ModelMapper modelMapper;
    private final ApplicationRespository applicationRespository;

    @Override
    @Transactional(readOnly = false)
    public Response create(Request request) {
        Application application = modelMapper.map(request, Application.class);
        application.setAppliedAt(LocalDateTime.now());

        Application savedApplication = applicationRespository.save(application);

        return modelMapper.map(savedApplication, Response.class);
    }

    @Override
    public Response get(Long applicationId) {
        Application application = applicationRespository.findById(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });
        return modelMapper.map(application, Response.class);
    }

    @Override
    @Transactional(readOnly = false)
    public Response update(Long applicationId, Request request) {
        Application application = applicationRespository.findById(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        application.setName(request.getName());
        application.setEmail(request.getEmail());
        application.setCellPhone(request.getCellPhone());
        application.setHopeAmount(request.getHopeAmount());

        return modelMapper.map(application, Response.class);
    }
}
