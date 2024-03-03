package com.devtomato.loan.service;

import com.devtomato.loan.domain.Counsel;
import com.devtomato.loan.dto.CounselDTO;
import com.devtomato.loan.dto.CounselDTO.Request;
import com.devtomato.loan.dto.CounselDTO.Response;
import com.devtomato.loan.exception.BaseException;
import com.devtomato.loan.exception.ResultType;
import com.devtomato.loan.repository.CounselRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class )
class CounselServiceTest {

    @InjectMocks
    CounselServiceImpl counselService;

    @Mock
    private CounselRepository counselRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    public void Should_ReturnResponseOfNewCounselEntity_When_RequestCounsel() throws Exception {
        // given
        Counsel counsel = Counsel.builder()
                .name("박민우")
                .cellPhone("01028613278")
                .email("devharrypmw@gmail.com")
                .address(null)
                .addressDetail(null)
                .zipCode(null)
                .memo("상담 신청합니다.")
                .build();

        Request request = Request.builder()
                .name(counsel.getName())
                .cellPhone(counsel.getCellPhone())
                .email(counsel.getEmail())
                .address(counsel.getAddress())
                .addressDetail(counsel.getAddressDetail())
                .zipCode(counsel.getZipCode())
                .memo(counsel.getMemo())
                .build();

        // when
        when(counselRepository.save(any(Counsel.class))).thenReturn(counsel);

        Response actual = counselService.create(request);

        // then
        assertThat(actual.getName()).isEqualTo(counsel.getName());
    }
    
    @Test
    public void Should_ReturnResponseOfExistCounselEntity_When_RequestExistCounselId() throws Exception {
        // given
        Long findId = 1L;

        // when
        Counsel entity = Counsel.builder()
                .counselId(1L)
                .build();
        when(counselRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

        Response actual = counselService.get(findId);

        // then
        assertThat(actual.getCounselId()).isEqualTo(entity.getCounselId());
    }

    @Test
    public void Should_ThrowException_When_RequestNotExistCounselId() throws Exception {
        // given
        Long findId = 2L;

        // when
        when(counselRepository.findById(findId)).thenThrow(new BaseException(ResultType.SYSTEM_ERROR));

        // then
        Assertions.assertThrows(BaseException.class, () -> counselService.get(findId));
    }

    @Test
    void Should_ReturnUpdatedResponseOfExistCounselEntity_When_RequestUpdateExistCounselInfo() {
        Long findId = 1L;

        Counsel entity = Counsel.builder()
                .counselId(1L)
                .name("Harry Park")
                .build();

        Request request = Request.builder()
                .name("DevTomato")
                .build();

        //Dirty Checking 때문에 주석함
        //when(counselRepository.save(ArgumentMatchers.any(Counsel.class))).thenReturn(entity);
        when(counselRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

        Response actual = counselService.update(findId, request);

        assertThat(actual.getCounselId()).isSameAs(findId);
        assertThat(actual.getName()).isSameAs(request.getName());
    }

    @Test
    void Should_DeletedCounselEntity_When_RequestDeleteExistCounselInfo() {
        Long targetId = 1L;

        Counsel entity = Counsel.builder()
                .counselId(1L)
                .build();

        //Dirty Checking 때문에 주석함
        //when(counselRepository.save(ArgumentMatchers.any(Counsel.class))).thenReturn(entity);
        when(counselRepository.findById(targetId)).thenReturn(Optional.ofNullable(entity));

        counselService.delete(targetId);

        assertThat(entity.getIsDeleted()).isSameAs(true);
    }
}