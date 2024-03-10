package com.devtomato.loan.controller;

import com.devtomato.loan.dto.ApplicationDTO;
import com.devtomato.loan.dto.ApplicationDTO.GrantAmount;
import com.devtomato.loan.dto.JudgementDTO.*;
import com.devtomato.loan.dto.ResponseDTO;
import com.devtomato.loan.service.JudgementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/judgements")
public class JudgementController extends AbstractController {

    private final JudgementService judgementService;

    @PostMapping
    public ResponseDTO<Response> create(@RequestBody Request request) {
        return ok(judgementService.create(request));
    }

    @GetMapping("/{judgmentId}")
    public ResponseDTO<Response> get(@PathVariable Long judgmentId) {
        return ok(judgementService.get(judgmentId));
    }

    @GetMapping("/applications/{applicationId}")
    public ResponseDTO<Response> getJudgementOfApplication(@PathVariable("applicationId") Long applicationId) {
        return ok(judgementService.getJudgementOfApplication(applicationId));
    }

    @PutMapping("/{judgmentId}")
    public ResponseDTO<Response> update(@PathVariable Long judgmentId, @RequestBody Request request) {
        return ok(judgementService.update(judgmentId, request));
    }

    @DeleteMapping("/{judgmentId}")
    public ResponseDTO<Void> delete(@PathVariable Long judgmentId) {
        judgementService.delete(judgmentId);
        return ok();
    }

    @PatchMapping("/{judgmentId}/grants")
    public ResponseDTO<GrantAmount> grant(@PathVariable Long judgmentId) {
        return ok(judgementService.grant(judgmentId));
    }
}
