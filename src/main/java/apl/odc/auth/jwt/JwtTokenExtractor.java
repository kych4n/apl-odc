package apl.odc.auth.jwt;

import apl.odc.domain.acp.constant.Authority;
import io.jsonwebtoken.Jwts;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenExtractor {

    private final JwtProperties jwtProperties;
    private final KeyProvider keyProvider;

    public String getSubject(String token) {
        return Jwts.parser()
                .verifyWith(keyProvider.getKeyFromString(jwtProperties.secretKey()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public List<Authority> getAuthorities(String token) {
        return Jwts.parser()
                .verifyWith(keyProvider.getKeyFromString(jwtProperties.secretKey()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("authorities", ArrayList.class)
                .stream()
                .map(authority -> Authority.valueOf((String) authority))
                .toList();
    }

}
