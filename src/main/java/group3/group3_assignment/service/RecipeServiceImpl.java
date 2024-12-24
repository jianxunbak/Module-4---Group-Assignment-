package group3.group3_assignment.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import group3.group3_assignment.controller.RecipeController;
import group3.group3_assignment.entity.Recipe;
import group3.group3_assignment.entity.User;
import group3.group3_assignment.exception.RecipeNotFoundException;
import group3.group3_assignment.exception.UserNotAuthorizeException;
import group3.group3_assignment.exception.UserNotFoundException;
import group3.group3_assignment.repository.RecipeRepo;
import group3.group3_assignment.repository.UserRepo;

@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepo recipeRepo;
    private UserRepo userRepo;
    private static final Logger logger = LoggerFactory.getLogger(RecipeController.class);

    public RecipeServiceImpl(RecipeRepo recipeRepo, UserRepo userRepo) {
        this.recipeRepo = recipeRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Recipe createRecipeToUser(Long userId, Recipe recipe) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUsername = authentication.getName();

        User selectedUser = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + userId + " is not found"));

        if (!authenticatedUsername.equals(selectedUser.getUsername())) {
            throw new UserNotAuthorizeException(userId, "save", "recipe");
        }

        recipe.setUser(selectedUser);
        logger.info("Created new Recipe");
        return recipeRepo.save(recipe);
    }

    @Override
    public Recipe getOneRecipe(Integer id) {
        logger.info("Got one recipe with id " + id + ".");
        return recipeRepo.findById(id).orElseThrow(() -> new RecipeNotFoundException(id));
    }

    @Override
    public List<Recipe> getAllRecipes() {
        logger.info("Got all recipes");
        return recipeRepo.findAll();
    }

    @Override
    public Recipe updateOneRecipe(Integer recipeId, Recipe recipe) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUsername = authentication.getName();
        // try to get recipe. if not found, throw exception.
        Recipe recipeToUpdate = recipeRepo.findById(recipeId).orElseThrow(() -> new RecipeNotFoundException(recipeId));

        // check if found recipe belongs to the user who created it. if not, throw
        // exception. so only the user who created it can edit.
        if (!authenticatedUsername.equals(recipeToUpdate.getUser().getUsername())) {
            throw new UserNotAuthorizeException(recipeToUpdate.getUser().getId(), "edit", "recipe");
        }

        // if user id is the same, contune to update the recipe
        recipeToUpdate.setDescription(recipe.getDescription());
        recipeToUpdate.setIngredients(recipe.getIngredients());
        recipeToUpdate.setSteps(recipe.getSteps());
        recipeToUpdate.setTitle(recipe.getTitle());
        recipeToUpdate.setImgSrc(recipe.getImgSrc());
        logger.info("User with id " + recipeToUpdate.getUser().getId() + "Updated recipe with id " + recipeId + ".");
        return recipeRepo.save(recipeToUpdate);
    }

    @Override
    public void deleteRecipe(Integer recipeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUsername = authentication.getName();

        // try to get recipe if not found, throw exception.
        Recipe recipeToDelete = recipeRepo.findById(recipeId).orElseThrow(() -> new RecipeNotFoundException(recipeId));
        // check if found recipe belongs to the user who created it. if not, throw
        // exception. so only the user who created it can delete.
        if (!authenticatedUsername.equals(recipeToDelete.getUser().getUsername())) {
            throw new UserNotAuthorizeException(recipeToDelete.getUser().getId(), "delete", "recipe");
        }
        // delete the recipe id user id is valid
        recipeRepo.delete(recipeToDelete);
        logger.info("Deleted recipe with id " + recipeId + ".");
    }

}