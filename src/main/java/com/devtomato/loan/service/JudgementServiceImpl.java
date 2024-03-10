package com.devtomato.loan.service;

import static com.devtomato.loan.dto.JudgementDTO.*;
import static com.devtomato.loan.exception.ResultType.SYSTEM_ERROR;

import com.devtomato.loan.domain.Judgement;
import com.devtomato.loan.exception.BaseException;
import com.devtomato.loan.repository.ApplicationRepository;
import com.devtomato.loan.repository.JudgementRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JudgementServiceImpl implements JudgementService {

    private final ModelMapper modelMapper;

    private final ApplicationRepository applicationRespository;
    private final JudgementRepository judgementRepository;

    @Override
    @Transactional(readOnly = false)
    public Response create(Request request) {
        // 신청 정보 검증
        Long applicationId = request.getApplicationId();

        if( !isPresentApplication(applicationId)) {
            throw new BaseException(SYSTEM_ERROR);
        }

        Judgement entity = modelMapper.map(request, Judgement.class);
        Judgement judgement = judgementRepository.save(entity);
        return modelMapper.map(judgement, Response.class);
    }

    @Override
    public Response get(Long judgementId) {
        Judgement judgement = judgementRepository.findById(judgementId).orElseThrow(() -> {
            throw new BaseException(SYSTEM_ERROR);
        });

        return modelMapper.map(judgement, Response.class);
    }

    @Override
    public Response getJudgementOfApplication(Long applicationId) {
        if(!isPresentApplication(applicationId)) {
            throw new BaseException(SYSTEM_ERROR);
        }

        Judgement entity = judgementRepository.findByApplicationId(applicationId).orElseThrow(() -> {
            throw new BaseException(SYSTEM_ERROR);
        });

        return modelMapper.map(entity, Response.class);
    }

    @Override
    @Transactional(readOnly = false)
    public Response update(Long judgementId, Request request) {

        Judgement entity = judgementRepository.findById(judgementId).orElseThrow(() -> {
            throw new BaseException(SYSTEM_ERROR);
        });

        entity.setName(request.getName());
        entity.setApprovalAmount(request.getApprovalAmount());

        return modelMapper.map(entity, Response.class);
    }

    private boolean isPresentApplication(Long applicationId) {
        return applicationRespository.findById(applicationId).isPresent();
    }
}
