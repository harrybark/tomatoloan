package com.devtomato.loan.service;

import com.devtomato.loan.domain.Counsel;
import com.devtomato.loan.dto.CounselDTO;
import com.devtomato.loan.dto.CounselDTO.Request;
import com.devtomato.loan.dto.CounselDTO.Response;
import com.devtomato.loan.repository.CounselRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

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
}