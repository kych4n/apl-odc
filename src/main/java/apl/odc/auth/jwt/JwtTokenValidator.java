package apl.odc.auth.jwt;

import apl.odc.global.exception.UnauthorizedException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenValidator {

    private final JwtProperties jwtProperties;
    private final KeyProvider keyProvider;

    public void validate(String token) {
        try {
            Jwts.parser()
                    .verifyWith(keyProvider.getKeyFromString(jwtProperties.secretKey()))
                    .build()
                    .parseSignedClaims(token);
        } catch (SecurityException | MalformedJwtException | IllegalArgumentException | UnsupportedJwtException e) {
            throw UnauthorizedException.wrong();
        } catch (ExpiredJwtException e) {
            throw UnauthorizedException.expired();
        }
    }

}
