package com.fastcampus.reserve.domain.product.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FacilityInfoDto(
    String roombathfacility,
    String roombath,
    String roomhometheater,
    String roomaircondition,
    String roomtv,
    String roompc,
    String roomcable,
    String roominternet,
    String roomrefrigerator,
    String roomtoiletries,
    String roomsofa,
    String roomcook,
    String roomtable,
    String roomhairdryer
) {
}
