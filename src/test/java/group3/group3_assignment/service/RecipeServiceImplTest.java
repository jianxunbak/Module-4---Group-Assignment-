package group3.group3_assignment.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import group3.group3_assignment.entity.Recipe;
import group3.group3_assignment.entity.User;
import group3.group3_assignment.exception.RecipeNotFoundException;
import group3.group3_assignment.repository.RecipeRepo;
import group3.group3_assignment.repository.UserRepo;

// unit test for service
@ExtendWith(MockitoExtension.class)
public class RecipeServiceImplTest {

    // 1. Create a mock repository
    @Mock
    public RecipeRepo recipeRepo;

    @Mock
    public UserRepo userRepo;

    // 2. inject the mock repository into service implementation class
    @InjectMocks
    RecipeServiceImpl recipeServiceImpl;

    private Recipe recipe;
    private User user;

    // initialize recipe object before each test
    @BeforeEach
    public void setup() {
        user = User.builder().id(1L).build();
        recipe = Recipe.builder().title("Stir Fried Noodles")
                .id(1)
                .user(user)
                .imgSrc("https://www.seriouseats.com/thmb/KOV3OvnLeh6RW64lEnRixbRxOq4=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/SEA-QiAi-stir-fried-lo-mein-noodles-pork-vegetables-recipe-hero-a55a4baa9f22449fbe036142f1047430.jpg")
                .description("A quick and tasty stir-fried noodle recipe packed with veggies and savory sauce.")
                .ingredients(Arrays.asList("Egg noodles",
                        "Soy sauce",
                        "Garlic cloves",
                        "Carrots",
                        "Bell peppers",
                        "Sesame oil",
                        "Green onions"))
                .steps(Arrays.asList("Cook the egg noodles according to package instructions.",
                        "Stir-fry garlic, carrots, and bell peppers in sesame oil.",
                        "Add cooked noodles and soy sauce, tossing until combined.",
                        "Garnish with green onions and serve."))
                .user(user)
                .build();

    }

    @Test
    public void testGetAllRecipes() {
        // create a list of recipes to simulate a list of all the recipes
        List<Recipe> recipes = Arrays.asList(recipe);

        // mocking recipeRepo behaviour by finding all recipes and returning the list of
        // recipes
        when(recipeRepo.findAll()).thenReturn(recipes);

        // calling the getAllRecipes method and returning the recipes
        List<Recipe> allRecipes = recipeServiceImpl.getAllRecipes();
        assertEquals(recipes, allRecipes);
    }

}