package group3.group3_assignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import group3.group3_assignment.filter.JwtAuthFilter;

@EnableMethodSecurity
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationConfiguration authenticationConfiguration;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, AuthenticationConfiguration authenticationConfiguration) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationConfiguration = authenticationConfiguration;

    }

    @Bean
    public PasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    // HttpSecurity contains method to customize the security behaviours of
    // different requests
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // first try to match the /recipe route and permit it (dont need authentication)
                .authorizeHttpRequests(
                        authz -> authz.requestMatchers(HttpMethod.GET, "/recipe").permitAll()
                                .requestMatchers(HttpMethod.POST, "/auth/generateToken", "/users").permitAll()
                                // any other request will need to authentication
                                .anyRequest().authenticated())
                // adds a custom filter to intercep the requst so it can verify the token
                // UsernamePasswordAuthenticationFilter is a default Spring Security filter that
                // handles authentication using usernam and password (login form)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                // disables the cross site request forgery atacks
                .csrf(csrf -> csrf.disable());

        // the build method returns a SecurityFilterChain instance which contains all
        // the configuration made.
        return http.build();
    }
}
