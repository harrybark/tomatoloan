package com.devtomato.loan.service;

import com.devtomato.loan.domain.Counsel;
import com.devtomato.loan.dto.CounselDTO.Request;
import com.devtomato.loan.dto.CounselDTO.Response;
import com.devtomato.loan.exception.BaseException;
import com.devtomato.loan.exception.ResultType;
import com.devtomato.loan.repository.CounselRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CounselServiceImpl implements CounselService
{

    private final ModelMapper modelMapper;
    private final CounselRepository counselRepository;

    @Override
    @Transactional(readOnly = false)
    public Response create(Request request) {

        Counsel counsel = modelMapper.map(request, Counsel.class);
        counsel.setAppliedAt(LocalDateTime.now());
        Counsel saved = counselRepository.save(counsel);

        return modelMapper.map(saved, Response.class);
    }

    @Override
    public Response get(Long counselId) {
        Counsel counsel = counselRepository.findById(counselId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });
        return modelMapper.map(counsel, Response.class);
    }

    @Override
    @Transactional(readOnly = false)
    public Response update(Long counselId, Request request) {

        Counsel counsel = counselRepository.findById(counselId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        counsel.setName(request.getName());
        counsel.setCellPhone(request.getCellPhone());
        counsel.setEmail(request.getEmail());
        counsel.setMemo(request.getMemo());
        counsel.setAddress(request.getAddress());
        counsel.setAddressDetail(request.getAddressDetail());
        counsel.setZipCode(request.getZipCode());

        //Transactional Annotation 으로 Dirty Checking
        //counselRepository.save(counsel);

        return modelMapper.map(counsel, Response.class);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long counselId) {
        Counsel counsel = counselRepository.findById(counselId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        counsel.setIsDeleted(true);
    }
}
