package com.devtomato.loan.service;

import com.devtomato.loan.dto.ApplicationDTO;
import com.devtomato.loan.dto.ApplicationDTO.GrantAmount;
import com.devtomato.loan.dto.JudgementDTO.*;

public interface JudgementService {

    Response create(Request request);

    Response get(Long judgementId);

    Response getJudgementOfApplication(Long applicationId);

    Response update(Long judgementId, Request request);

    void delete(Long judgementId);

    GrantAmount grant(Long judgementId);
}
