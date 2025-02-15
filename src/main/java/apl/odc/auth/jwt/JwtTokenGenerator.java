package apl.odc.auth.jwt;

import apl.odc.domain.acp.constant.Authority;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenGenerator {

    private static final String AUTHORITIES = "authorities";
    private static final String IS_ACCESS_TOKEN = "isAccessToken";

    private final JwtProperties jwtProperties;
    private final KeyProvider keyProvider;

    public String createAccessToken(String payload, List<Authority> authorities) {

        Date now = new Date();
        Map<String, Object> claims = new HashMap<>();

        claims.put(AUTHORITIES, authorities);
        claims.put(IS_ACCESS_TOKEN, true);

        return Jwts.builder()
                .header().type("JWT")
                .and()
                .issuedAt(now)
                .subject(payload)
                .claims(claims)
                .expiration(new Date(now.getTime() + jwtProperties.accessTokenValidTime()))
                .signWith(keyProvider.getKeyFromString(jwtProperties.secretKey()), SIG.HS256)
                .compact();
    }

    public String createRefreshToken(String payload, List<Authority> authorities) {

        Date now = new Date();
        Map<String, Object> claims = new HashMap<>();

        claims.put(AUTHORITIES, authorities);
        claims.put(IS_ACCESS_TOKEN, false);

        return Jwts.builder()
                .header().type("JWT")
                .and()
                .issuedAt(now)
                .subject(payload)
                .claims(claims)
                .expiration(new Date(now.getTime() + jwtProperties.refreshTokenValidTime()))
                .signWith(keyProvider.getKeyFromString(jwtProperties.secretKey()), SIG.HS256)
                .compact();
    }

}
