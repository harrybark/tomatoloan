package com.devtomato.loan.service;

import com.devtomato.loan.dto.TermsDTO;
import com.devtomato.loan.dto.TermsDTO.Request;
import com.devtomato.loan.dto.TermsDTO.Response;

import java.util.List;

public interface TermsService {
    Response create(Request request);

    List<Response> getAll();
}
