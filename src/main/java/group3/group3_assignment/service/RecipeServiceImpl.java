package group3.group3_assignment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import group3.group3_assignment.entity.Recipe;
import group3.group3_assignment.exception.RecipeNotFoundException;
import group3.group3_assignment.repository.RecipeRepo;

@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepo recipeRepo;

    public RecipeServiceImpl(RecipeRepo recipeRepo) {
        this.recipeRepo = recipeRepo;
    }

    @Override
    public Recipe createRecipe(Recipe recipe) {
        return recipeRepo.save(recipe);
    }

    @Override
    public Recipe getOneRecipe(Integer id) {
        return recipeRepo.findById(id).orElseThrow(() -> new RecipeNotFoundException(id));
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepo.findAll();
    }

    @Override
    public Recipe updateOneRecipe(Integer id, Recipe recipe) {
        Recipe recipeToUpdate = recipeRepo.findById(id).orElseThrow(() -> new RecipeNotFoundException(id));
        recipeToUpdate.setDescription(recipe.getDescription());
        recipeToUpdate.setIngredients(recipe.getIngredients());
        recipeToUpdate.setSteps(recipe.getSteps());
        recipeToUpdate.setTitle(recipe.getTitle());
        return recipeRepo.save(recipeToUpdate);
    }

    @Override
    public void deleteRecipe(Integer id) {
        recipeRepo.findById(id).orElseThrow(() -> new RecipeNotFoundException(id));
        recipeRepo.deleteById(id);
    }

}
