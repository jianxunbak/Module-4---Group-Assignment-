package group3.group3_assignment.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import group3.group3_assignment.controller.AuthController;
import group3.group3_assignment.repository.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
@Configuration
public class JwtUtilServiceImpl implements JwtUtillService {
    UserRepo userRepo;

    @Autowired
    private Environment env;

    public JwtUtilServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    // secret key store as an env variable

    public String getSecretKey() {
        return env.getProperty("JWT_SECRET_KEY");
    }

    private SecretKey getKey() {
        String secretKey = getSecretKey();
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // // creates a crytographic secret key
    // private final SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

    public Map<String, String> generateToken(String username) {
        logger.debug("entered generate Token service");
        Date expirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24); // 1 day
        Date currentDate = new Date(System.currentTimeMillis());
        String jws = Jwts.builder()
                .subject(username)
                .expiration(expirationDate)
                .issuedAt(currentDate)
                .signWith(getKey())
                .compact(); // generate the final compact JWT
        logger.debug("finish building token");

        Map<String, String> response = new HashMap<>(); // create a Json
        logger.debug("created hash map");

        response.put("token", jws);
        logger.debug("added token in json");

        response.put("username", username);
        logger.debug("added username in json");
        logger.debug("returning to controller with response " + response);

        return response;
    }

    public Map<String, String> validateToken(String authorizationHeader) {
        // remove the "bearer that is sent from authroization header
        String token = authorizationHeader.replace("Bearer ", "");
        // verify that the token with the secret key
        Jws<Claims> claimsJws = Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token);
        // get the subject: username
        String username = claimsJws.getPayload().getSubject();
        Map<String, String> response = new HashMap<>();
        // return the username so it can be displayed in front end ("welcome username")
        response.put("username", username);
        return response;
    }

}
