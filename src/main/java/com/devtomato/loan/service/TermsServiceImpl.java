package com.devtomato.loan.service;

import com.devtomato.loan.domain.Terms;
import com.devtomato.loan.dto.TermsDTO.Request;
import com.devtomato.loan.dto.TermsDTO.Response;
import com.devtomato.loan.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TermsServiceImpl implements TermsService {

    private final ModelMapper modelMapper;
    private final TermsRepository termsRepository;

    @Override
    public Response create(Request request) {
        Terms entity = modelMapper.map(request, Terms.class);
        Terms saved = termsRepository.save(entity);
        return modelMapper.map(saved, Response.class);
    }

    @Override
    public List<Response> getAll() {
        List<Terms> termsList = termsRepository.findAll();

        return termsList.stream().map(t -> modelMapper.map(t, Response.class)).collect(Collectors.toList());
    }
}
