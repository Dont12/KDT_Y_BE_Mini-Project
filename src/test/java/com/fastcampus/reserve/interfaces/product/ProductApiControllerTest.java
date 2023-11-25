package com.fastcampus.reserve.interfaces.product;

import com.fastcampus.reserve.application.ProductFacade;
import com.fastcampus.reserve.common.security.config.SecurityConfig;
import com.fastcampus.reserve.interfaces.product.dto.response.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(controllers = ProductApiController.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductApiControllerTest {
    @Autowired
    MockMvc mockMvc;


    @MockBean
    ProductFacade productFacade;
    @Mock
    Logger log;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProducts() throws Exception {
        when(productFacade.getProducts()).thenReturn(List.of(new ProductResponse(Long.valueOf(1), "name", Integer.valueOf(0), "imageUrl")));

        mockMvc.perform(get("/products")).andExpect(status().isBadRequest()).andDo(print());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: https://weirddev.com/forum#!/testme