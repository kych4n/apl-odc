package apl.odc.api.controller;

import apl.odc.annotation.Permission;
import apl.odc.api.dto.response.AttributeResponse;
import apl.odc.api.facade.MainFacade;
import apl.odc.domain.acp.constant.Authority;
import apl.odc.global.common.BaseResponse;
import apl.odc.global.message.SuccessMessage;
import apl.odc.global.util.ApiResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MainController {

    private final MainFacade mainFacade;

    @Permission(value = {Authority.READ})
    @GetMapping
    public ResponseEntity<BaseResponse<?>> getAttributes() {
        AttributeResponse attributes = mainFacade.getAttributes();
        return ApiResponseUtil.success(SuccessMessage.SUCCESS, attributes);
    }
    
}
