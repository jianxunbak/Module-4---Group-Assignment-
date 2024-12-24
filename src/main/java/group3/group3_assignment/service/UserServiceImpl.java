package group3.group3_assignment.service;

import group3.group3_assignment.entity.User;
import group3.group3_assignment.entity.Recipe;
import group3.group3_assignment.exception.UserNotAuthorizeException;
import group3.group3_assignment.exception.UserNotFoundException;
import group3.group3_assignment.repository.UserRepo;
import group3.group3_assignment.repository.RecipeRepo;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final RecipeRepo recipeRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, RecipeRepo recipeRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.recipeRepo = recipeRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User addUser(User user) {
        // Encrypt the password before saving the user
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        return userRepo.save(user);
    }

    @Override
    public User getUser(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User updateUser(Long id, User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUsername = authentication.getName();

        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("user with id: " + id + "is not found."));
        if (authenticatedUsername.equals(existingUser.getUsername())) {
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            existingUser.setEmail(user.getEmail());
            return userRepo.save(existingUser);
        } else
            throw new UserNotAuthorizeException(id, "edit", "user details");
    }

    @Override
    public void deleteUser(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUsername = authentication.getName();

        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("user with id: " + id + "is not found."));

        if (authenticatedUsername.equals(existingUser.getUsername())) {
            userRepo.delete(existingUser);
        } else
            throw new UserNotAuthorizeException(id, "edit", "user details");
    }

    // @Override
    // public Recipe addRecipeToUser(Long id, Recipe recipe) {
    // User selectedUser = userRepo.findById(id)
    // .orElseThrow(() -> new UserNotFoundException(("User not found with ID: " +
    // id)));
    // recipe.setUser(selectedUser);
    // return recipeRepo.save(recipe);
    // }

    // @Override
    // public List<Recipe> getRecipesByUser(Long userId) {
    // User user = userRepo.findById(userId)
    // .orElseThrow(() -> new UserNotFoundException("User not found with ID: " +
    // userId));
    // return user.getRecipes();
    // }

    // @Override
    // public Recipe uploadRecipe(Integer userId, Recipe recipe) {
    // User user = userRepo.findById(userId)
    // .orElseThrow(() -> new UserNotFoundException("User not found with ID: " +
    // userId));
    // // recipe.setUser(user); // Uncomment this line if User-Recipe relationship
    // is
    // // implemented
    // return recipeRepo.save(recipe);
    // }
}
