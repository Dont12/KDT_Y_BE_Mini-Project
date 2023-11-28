package com.fastcampus.reserve.infrestructure.product;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.never;
import static org.mockito.BDDMockito.verify;

import com.fastcampus.reserve.domain.dto.request.ProductListOptionDto;
import com.fastcampus.reserve.domain.product.Product;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


class ProductReaderImplTest {
    @Mock
    ProductRepository productRepository;
    @InjectMocks
    ProductReaderImpl productReaderImpl;

    int page = 1;
    int pageSize = 10;
    Pageable pageable = PageRequest.of(page, pageSize);

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
        verify(productRepository, never()).findAllByArea(anyString(), eq(pageable));
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
                                                            page,
                                                            pageSize);
        List<Product> expectedProducts = Collections.singletonList(Product.builder().id(2531417L)
                .name("전주 한옥숙박체험관[한국관광 품질인증/Korea Quality]")
                .category("한옥")
                .description("전주 한옥숙박체험관은 전주한옥마을")
                .zipCode("55042")
                .address("전라북도 전주시 완산구 은행로 56-1")
                .longitude("127.1521863940")
                .latitude("35.8150142818")
                .area("전라북도")
                .sigungu("전주시")
                .build());

        given(productRepository.findAllByArea(areaCode, pageable))
                .willReturn(expectedProducts);

        // When
        List<Product> actualProducts = productReaderImpl.getAllProduct(dto);

        // Then
        assertThat(actualProducts).isEqualTo(expectedProducts);
        verify(productRepository).findAllByArea(areaCode, pageable);
        verify(productRepository, never()).findAll();
    }

    @Test
    @DisplayName("카테고리가 있으면 해당 카테고리에 맞는 상품만 나온다")
    public void whenCategoryProvided_shouldUseCategoryFilter() {
        // Given
        String category = "호텔";

        int page = 1;
        int pageSize = 10;
        ProductListOptionDto dto = new ProductListOptionDto(LocalDate.now(),
                LocalDate.now().plusDays(2),
                category,
                null,
                page,
                pageSize);
        List<Product> expectedProducts = Collections.singletonList(Product.builder().id(2531417L)
                .name("테스트 호텔")
                .category("호텔")
                .build());

        given(productRepository.findAllByCategory(category, pageable))
                .willReturn(expectedProducts);

        // When
        List<Product> actualProducts = productReaderImpl.getAllProduct(dto);

        // Then
        assertThat(actualProducts).isEqualTo(expectedProducts);
        verify(productRepository).findAllByCategory(category, pageable);
        verify(productRepository, never()).findAll();
        verify(productRepository, never()).findAllByArea(anyString(), eq(pageable));
    }


    @Test
    @DisplayName("AreaCode와 Category가 모두 있으면 해당 AreaCode와 Category에 맞는 상품만 나온다")
    public void whenAreaCodeAndCategoryProvided_shouldUseBothFilters() {
        // Given
        String areaCode = "충청남도";
        String category = "호텔";
        int page = 1;
        int pageSize = 10;
        ProductListOptionDto dto = new ProductListOptionDto(LocalDate.now(),
                LocalDate.now().plusDays(2),
                category,
                areaCode,
                page,
                pageSize);
        List<Product> expectedProducts = Collections.singletonList(Product.builder().id(2531417L)
                .name("테스트 호텔")
                .category(category)
                .area(areaCode)
                .build());

        given(productRepository.findAllByAreaAndCategory(areaCode, category, pageable))
                .willReturn(expectedProducts);

        // When
        List<Product> actualProducts = productReaderImpl.getAllProduct(dto);

        // Then
        assertThat(actualProducts).isEqualTo(expectedProducts);
        verify(productRepository).findAllByAreaAndCategory(areaCode, category, pageable);
        verify(productRepository, never()).findAllByArea(anyString(), eq(pageable));
        verify(productRepository, never()).findAllByCategory(anyString(), eq(pageable));
        verify(productRepository, never()).findAll();
    }
}
