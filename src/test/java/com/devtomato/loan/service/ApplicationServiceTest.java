package com.devtomato.loan.service;

import com.devtomato.loan.domain.Application;
import com.devtomato.loan.domain.Counsel;
import com.devtomato.loan.dto.ApplicationDTO;
import com.devtomato.loan.dto.ApplicationDTO.Request;
import com.devtomato.loan.dto.ApplicationDTO.Response;
import com.devtomato.loan.exception.BaseException;
import com.devtomato.loan.exception.ResultType;
import com.devtomato.loan.repository.ApplicationRespository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class )
class ApplicationServiceTest {

    @InjectMocks
    ApplicationServiceImpl applicationService;

    @Mock
    private ApplicationRespository applicationRespository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    public void Should_ReturnResponseOfNewApplicationEntity_When_RequestCreateApplication() throws Exception {
        // given
        Application entity = Application.builder()
                .name("Harry")
                .cellPhone("01012341234")
                .email("test@test.com")
                .hopeAmount(BigDecimal.valueOf(500000000))
                .build();

        Request request = Request.builder()
                .name("Harry")
                .cellPhone("01012341234")
                .email("test@test.com")
                .hopeAmount(BigDecimal.valueOf(500000000))
                .build();

        // when
        when(applicationRespository.save(any(Application.class))).thenReturn(entity);
        Response actual = applicationService.create(request);

        // then
        assertThat(actual.getName()).isEqualTo(entity.getName());
    }

    @Test
    public void Should_ReturnResponseOfExistApplicationEntity_When_RequestExistApplicationId() throws Exception {
        // given
        Long findId = 1L;

        Application application = Application.builder()
                .applicationId(1L)
                .build();
        // when
        when(applicationRespository.findById(findId)).thenThrow(new BaseException(ResultType.SYSTEM_ERROR));

        // then
        Assertions.assertThrows(BaseException.class, () -> applicationService.get(findId));

    }
}