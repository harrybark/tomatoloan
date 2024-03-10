package com.devtomato.loan.service;

import com.devtomato.loan.domain.Application;
import com.devtomato.loan.domain.Judgement;
import com.devtomato.loan.dto.JudgementDTO.*;
import com.devtomato.loan.repository.ApplicationRepository;
import com.devtomato.loan.repository.JudgementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JudgementServiceTest {

    @InjectMocks
    private JudgementServiceImpl judgmentService;

    @Mock
    private JudgementRepository judgmentRepository;

    @Mock
    private ApplicationRepository applicationRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void Should_ReturnResponseOfNewJudgmentEntity_When_RequestNewJudgment() {
        Judgement judgmentEntity = Judgement.builder()
                .name("Member Kim")
                .applicationId(1L)
                .approvalAmount(BigDecimal.valueOf(50000000))
                .build();

        Application applicationEntity = Application.builder()
                .applicationId(1L)
                .build();

        Request request = Request.builder()
                .name("Member Kim")
                .applicationId(1L)
                .approvalAmount(BigDecimal.valueOf(50000000))
                .build();

        when(applicationRepository.findById(1L)).thenReturn(Optional.ofNullable(applicationEntity));
        when(judgmentRepository.save(ArgumentMatchers.any(Judgement.class))).thenReturn(judgmentEntity);

        Response actual = judgmentService.create(request);

        assertThat(actual.getJudgmentId()).isSameAs(judgmentEntity.getJudgmentId());
        assertThat(actual.getName()).isSameAs(judgmentEntity.getName());
        assertThat(actual.getApplicationId()).isSameAs(judgmentEntity.getApplicationId());
        assertThat(actual.getApprovalAmount()).isSameAs(judgmentEntity.getApprovalAmount());
    }

}