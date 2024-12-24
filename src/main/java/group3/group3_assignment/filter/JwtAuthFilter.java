package group3.group3_assignment.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import group3.group3_assignment.service.JwtUtilServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// extends OncePerRequestFilter is to execute Spring filter logic so that every request is filtered
// it does it by intercepting every HTTP request made in the app
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtilServiceImpl jwtUtilServiceImpl;

    public JwtAuthFilter(JwtUtilServiceImpl jwtUtilServiceImpl) {
        this.jwtUtilServiceImpl = jwtUtilServiceImpl;
    }

    @Override
    public void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain chain)
            throws IOException, ServletException {

        // Retrieves the Authorization header from the incoming HTTP request that is
        // sent from the client in a json format
        String authorizationHeader = request.getHeader("Authorization");
        // check if authorization header is not null and have bearer prefix
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // if true, validate the token using the validateToken method, which the method
            // will return the username Json data.
            Map<String, String> tokenData = jwtUtilServiceImpl.validateToken(authorizationHeader);
            // check if the return results contains username
            if (tokenData.containsKey("username")) {
                // if true, then proceed to get the username
                String username = tokenData.get("username");
                // create a UsernamePasswordAuthenticationToken object which represents the
                // authenticated user
                // first parameter: the user to authenticate (i.e.username)
                // second parameter: the passowrd (which in this case dont need as it is using
                // JWT)
                // third parameter: the user roles
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        username, null, Collections.emptyList());

                // store the authenticated user inside SecurityContextHolder object so
                // springboot knows give access to this user for future request
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        // this passes the request and response forward to the controllers
        chain.doFilter(request, response);
    }

}
