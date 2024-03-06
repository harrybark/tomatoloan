package com.devtomato.loan.controller;

import com.devtomato.loan.dto.ResponseDTO;
import static com.devtomato.loan.dto.TermsDTO.*;
import com.devtomato.loan.dto.TermsDTO.Response;
import com.devtomato.loan.service.TermsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/terms")
public class TermsController extends AbstractController{

    private final TermsService termsService;

    @PostMapping
    public ResponseDTO<Response> create(@RequestBody Request request) {
        return ok(termsService.create(request));
    }

    @GetMapping
    public ResponseDTO<List<Response>> getAll() {
        return ok(termsService.getAll());
    }
}
