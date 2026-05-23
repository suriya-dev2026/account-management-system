package com.accountmanagement.config;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

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

    @Autowired
    private TokenUtility tokenUtility;

    @Autowired
    private UserRepository userRepository;;

    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final List<String> PUBLIC_URLS = List.of(
            "/user/register",
            "/user/login",
            "/user/verify/otp",
            "/swagger-ui/index.html",
            "/v3/api-docs");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestUri = request.getRequestURI();

        if (PUBLIC_URLS.stream().anyMatch(requestUri::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String accessToken = getJWTFromRequest(request);

            if (accessToken == null) {
                filterChain.doFilter(request, response);
                return;
            }

            final String userName = tokenUtility.extractSessionId(accessToken);

            User user = userRepository.findByUserName(userName);

            if (user == null) {
                sendError(response, "User Not Found", 401);
                return;
            }

            if (tokenUtility.isTokenExpired(accessToken)) {
                sendError(response, "Token Expired", 401);
                return;
            }

            letProceedFurther(user, accessToken, filterChain, request, response);

        } catch (CredentialsExpiredException e) {
            sendError(response, "Token Expired", 401);
            return;

        } catch (JwtException e) {
            sendError(response, "Invalid Token", 401);
            return;

        } catch (Exception e) {
            try {
                e.printStackTrace();
                sendError(response, "Access Token Missing", 403);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
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
        if (authtoken != null && StringUtils.hasText(authtoken) && authtoken.startsWith("Bearer")) {
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
