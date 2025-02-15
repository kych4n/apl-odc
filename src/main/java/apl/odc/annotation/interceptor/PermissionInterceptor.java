package apl.odc.annotation.interceptor;

import apl.odc.annotation.Permission;
import apl.odc.auth.jwt.JwtTokenExtractor;
import apl.odc.auth.jwt.JwtTokenParser;
import apl.odc.domain.acp.constant.Authority;
import apl.odc.global.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class PermissionInterceptor implements HandlerInterceptor {

    private final JwtTokenExtractor jwtTokenExtractor;
    private final JwtTokenParser jwtTokenParser;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        HandlerMethod method = (HandlerMethod) handler;

        Permission permission = method.getMethodAnnotation(Permission.class);
        if (permission == null) {
            log.info("Successful Authorization.");
            return true;
        }

        Authority[] permitted_authorities = permission.value();

        String token = jwtTokenParser.getToken(request.getHeader(HttpHeaders.AUTHORIZATION));
        List<Authority> authorities = jwtTokenExtractor.getAuthorities(token);

        if (new HashSet<>(authorities).containsAll(Arrays.stream(permitted_authorities).toList())) {
            log.info("Successful Authorization.");
            return true;
        } else {
            throw UnauthorizedException.empty();
        }
    }

}
