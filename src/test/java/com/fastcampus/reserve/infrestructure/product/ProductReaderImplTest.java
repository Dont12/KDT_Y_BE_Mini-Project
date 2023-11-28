package com.fastcampus.reserve.infrestructure.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.never;
import static org.mockito.BDDMockito.verify;

import com.fastcampus.reserve.domain.dto.request.ProductListOptionDto;
import com.fastcampus.reserve.domain.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


class ProductReaderImplTest {
    @Mock
    ProductRepository productRepository;
    @InjectMocks
    ProductReaderImpl productReaderImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("전체 조회 테스트")
    public void whenAreaCodeIsEmpty_shouldCallFindAll() {
        // Given
        ProductListOptionDto dto = new ProductListOptionDto(LocalDate.now(),
                                                            LocalDate.now().plusDays(2),
                                                    null,
                                                    "",
                                                        1,
                                                    10);
        List<Product> expectedProducts = Collections.singletonList(Product.builder().id(2531417L)
                .build());

        given(productRepository.findAll()).willReturn(expectedProducts);

        // When
        List<Product> actualProducts = productReaderImpl.getAllProduct(dto);

        // Then
        assertThat(actualProducts).isEqualTo(expectedProducts);
        verify(productRepository).findAll();
        verify(productRepository, never()).findAllByArea(anyString());
    }


    @Test
    @DisplayName("AreaCode가 있으면 해당 AreaCode에 걸린것들만 나온다")
    public void whenAreaCodeProvided_shouldUseAreaCodeFilter() {
        // Given
        String areaCode = "충청남도";
        ProductListOptionDto dto = new ProductListOptionDto(LocalDate.now(),
                                                            LocalDate.now().plusDays(2),
                                                    null,
                                                            areaCode,
                                                       1,
                                                    10);
        List<Product> expectedProducts = Collections.singletonList(Product.builder().id(2531417L)
                .name("전주 한옥숙박체험관[한국관광 품질인증/Korea Quality]")
                .category("한옥")
                .description("전주 한옥숙박체험관은 전주한옥마을 중심부인 은행로에 있어 한옥마을을 여행하기에 수월하다. " +
                        "골목을 따라 안쪽으로 들어가야 만날 수 있기 때문에 은행로의 번잡함은 걱정하지 않아도 된다. " +
                        "전 객실은 온돌로 구성되어 있고 복층공간이 있어 아이들에게 인기가 좋다. 또한 정수기를 설치하여 편의성을 높였다. " +
                        "조식으로 토스트와 아메리카노, 과일과 차를 제공하고, " +
                        "야외 마당에는 테이블이 있어 휴식을 취하기 좋다.")
                .zipCode("55042")
                .address("전라북도 전주시 완산구 은행로 56-1")
                .longitude("127.1521863940")
                .latitude("35.8150142818")
                .area("전라북도")
                .sigungu("전주시")
                .build());

        given(productRepository.findAllByArea(areaCode)).willReturn(expectedProducts);

        // When
        List<Product> actualProducts = productReaderImpl.getAllProduct(dto);

        // Then
        assertThat(actualProducts).isEqualTo(expectedProducts);
        verify(productRepository).findAllByArea(areaCode);
        verify(productRepository, never()).findAll();
    }
}
