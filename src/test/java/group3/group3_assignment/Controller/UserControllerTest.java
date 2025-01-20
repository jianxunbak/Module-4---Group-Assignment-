// package group3.group3_assignment.Controller;

// import com.fasterxml.jackson.databind.ObjectMapper;

// import group3.group3_assignment.entity.User;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import org.springframework.beans.factory.annotation.Autowired;
// import
// org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.RequestBuilder;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

// import static
// org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @SpringBootTest
// @AutoConfigureMockMvc
// public class UserControllerTest {

// @Autowired
// private MockMvc mockMvc; // MockMvc to perform HTTP requests

// @Autowired
// private ObjectMapper objectMapper;

// // @Mock
// // private UserRepo userRepo; // Mock the UserRepo

// // @InjectMocks
// // private UserService userService; // Inject the mocked repo into the
// // UserService

// private User user;

// @BeforeEach
// public void setUp() {

// // Initialize the user object before each test
// // user = new User();
// // user.setUsername("john_doe");
// // user.setEmail("john_doe@example.com");
// // user.setPassword("password123");
// user = User.builder()
// .username("john_doe")
// .email("john_doe@example.com")
// .password("password123")
// .build();

// }

// @Test
// public void testCreateUser_ValidationError() throws Exception {
// // Create a user with invalid data (e.g., missing email)
// User invalidUser = User.builder()
// .username("valid_name")
// .email(null)
// .password("12345678")
// .build();

// // Serialize the invalid user object to JSON
// String invalidUserJson = objectMapper.writeValueAsString(invalidUser);

// // Perform POST request to "/users" and expect a 400 Bad Request due to
// // validation errors
// mockMvc.perform(MockMvcRequestBuilders.post("/users")
// .contentType(MediaType.APPLICATION_JSON)
// .content(invalidUserJson))
// .andExpect(status().isBadRequest()) // Expecting a 400 Bad Request
// .andExpect(jsonPath("$.message").value("Email cannot be blank.")); // Adjust
// based on
// // actual validation
// // error message
// }

// }
