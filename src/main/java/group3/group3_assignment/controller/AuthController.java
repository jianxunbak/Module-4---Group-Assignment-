package group3.group3_assignment.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import group3.group3_assignment.config.UserAuth;
import group3.group3_assignment.entity.AuthRequest;
import group3.group3_assignment.service.JwtUtillService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private JwtUtillService jwtUtillService;
    private UserAuth userAuth;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(JwtUtillService jwtUtillService, UserAuth userAuth) {
        this.jwtUtillService = jwtUtillService;
        this.userAuth = userAuth;
    }

    @PostMapping("/generateToken")
    public ResponseEntity<Map<String, String>> generateToken(@RequestBody AuthRequest authRequest) {
        logger.debug("entered generate token method with " + authRequest);

        Authentication authentication = userAuth.authentication(authRequest.getUsername(),
                authRequest.getPassword());

        logger.debug("recieved results from authentication method: " + authentication);
        if (authentication.isAuthenticated()) {
            logger.debug("authentication is successful. calling generateToken service");
            Map<String, String> token = jwtUtillService.generateToken(authRequest.getUsername());
            logger.info("successfully issued JWS token to " + authRequest.getUsername() + ".");
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Unexpected authentication failure."));

    }

    @PostMapping("/validateToken")
    public ResponseEntity<Map<String, String>> validateToken(
            @RequestHeader("Authorization") String authorizationHeader) {
        Map<String, String> username = jwtUtillService.validateToken(authorizationHeader);
        logger.info("successfully validated JWS token");
        return new ResponseEntity<>(username, HttpStatus.OK);
    }

}
