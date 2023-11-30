package com.fastcampus.reserve.restdocs.product;

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

import com.fastcampus.reserve.common.SecurityApiDocumentation;
import com.fastcampus.reserve.domain.product.Product;
import com.fastcampus.reserve.domain.product.ProductImage;
import com.fastcampus.reserve.domain.product.room.Room;
import com.fastcampus.reserve.domain.product.room.RoomImage;
import com.fastcampus.reserve.infrestructure.product.ProductImageRepository;
import com.fastcampus.reserve.infrestructure.product.ProductRepository;
import com.fastcampus.reserve.infrestructure.product.RoomImageRepository;
import com.fastcampus.reserve.infrestructure.product.room.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class ProductDocumentationTest2 extends SecurityApiDocumentation {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomImageRepository roomImageRepository;

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
    void getProduct() throws Exception {
        // given
        createProduct();

        // when, then
        this.mockMvc.perform(get("/v1/products/{id}", 1)
                .param("checkIn", "2023-01-01")
                .param("checkOut", "2023-01-03")
            )
            .andExpect(status().isOk())
            .andDo(document("get-product",
                pathParameters(
                    parameterWithName("id").description("The product ID")
                ),
                queryParameters(
                    parameterWithName("checkIn").description("Check-in date"),
                    parameterWithName("checkOut").description("Check-out date")
                ),
                responseFields(
                    fieldWithPath("status")
                        .description("Response status"),
                    fieldWithPath("data.id")
                        .description("Product ID"),
                    fieldWithPath("data.name")
                        .description("Name of the accommodation"),
                    fieldWithPath("data.zipCode")
                        .description("Zip code"),
                    fieldWithPath("data.address")
                        .description("Address"),
                    fieldWithPath("data.description")
                        .description("Description of the accommodation"),
                    fieldWithPath("data.longitude")
                        .description("Longitude"),
                    fieldWithPath("data.latitude")
                        .description("Latitude"),
                    fieldWithPath("data.imageUrls")
                        .description("Image URLs"),
                    fieldWithPath("data.rooms[].id")
                        .description("Room ID"),
                    fieldWithPath("data.rooms[].name")
                        .description("Room name"),
                    fieldWithPath("data.rooms[].basicGuestCount")
                        .description("Basic guest count"),
                    fieldWithPath("data.rooms[].maxGuestCount")
                        .description("Maximum guest count"),
                    fieldWithPath("data.rooms[].price")
                        .description("Price"),
                    fieldWithPath("data.rooms[].checkInTime")
                        .description("Check-in time"),
                    fieldWithPath("data.rooms[].checkOutTime")
                        .description("Check-out time"),
                    fieldWithPath("data.rooms[].imageUrls")
                        .description("Image URL for the room"),
                    fieldWithPath("data.rooms[].reserveDate")
                        .description("Reservation date"),
                    fieldWithPath("data.rooms[].stock")
                        .description("Stock available"),
                    fieldWithPath("data.rooms[].roomBathFacility")
                        .description("Room bath facility availability"),
                    fieldWithPath("data.rooms[].roomBath")
                        .description("Room bath availability"),
                    fieldWithPath("data.rooms[].roomHomeTheater")
                        .description("Room home theater availability"),
                    fieldWithPath("data.rooms[].roomAircondition")
                        .description("Room air conditioning availability"),
                    fieldWithPath("data.rooms[].roomTv")
                        .description("Room TV availability"),
                    fieldWithPath("data.rooms[].roomPc")
                        .description("Room PC availability"),
                    fieldWithPath("data.rooms[].roomCable")
                        .description("Room cable availability"),
                    fieldWithPath("data.rooms[].roomInternet")
                        .description("Room internet availability"),
                    fieldWithPath("data.rooms[].roomRefrigerator")
                        .description("Room refrigerator availability"),
                    fieldWithPath("data.rooms[].roomSofa")
                        .description("Room sofa availability"),
                    fieldWithPath("data.rooms[].roomCook")
                        .description("Room cooking facilities"),
                    fieldWithPath("data.rooms[].roomTable")
                        .description("Room table availability"),
                    fieldWithPath("data.rooms[].roomHairdryer")
                        .description("Room hairdryer availability")
                )
            ));
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
            .roomFacilities(
                "{\"roomoffseasonminfee2\":\"230000\",\"roomoffseasonminfee1\":\"200000\",\"roomhometheater\":\"\",\"roomcount\":\"0\",\"roomtv\":\"Y\",\"roomsofa\":\"\",\"roomimg1alt\":\"충남_스테이 앤드 스튜디오 여여재_객실 A2 1\",\"roommaxcount\":\"4\",\"cpyrhtDivCd5\":\"Type3\",\"roomcook\":\"Y\",\"roomimg2alt\":\"충남_스테이 앤드 스튜디오 여여재_객실 A2 2\",\"cpyrhtDivCd4\":\"Type3\",\"roomcable\":\"Y\",\"roomrefrigerator\":\"Y\",\"roomtable\":\"Y\",\"roomimg4alt\":\"충남_스테이 앤드 스튜디오 여여재_객실 A2 5\",\"cpyrhtDivCd1\":\"Type3\",\"roombathfacility\":\"Y\",\"roompc\":\"\",\"roomtoiletries\":\"Y\",\"cpyrhtDivCd3\":\"Type3\",\"cpyrhtDivCd2\":\"Type3\",\"roomimg4\":\"http://tong.visitkorea.or.kr/cms/resource/96/2803396_image2_1.jpg\",\"roomimg5\":\"http://tong.visitkorea.or.kr/cms/resource/99/2803399_image2_1.jpg\",\"roomintro\":\"복층 / 바다 조망 / 넓은 발코니\\n\",\"roominternet\":\"Y\",\"roomimg1\":\"http://tong.visitkorea.or.kr/cms/resource/91/2803391_image2_1.jpg\",\"roomtitle\":\"A/2\",\"roomimg2\":\"http://tong.visitkorea.or.kr/cms/resource/92/2803392_image2_1.jpg\",\"roomimg3\":\"http://tong.visitkorea.or.kr/cms/resource/93/2803393_image2_1.jpg\",\"roomsize1\":\"16\",\"roomhairdryer\":\"Y\",\"roombasecount\":\"4\",\"roomsize2\":\"56\",\"contentid\":\"2804347\",\"roomaircondition\":\"Y\",\"contenttypeid\":\"32\",\"roomimg3alt\":\"충남_스테이 앤드 스튜디오 여여재_객실 A2 3\",\"roompeakseasonminfee1\":\"250000\",\"roompeakseasonminfee2\":\"280000\",\"roomimg5alt\":\"충남_스테이 앤드 스튜디오 여여재_객실 A2 7\",\"roomcode\":\"55640\",\"roombath\":\"\"}")
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

