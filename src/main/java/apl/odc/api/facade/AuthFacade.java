package apl.odc.api.facade;

import apl.odc.domain.user.application.SignInService;
import apl.odc.domain.user.dto.request.SignInRequest;
import apl.odc.domain.user.dto.response.SignInResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final SignInService signInService;

    @Transactional
    public SignInResponse signIn(SignInRequest signInRequest) {
        return signInService.signIn(signInRequest);
    }

}
