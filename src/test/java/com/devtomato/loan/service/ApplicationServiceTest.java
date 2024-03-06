package com.devtomato.loan.service;

import com.devtomato.loan.domain.AcceptTerms;
import com.devtomato.loan.domain.Application;
import com.devtomato.loan.domain.Terms;
import com.devtomato.loan.dto.ApplicationDTO;
import com.devtomato.loan.dto.ApplicationDTO.AcceptTermsDTO;
import com.devtomato.loan.dto.ApplicationDTO.Request;
import com.devtomato.loan.dto.ApplicationDTO.Response;
import com.devtomato.loan.exception.BaseException;
import com.devtomato.loan.exception.ResultType;
import com.devtomato.loan.repository.AcceptTermsRepository;
import com.devtomato.loan.repository.ApplicationRespository;
import com.devtomato.loan.repository.TermsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.devtomato.loan.dto.ApplicationDTO.AcceptTermsDTO.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class )
class ApplicationServiceTest {

    @InjectMocks
    ApplicationServiceImpl applicationService;

    @Mock
    private ApplicationRespository applicationRepository;

    @Mock
    private TermsRepository termsRepository;

    @Mock
    private AcceptTermsRepository acceptTermsRepository;
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
        when(applicationRepository.save(any(Application.class))).thenReturn(entity);
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
        when(applicationRepository.findById(findId)).thenThrow(new BaseException(ResultType.SYSTEM_ERROR));

        // then
        Assertions.assertThrows(BaseException.class, () -> applicationService.get(findId));

    }

    @Test
    void Should_ReturnUpdatedResponseOfExistApplicationEntity_When_RequestUpdateExistApplicationInfo() {
        Long findId = 1L;

        Application entity = Application.builder()
                .applicationId(1L)
                .name("Harry Park")
                .build();

        Request request = Request.builder()
                .name("DevTomato")
                .build();

        //Dirty Checking 때문에 주석함
        when(applicationRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

        Response actual = applicationService.update(findId, request);

        assertThat(actual.getApplicationId()).isSameAs(findId);
        assertThat(actual.getName()).isSameAs(request.getName());
    }

    @Test
    void Should_DeleteApplicationEntity_When_RequestDeleteExistApplicationInfo() {
        Long targetId = 1L;

        Application entity = Application.builder()
                .applicationId(1L)
                .build();

        //Dirty Checking 때문에 주석함
        //when(counselRepository.save(ArgumentMatchers.any(Counsel.class))).thenReturn(entity);
        when(applicationRepository.findById(targetId)).thenReturn(Optional.ofNullable(entity));

        applicationService.delete(targetId);

        assertThat(entity.getIsDeleted()).isSameAs(true);
    }

    @Test
    void Should_AddAcceptTerms_When_RequestAcceptTermsOfApplication() {
        Terms entityA = Terms.builder()
                .termsId(1L)
                .name("대출 이용 약관 1")
                .termsDetailUrl("https://abc-storage.acc/dslfjdlsfjlsdddads")
                .build();

        Terms entityB = Terms.builder()
                .termsId(2L)
                .name("대출 이용 약관 2")
                .termsDetailUrl("https://abc-storage.acc/dslfjdlsfjlsdweqwq")
                .build();

        List<Long> acceptTerms = Arrays.asList(1L, 2L);

        AcceptTermsDTO request = builder()
                .acceptTermsIds(acceptTerms)
                .build();

        Long findId = 1L;

        when(applicationRepository.findById(findId)).thenReturn(
                Optional.ofNullable(Application.builder().build()));
        when(termsRepository.findAll(Sort.by(Direction.ASC, "termsId"))).thenReturn(Arrays.asList(entityA, entityB));
        when(acceptTermsRepository.save(any(AcceptTerms.class))).thenReturn(AcceptTerms.builder().build());


        Boolean actual = applicationService.acceptTerms(findId, request);
        assertThat(actual).isTrue();
    }

    @Test
    void Should_ThrowException_When_RequestNotAllAcceptTermsOfApplication() {
        Terms entityA = Terms.builder()
                .termsId(1L)
                .name("대출 이용 약관 1")
                .termsDetailUrl("https://abc-storage.acc/dslfjdlsfjlsdddads")
                .build();

        Terms entityB = Terms.builder()
                .termsId(2L)
                .name("대출 이용 약관 2")
                .termsDetailUrl("https://abc-storage.acc/dslfjdlsfjlsdweqwq")
                .build();

        List<Long> acceptTerms = Arrays.asList(1L);

        AcceptTermsDTO request = builder()
                .acceptTermsIds(acceptTerms)
                .build();

        Long findId = 1L;

        when(applicationRepository.findById(findId)).thenReturn(
                Optional.ofNullable(Application.builder().build()));
        when(termsRepository.findAll(Sort.by(Direction.ASC, "termsId"))).thenReturn(Arrays.asList(entityA, entityB));

        Assertions.assertThrows(BaseException.class, () -> applicationService.acceptTerms(1L, request));
    }

    @Test
    void Should_ThrowException_When_RequestNotExistAcceptTermsOfApplication() {
        Terms entityA = Terms.builder()
                .termsId(1L)
                .name("대출 이용 약관 1")
                .termsDetailUrl("https://abc-storage.acc/dslfjdlsfjlsdddads")
                .build();

        Terms entityB = Terms.builder()
                .termsId(2L)
                .name("대출 이용 약관 2")
                .termsDetailUrl("https://abc-storage.acc/dslfjdlsfjlsdweqwq")
                .build();

        List<Long> acceptTerms = Arrays.asList(1L, 3L);

        AcceptTermsDTO request = builder()
                .acceptTermsIds(acceptTerms)
                .build();

        Long findId = 1L;

        when(applicationRepository.findById(findId)).thenReturn(
                Optional.ofNullable(Application.builder().build()));
        when(termsRepository.findAll(Sort.by(Direction.ASC, "termsId"))).thenReturn(Arrays.asList(entityA, entityB));

        Assertions.assertThrows(BaseException.class, () -> applicationService.acceptTerms(1L, request));
    }
}