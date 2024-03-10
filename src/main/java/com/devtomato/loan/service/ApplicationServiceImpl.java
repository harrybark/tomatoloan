package com.devtomato.loan.service;

import com.devtomato.loan.domain.Application;
import com.devtomato.loan.domain.Terms;
import com.devtomato.loan.domain.AcceptTerms;

import com.devtomato.loan.dto.ApplicationDTO.AcceptTermsDTO;
import com.devtomato.loan.dto.ApplicationDTO.Request;
import com.devtomato.loan.dto.ApplicationDTO.Response;
import com.devtomato.loan.exception.BaseException;
import com.devtomato.loan.exception.ResultType;
import com.devtomato.loan.repository.AcceptTermsRepository;
import com.devtomato.loan.repository.ApplicationRepository;
import com.devtomato.loan.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationServiceImpl implements ApplicationService {

    private final ModelMapper modelMapper;

    private final TermsRepository termsRepository;

    private final AcceptTermsRepository acceptTermsRepository;
    private final ApplicationRepository applicationRepository;

    @Override
    @Transactional(readOnly = false)
    public Response create(Request request) {
        Application application = modelMapper.map(request, Application.class);
        application.setAppliedAt(LocalDateTime.now());

        Application savedApplication = applicationRepository.save(application);

        return modelMapper.map(savedApplication, Response.class);
    }

    @Override
    public Response get(Long applicationId) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });
        return modelMapper.map(application, Response.class);
    }

    @Override
    @Transactional(readOnly = false)
    public Response update(Long applicationId, Request request) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        application.setName(request.getName());
        application.setEmail(request.getEmail());
        application.setCellPhone(request.getCellPhone());
        application.setHopeAmount(request.getHopeAmount());

        return modelMapper.map(application, Response.class);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long applicationId) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        application.setIsDeleted(true);
    }

    @Override
    public Boolean acceptTerms(Long applicationId, AcceptTermsDTO request) {

        applicationRepository.findById(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        List<Terms> termsList = termsRepository.findAll(Sort.by(Direction.ASC, "termsId"));
        if (termsList.isEmpty()) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        List<Long> acceptTermsIds = request.getAcceptTermsIds();
        if (termsList.size() != acceptTermsIds.size()) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        List<Long> termsIds = termsList.stream().map(Terms::getTermsId).collect(Collectors.toList());
        Collections.sort(acceptTermsIds);

        if (!termsIds.containsAll(acceptTermsIds)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        for (Long termsId : acceptTermsIds) {
            AcceptTerms accepted = AcceptTerms.builder()
                    .termsId(termsId)
                    .applicationId(applicationId)
                    .build();

            acceptTermsRepository.save(accepted);
        }

        return true;
    }
}
