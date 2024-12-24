package group3.group3_assignment.service;

import java.util.List;

import group3.group3_assignment.entity.Recipe;

public interface RecipeService {
    Recipe createRecipeToUser(Long userId, Recipe recipe);

    Recipe getOneRecipe(Integer userId);

    List<Recipe> getAllRecipes();

    Recipe updateOneRecipe(Integer recipeId, Recipe recipe);

    void deleteRecipe(Integer recipeId);

}
