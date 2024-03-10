package com.devtomato.loan.service;

import com.devtomato.loan.dto.JudgementDTO.*;

public interface JudgementService {

    Response create(Request request);

    Response get(Long judgementId);

    Response getJudgementOfApplication(Long applicationId);
}
