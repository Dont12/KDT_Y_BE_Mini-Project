package com.fastcampus.reserve.interfaces.product;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastcampus.reserve.application.ProductFacade;
import com.fastcampus.reserve.domain.product.dto.response.ProductDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
class ProductApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductFacade productFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @WithMockUser
    @DisplayName("전체 조회API 테스트")
    void testGetProducts() throws Exception {
        when(productFacade.getProducts(any()))
                .thenReturn(
                        List.of(new ProductDto(1L, "name", 0, "imageUrl")));
        mockMvc.perform(get("/products"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
