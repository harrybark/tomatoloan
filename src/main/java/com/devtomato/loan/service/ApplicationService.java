package com.devtomato.loan.service;

import com.devtomato.loan.dto.ApplicationDTO;
import com.devtomato.loan.dto.ApplicationDTO.Request;
import com.devtomato.loan.dto.ApplicationDTO.Response;

public interface ApplicationService {

    Response create(Request request);

    Response get(Long applicationId);

    Response update(Long applicationId, Request request);
}
