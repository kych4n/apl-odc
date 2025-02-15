package apl.odc.auth.jwt;

import apl.odc.global.exception.UnauthorizedException;
import apl.odc.global.message.FailureMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenParser {

    private static final String BEARER_PREFIX = "Bearer ";

    public String getToken(String token) {
        if (token.startsWith(BEARER_PREFIX)) {
            return token.substring(BEARER_PREFIX.length());
        } else {
            throw new UnauthorizedException(FailureMessage.INVALID_TOKEN);
        }
    }

}