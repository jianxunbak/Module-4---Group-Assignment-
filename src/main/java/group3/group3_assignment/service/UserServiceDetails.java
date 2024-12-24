package group3.group3_assignment.service;

import java.util.ArrayList;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import group3.group3_assignment.entity.User;
import group3.group3_assignment.exception.UserNotFoundException;
import group3.group3_assignment.repository.UserRepo;

@Configuration
public class UserServiceDetails implements UserDetailsService {

    private UserRepo userRepo;

    public UserServiceDetails(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("username: " + username + "not found"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

}
