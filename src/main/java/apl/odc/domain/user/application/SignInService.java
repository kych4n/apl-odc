package apl.odc.domain.user.application;

import apl.odc.auth.jwt.JwtToken;
import apl.odc.auth.jwt.JwtTokenGenerator;
import apl.odc.auth.otp.OtpHandler;
import apl.odc.domain.acp.ACP;
import apl.odc.domain.acp.constant.Authority;
import apl.odc.domain.user.User;
import apl.odc.domain.user.dto.request.SignInRequest;
import apl.odc.domain.user.dto.response.SignInResponse;
import apl.odc.domain.user.repository.UserRepository;
import apl.odc.global.exception.NotFoundException;
import apl.odc.global.exception.UnauthorizedException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignInService {

    private final JwtTokenGenerator jwtTokenGenerator;
    private final OtpHandler otpHandler;
    private final UserRepository userRepository;

    public SignInResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.findByUsernameAndPassword(signInRequest.username(), signInRequest.password())
                .orElseThrow(NotFoundException::wrong);

        if (!otpHandler.verifyTotp(user.getSecretKey(), signInRequest.totp())) {
            throw UnauthorizedException.wrong();
        }

        List<Authority> authorities = user.getACPs().stream().map(ACP::getAuthority).toList();

        JwtToken token = createToken(user.getId().toString(), authorities);
        user.setRefreshToken(token.refreshToken());

        return SignInResponse.of(token.accessToken(), token.refreshToken());
    }

    private JwtToken createToken(String payload, List<Authority> authorities) {
        return JwtToken.of(jwtTokenGenerator.createAccessToken(payload, authorities),
                jwtTokenGenerator.createRefreshToken(payload, authorities));
    }

}
