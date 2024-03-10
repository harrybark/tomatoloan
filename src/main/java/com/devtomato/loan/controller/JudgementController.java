package com.devtomato.loan.controller;

import com.devtomato.loan.dto.JudgementDTO.*;
import com.devtomato.loan.dto.ResponseDTO;
import com.devtomato.loan.service.JudgementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/judgements")
public class JudgementController extends AbstractController {

    private final JudgementService judgementService;

    @PostMapping
    public ResponseDTO<Response> create(@RequestBody Request request) {
        return ok(judgementService.create(request));
    }
}
