package com.fastcampus.reserve.restdocs.product;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastcampus.reserve.application.ProductFacade;
import com.fastcampus.reserve.common.SecurityApiDocumentation;
import com.fastcampus.reserve.domain.product.Product;
import com.fastcampus.reserve.domain.product.ProductImage;
import com.fastcampus.reserve.domain.product.dto.request.ProductListOptionDto;
import com.fastcampus.reserve.domain.product.dto.response.ProductSummaryDto;
import com.fastcampus.reserve.domain.product.room.Room;
import com.fastcampus.reserve.domain.product.room.RoomImage;
import com.fastcampus.reserve.infrestructure.product.ProductImageRepository;
import com.fastcampus.reserve.infrestructure.product.ProductRepository;
import com.fastcampus.reserve.infrestructure.product.RoomImageRepository;
import com.fastcampus.reserve.infrestructure.product.room.RoomRepository;
import com.fastcampus.reserve.interfaces.product.ProductDtoMapper;
import com.fastcampus.reserve.interfaces.product.dto.response.ProductResponse;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class ProductDocumentationTest extends SecurityApiDocumentation {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomImageRepository roomImageRepository;

    @MockBean
    private ProductFacade productFacade;

    @MockBean
    private ProductDtoMapper mapper;


    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocument) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocument)
                .operationPreprocessors()
                .withRequestDefaults(prettyPrint())
                .withResponseDefaults(prettyPrint()))
            .build();


        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void getProductsExample() throws Exception {
        List<ProductSummaryDto> products = List.of(
                new ProductSummaryDto(2804347L, "스테이 앤드 스튜디오 여여재[한국관광 품질인증/Korea Quality]", 170000, "http://tong.visitkorea.or.kr/cms/resource/41/2803441_image2_1.jpg"),
                new ProductSummaryDto(2706613L, "송계헌 [한국관광 품질인증/Korea Quality]", 450000, "http://tong.visitkorea.or.kr/cms/resource/22/2629122_image2_1.jpg"),
                new ProductSummaryDto(1972030L, "상유재 [한국관광 품질인증/Korea Quality]", 70000, "http://tong.visitkorea.or.kr/cms/resource/22/2629122_image2_1.jpg"),
                new ProductSummaryDto(1836470L, "베이브리즈가족호텔", 120000, "http://tong.visitkorea.or.kr/cms/resource/06/1836506_image2_1.JPG"),
                new ProductSummaryDto(2531417L, "전주 한옥숙박체험관[한국관광 품질인증/Korea Quality]", 60000, "http://tong.visitkorea.or.kr/cms/resource/90/2531490_image2_1.jpg")
        );

        List<ProductResponse> productResponses = List.of(
                ProductResponse.builder()
                        .id(2804347L)
                        .name("스테이 앤드 스튜디오 여여재[한국관광 품질인증/Korea Quality]")
                        .minPrice(170000)
                        .imageUrl("http://tong.visitkorea.or.kr/cms/resource/41/2803441_image2_1.jpg")
                        .build(),
                ProductResponse.builder()
                        .id(2706613L)
                        .name("송계헌 [한국관광 품질인증/Korea Quality]")
                        .minPrice(450000)
                        .imageUrl("http://tong.visitkorea.or.kr/cms/resource/22/2629122_image2_1.jpg")
                        .build(),
                ProductResponse.builder()
                        .id(1972030L)
                        .name("상유재 [한국관광 품질인증/Korea Quality]")
                        .minPrice(70000)
                        .imageUrl("http://tong.visitkorea.or.kr/cms/resource/30/1972030_image2_1.jpg")
                        .build(),
                ProductResponse.builder()
                        .id(1836470L)
                        .name("베이브리즈가족호텔")
                        .minPrice(120000)
                        .imageUrl("http://tong.visitkorea.or.kr/cms/resource/06/1836506_image2_1.JPG")
                        .build(),
                ProductResponse.builder()
                        .id(2531417L)
                        .name("전주 한옥숙박체험관[한국관광 품질인증/Korea Quality]")
                        .minPrice(60000)
                        .imageUrl("http://tong.visitkorea.or.kr/cms/resource/90/2531490_image2_1.jpg")
                        .build()
        );


        given(productFacade.getProducts(any(ProductListOptionDto.class)))
                .willReturn(products);

        given(mapper.of(anyList()))
                .willReturn(productResponses);

        this.mockMvc.perform(get("/v1/products")
                        .param("checkIn", "2023-01-01")
                        .param("checkOut", "2023-01-03")
                        .param("category", "호텔")
                        .param("areaCode", "충청남도")
                        .param("page", "1")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andDo(document("get-products",
                    getDocumentRequest(),
                    getDocumentResponse(),
                        pathParameters(
                                parameterWithName("category").description("카테고리").optional(),
                                parameterWithName("areaCode").description("지역 코드").optional(),
                                parameterWithName("page").description("페이지 번호").optional(),
                                parameterWithName("pageSize").description("페이지 크기").optional()
                        ),
                        responseFields(
                                fieldWithPath("status").ignored(),
                                fieldWithPath("data[].id").description("상품 ID"),
                                fieldWithPath("data[].name").description("상품 이름"),
                                fieldWithPath("data[].imageUrl").description("상품 이미지 URL"),
                                fieldWithPath("data[].minPrice").description("최소 가격")
                        )
                ));
    }
}

