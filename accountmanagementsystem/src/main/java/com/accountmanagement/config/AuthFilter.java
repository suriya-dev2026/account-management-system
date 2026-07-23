package com.accountmanagement.config;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.accountmanagement.constants.message.UserMessage;
import com.accountmanagement.model.User;

import com.accountmanagement.repository.UserRepository;
import com.accountmanagement.utility.TokenUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFilter extends OncePerRequestFilter {

    private final TokenUtility tokenUtility;

    private final UserRepository userRepository;

    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final RedisTemplate<String, String> redisTemplate;

    AuthFilter(TokenUtility tokenUtility, UserRepository userRepository,
            RedisTemplate<String, String> redisTemplate) {
        this.tokenUtility = tokenUtility;
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String accessToken = getJWTFromRequest(request);
            if (accessToken == null) {
                sendError(response, UserMessage.INVALID_REQUEST, 403);
                return;
            }
            String userName = tokenUtility.extractSessionId(accessToken);

            User user = userRepository.findByUserName(userName);
            if (user == null) {
                sendError(response, UserMessage.USER_NOT_FOUND, 401);
                return;
            }
            if (user.getIsAccountLocked()) {
                sendError(response, UserMessage.ACCOUNT_LOCKED, 423);
                return;
            }
            String token = redisTemplate.opsForValue().get(accessToken);
            if (token == null) {
                sendError(response, UserMessage.INVALID_TOKEN, 401);
                return;
            }
            if (tokenUtility.isTokenExpired(accessToken)) {
                sendError(response, UserMessage.TOKEN_EXPIRED, 401);
                return;
            }
            letProceedFurther(user, accessToken, filterChain, request, response);
        } catch (JwtException e) {
            sendError(response, UserMessage.INVALID_TOKEN, 401);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            sendError(response, e.getMessage(), 403);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.equals("/organization/register")
                || path.equals("/organization/update/")
                || path.equals("/organization/delete/")
                || path.equals("/organization")
                || path.equals("/register")
                || path.equals("/login")
                || path.equals("/verify/otp")
                || path.startsWith("/refreshKey")
                || path.equals("/verify/reset/otp")
                || path.startsWith("/forgot/password")
                || path.equals("/change/password")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.equals("/swagger-ui.html");
    }

    private void letProceedFurther(User user, String accessToken, FilterChain filterChain,
            HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, accessToken,
                authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }

    private String getJWTFromRequest(HttpServletRequest request) {
        String authtoken = request.getHeader("Authorization");
        if (authtoken != null && StringUtils.hasText(authtoken) && authtoken.startsWith("Bearer ")) {
            return authtoken.substring(7, authtoken.length());
        }
        return null;
    }

    public void sendError(HttpServletResponse response, String message, int webState)
            throws JsonProcessingException, IOException {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now().toString());
        errorDetails.put("status", webState);
        errorDetails.put("message", message);
        errorDetails.put("error", "error");
        response.setStatus(webState);
        response.setContentType("application/json");
        response.getWriter().write(OBJECT_MAPPER.writeValueAsString(errorDetails));
    }

}
