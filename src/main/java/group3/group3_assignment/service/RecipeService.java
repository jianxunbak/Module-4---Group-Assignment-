package group3.group3_assignment.service;

import java.util.List;

import group3.group3_assignment.entity.Recipe;

public interface RecipeService {
    Recipe createRecipe(Recipe recipe);

    Recipe getOneRecipe(Integer id);

    List<Recipe> getAllRecipes();

    Recipe updateOneRecipe(Integer id, Recipe recipe);

    void deleteRecipe(Integer id);

}
