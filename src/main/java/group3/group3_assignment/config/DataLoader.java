package group3.group3_assignment.config;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import group3.group3_assignment.entity.Recipe;
import group3.group3_assignment.repository.RecipeRepo;
import jakarta.annotation.PostConstruct;

@Component
public class DataLoader {
    private RecipeRepo recipeRepo;

    public DataLoader(RecipeRepo recipeRepo) {
        this.recipeRepo = recipeRepo;
    }

    @PostConstruct
    public void loadData() {
        recipeRepo.deleteAll();
        recipeRepo.save(Recipe.builder()
                .title("Stir Fried Noodles")
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
                .build());

    }
}
