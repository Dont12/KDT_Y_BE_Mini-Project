package com.fastcampus.reserve.interfaces.user;

import com.fastcampus.reserve.application.user.CartFacade;
import com.fastcampus.reserve.common.response.CommonResponse;
import com.fastcampus.reserve.common.security.PrincipalDetails;
import com.fastcampus.reserve.interfaces.user.dto.request.CartItemAddRequest;
import com.fastcampus.reserve.interfaces.user.dto.response.CartListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping CommonResponse<CartListResponse> getList(
        @AuthenticationPrincipal PrincipalDetails principal,
        @RequestParam(required = false, defaultValue = "1") int page,
        @RequestParam(required = false, defaultValue = "10") int pageSize
    ) {
        int idx = page - 1;
        Pageable pageable = PageRequest.of(idx, pageSize);

        Long userId = principal.id();
        var dto = cartFacade.getList(userId, pageable);

        return CommonResponse.ok(mapper.of(dto));
    }
}
