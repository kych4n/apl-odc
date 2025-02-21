package apl.odc.api.controller;

import apl.odc.annotation.Permission;
import apl.odc.annotation.UserId;
import apl.odc.api.dto.request.InfoForFilteringRequest;
import apl.odc.api.dto.response.AttributeResponse;
import apl.odc.api.dto.response.SignedUrlResponse;
import apl.odc.api.facade.MainFacade;
import apl.odc.domain.acp.constant.Authority;
import apl.odc.global.common.BaseResponse;
import apl.odc.global.message.SuccessMessage;
import apl.odc.global.util.ApiResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Permission(value = {Authority.READ, Authority.CREATE})
    @PostMapping
    public ResponseEntity<BaseResponse<?>> getEncryptedData(
            @RequestBody @Validated InfoForFilteringRequest infoForFilteringRequest, @UserId Long userId)
            throws Exception {
        mainFacade.prepareForFiltering(infoForFilteringRequest);
        mainFacade.filter();
        mainFacade.encrypt(userId);
        SignedUrlResponse signedUrl = mainFacade.getSignedUrl();
        return ApiResponseUtil.success(SuccessMessage.SUCCESS, signedUrl);
    }

    @Permission(value = {Authority.READ, Authority.CREATE})
    @DeleteMapping
    public ResponseEntity<BaseResponse<?>> removeData() {
        mainFacade.remove();
        return ApiResponseUtil.success(SuccessMessage.DELETED);
    }

}
