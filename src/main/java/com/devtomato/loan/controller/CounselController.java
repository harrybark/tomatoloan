package com.devtomato.loan.controller;

import com.devtomato.loan.dto.CounselDTO;
import com.devtomato.loan.dto.CounselDTO.Request;
import com.devtomato.loan.dto.CounselDTO.Response;
import com.devtomato.loan.dto.ResponseDTO;
import com.devtomato.loan.service.CounselService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/counsels")
public class CounselController extends AbstractController {

    private final CounselService counselService;

    @PostMapping
    public ResponseDTO<Response> create(@RequestBody Request request) {
        return ok(counselService.create(request));
    }

    @GetMapping("/{counselId}")
    public ResponseDTO<Response> get(@PathVariable("counselId") Long counselId) {
        return ok(counselService.get(counselId));
    }

    @PutMapping("/{counselId}")
    public ResponseDTO<Response> update(@PathVariable("counselId") Long counselId, @RequestBody Request request) {
        return ok(counselService.update(counselId, request));
    }
}