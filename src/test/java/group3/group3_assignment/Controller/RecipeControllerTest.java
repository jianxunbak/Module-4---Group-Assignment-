// package group3.group3_assignment.Controller;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import
// org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.annotation.DirtiesContext;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.RequestBuilder;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

// import com.fasterxml.jackson.databind.ObjectMapper;

// import group3.group3_assignment.entity.Recipe;
// import group3.group3_assignment.entity.User;

// import static
// org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import java.util.Arrays;

// //integration test
// @SpringBootTest
// @DirtiesContext
// @AutoConfigureMockMvc
// public class RecipeControllerTest {
// @Autowired
// private MockMvc mockMvc;
// @Autowired
// private ObjectMapper objectMapper;

// private Recipe recipe;
// private User user;

// @BeforeEach
// public void setUp() {
// user = User.builder()
// .username("john_doe")
// .email("john_doe@example.com")
// .password("password123")
// .build();

// recipe = Recipe.builder()
// .title("Stir Fried Noodles")
// .imgSrc("https://www.seriouseats.com/thmb/KOV3OvnLeh6RW64lEnRixbRxOq4=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/SEA-QiAi-stir-fried-lo-mein-noodles-pork-vegetables-recipe-hero-a55a4baa9f22449fbe036142f1047430.jpg")
// .description("A quick and tasty stir-fried noodle recipe packed with veggies
// and savory sauce.")
// .ingredients(Arrays.asList("Egg noodles",
// "Soy sauce"))
// .steps(Arrays.asList("Cook the egg noodles according to package
// instructions.",
// "Stir-fry garlic, carrots, and bell peppers in sesame oil."))
// .build();
// }

// @Test
// public void testGetAllRecipes() throws Exception {
// // 1) build a get request for "/recipe"
// RequestBuilder request = MockMvcRequestBuilders.get("/recipe");
// // 2) perform the request and get a response and assert
// mockMvc.perform(request).andExpect(status().isOk())
// .andExpect(content().contentType(MediaType.APPLICATION_JSON))
// .andExpect(jsonPath("$.size()").value(5));
// }
// }
