package com.memesphere.jwt;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = resolveToken(servletRequest);

            if (StringUtils.hasText(jwt) && tokenProvider.validateAccessToken(jwt)) {

                // JWT에서 사용자의 ID를 추출해서 Authentication 객체생성
                Authentication authentication = this.tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            // 이상이 없다면 다음 필터로 진행
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (JwtException e) {
            log.error("[JWTExceptionHandlerFilter] " + e.getMessage());
            servletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            servletResponse.setContentType("application/json;charset=UTF-8");
            servletResponse.getWriter().write("{\"error\": \"Unauthorized - " + e.getMessage() + "\"}");
        } catch (Exception e) {
            log.error("[ExceptionHandlerFilter] " + e.getMessage());
            handleOtherExceptions(servletResponse, e);
        }
    }

    private void handleOtherExceptions(HttpServletResponse response, Exception ex) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"Error processing request - " + ex.getMessage() + "\"}");
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && StringUtils.startsWithIgnoreCase(bearerToken,
                "Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /*
    // 인증 처리
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 사용자 이름을 기반으로 인증 객체를 생성
    private Authentication createAuthentication(String username) {
        log.info("Setting authentication for user: {}", username);
        UserDetails userDetails = memberDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
    */
}