package apl.odc.api.controller;

import apl.odc.annotation.Permission;
import apl.odc.api.facade.AuthFacade;
import apl.odc.domain.acp.constant.Authority;
import apl.odc.domain.user.dto.request.SignInRequest;
import apl.odc.domain.user.dto.response.SignInResponse;
import apl.odc.global.common.BaseResponse;
import apl.odc.global.message.SuccessMessage;
import apl.odc.global.util.ApiResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthFacade authFacade;

    @PostMapping("/signin")
    public ResponseEntity<BaseResponse<?>> signIn(@RequestBody @Validated SignInRequest signInRequest) {
        SignInResponse signInResponse = authFacade.signIn(signInRequest);
        return ApiResponseUtil.success(SuccessMessage.SUCCESS, signInResponse);
    }

    @Permission(value = {Authority.READ})
    @GetMapping
    public ResponseEntity<BaseResponse<?>> assignIn() {
        return ApiResponseUtil.success(SuccessMessage.SUCCESS);
    }

}
