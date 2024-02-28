package com.devtomato.loan.service;

import com.devtomato.loan.domain.Counsel;
import com.devtomato.loan.dto.CounselDTO.Request;
import com.devtomato.loan.dto.CounselDTO.Response;
import com.devtomato.loan.repository.CounselRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CounselServiceImpl implements CounselService
{

    private final ModelMapper modelMapper;
    private final CounselRepository counselRepository;

    @Override
    public Response create(Request request) {

        Counsel counsel = modelMapper.map(request, Counsel.class);
        counsel.setAppliedAt(LocalDateTime.now());
        Counsel saved = counselRepository.save(counsel);

        return modelMapper.map(saved, Response.class);
    }
}
