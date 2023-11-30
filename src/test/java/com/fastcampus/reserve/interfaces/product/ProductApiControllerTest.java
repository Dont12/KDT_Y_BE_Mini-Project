package com.fastcampus.reserve.interfaces.product;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastcampus.reserve.application.ProductFacade;
import com.fastcampus.reserve.domain.product.Product;
import com.fastcampus.reserve.domain.product.ProductImage;
import com.fastcampus.reserve.domain.product.dto.response.ProductSummaryDto;
import com.fastcampus.reserve.domain.product.room.Room;
import com.fastcampus.reserve.domain.product.room.RoomImage;
import com.fastcampus.reserve.infrestructure.product.ProductImageRepository;
import com.fastcampus.reserve.infrestructure.product.ProductRepository;
import com.fastcampus.reserve.infrestructure.product.RoomImageRepository;
import com.fastcampus.reserve.infrestructure.product.room.RoomRepository;
import java.time.LocalDate;
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

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomImageRepository roomImageRepository;

    @MockBean
    ProductFacade productFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("전체 조회API 테스트")
    void testGetProducts() throws Exception {
        when(productFacade.getProducts(any()))
                .thenReturn(
                        List.of(new ProductSummaryDto(1L, "name", 0, "imageUrl")));
        mockMvc.perform(get("/v1/products"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void getProduct() throws Exception {
        // given
        createProduct();

        // when, then
        mockMvc.perform(get("/v1/products/1"))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    private void createProduct() {
        Product product = Product.builder()
            .id(1L)
            .name("product name")
            .description("description")
            .category("category")
            .area("area")
            .address("address")
            .latitude("latitude")
            .longitude("longitude")
            .zipCode("zipcode")
            .build();
        productRepository.save(product);

        ProductImage productImage = ProductImage.builder()
            .url("url")
            .product(product)
            .build();
        productImageRepository.save(productImage);

        product.addImage(productImage);
        productRepository.save(product);

        Room room = Room.builder()
            .id(1L)
            .roomFacilities("{}")
            .baseGuestCount(4)
            .maxGuestCount(6)
            .stock(3)
            .checkInTime("checkInTime")
            .checkOutTime("checkOutTime")
            .price(10000)
            .product(product)
            .name("room name")
            .build();
        roomRepository.save(room);

        RoomImage roomImage = RoomImage.builder().url("url").room(room).build();
        roomImageRepository.save(roomImage);

        room.addImage(roomImage);
        roomRepository.save(room);
    }
}
