package com.fastcampus.reserve.interfaces.user;

import com.fastcampus.reserve.application.user.CartFacade;
import com.fastcampus.reserve.common.response.CommonResponse;
import com.fastcampus.reserve.common.security.PrincipalDetails;
import com.fastcampus.reserve.interfaces.user.dto.request.CartItemAddRequest;
import com.fastcampus.reserve.interfaces.user.dto.request.CartItemDeleteRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/carts")
public class CartController {

    private final CartFacade cartFacade;
    private final CartDtoMapper mapper;

    @PostMapping
    public CommonResponse<Void> addItem(
        @AuthenticationPrincipal PrincipalDetails principal,
        @RequestBody CartItemAddRequest request
    ) {
        Long userId = principal.id();
        var dto = mapper.of(request);

        cartFacade.addItem(userId, dto);

        return CommonResponse.ok();
    }

    @DeleteMapping
    public CommonResponse<Void> removeItems(
        @AuthenticationPrincipal PrincipalDetails principal,
        @RequestBody CartItemDeleteRequest request
    ) {
        Long userId = principal.id();
        var dto = mapper.of(request);

        cartFacade.deleteItems(userId, dto);

        return CommonResponse.ok();
    }
}
