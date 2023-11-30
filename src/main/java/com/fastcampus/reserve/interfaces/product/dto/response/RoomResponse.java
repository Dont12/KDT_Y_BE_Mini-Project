package com.fastcampus.reserve.interfaces.product.dto.response;

import com.fastcampus.reserve.domain.product.room.Room;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;

public class RoomResponse {

    private final Long id;

    private final String name;

    private final Integer price;

    private final Integer stock;

    private final String imageUrl;

    private final String roomBathFacility;

    private final String roomBath;
    private final String roomHomeTheater;
    private final String roomAircondition;
    private final String roomTv;
    private final String roomPc;
    private final String roomCable;
    private final String roomInternet;
    private final String roomRefrigerator;
    private final String roomToiletries;
    private final String roomSofa;
    private final String roomCook;
    private final String roomTable;
    private final String roomHairdryer;

    @Builder
    public RoomResponse(Long id,
                        String name,
                        Integer price,
                        Integer stock,
                        String imageUrl,
                        String roomBathFacility,
                        String roomBath,
                        String roomHomeTheater,
                        String roomAircondition,
                        String roomTv,
                        String roomPc,
                        String roomCable,
                        String roomInternet,
                        String roomRefrigerator,
                        String roomToiletries,
                        String roomSofa,
                        String roomCook,
                        String roomTable,
                        String roomHairdryer) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.roomBathFacility = roomBathFacility;
        this.roomBath = roomBath;
        this.roomHomeTheater = roomHomeTheater;
        this.roomAircondition = roomAircondition;
        this.roomTv = roomTv;
        this.roomPc = roomPc;
        this.roomCable = roomCable;
        this.roomInternet = roomInternet;
        this.roomRefrigerator = roomRefrigerator;
        this.roomToiletries = roomToiletries;
        this.roomSofa = roomSofa;
        this.roomCook = roomCook;
        this.roomTable = roomTable;
        this.roomHairdryer = roomHairdryer;
    }


    public static RoomResponse from(Room room) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(room.getRoomFacilities());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return RoomResponse.builder()
                .id(room.getId())
                .name(room.getName())
                .price(room.getPrice())
                .stock(room.getStock())
                .imageUrl(room.getImages().get(0).getUrl())
                .roomBathFacility(jsonNode.get("roombathfacility").asText())
                .roomHomeTheater(jsonNode.get("roomhometheater").asText())
                .roomTv(jsonNode.get("roomtv").asText())
                .roomSofa(jsonNode.get("roomsofa").asText())
                .roomCook(jsonNode.get("roomcook").asText())
                .roomCable(jsonNode.get("roomcable").asText())
                .roomTable(jsonNode.get("roomtable").asText())
                .roomPc(jsonNode.get("roompc").asText())
                .roomToiletries(jsonNode.get("roomtoiletries").asText())
                .roomInternet(jsonNode.get("roominternet").asText())
                .roomHairdryer(jsonNode.get("roomhairdryer").asText())
                .roomAircondition(jsonNode.get("roomaircondition").asText())
                .roomBath(jsonNode.get("roombath").asText())
                .build();

    }
}
