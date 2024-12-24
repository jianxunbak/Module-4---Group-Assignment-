package group3.group3_assignment.config;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import group3.group3_assignment.controller.AuthController;
import group3.group3_assignment.entity.User;
import group3.group3_assignment.exception.UserNotFoundException;
import group3.group3_assignment.repository.UserRepo;
import group3.group3_assignment.service.UserServiceDetails;

@Configuration
public class UserAuth {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserServiceDetails userServiceDetails;

    public UserAuth(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
            UserServiceDetails userServiceDetails) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userServiceDetails = userServiceDetails;
    }

    public Authentication authentication(String username, String rawPassword) {
        logger.debug("entered authentication method with" + username + rawPassword);

        UserDetails userDetails = userServiceDetails.loadUserByUsername(username);
        logger.debug("username: " + userDetails.getUsername() + "password: " + userDetails.getPassword());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                rawPassword);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        return authentication;

    }
}
