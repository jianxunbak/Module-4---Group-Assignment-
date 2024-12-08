package group3.group3_assignment.exception;

public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(Integer id) {
        super("Could not find recipe with id: " + id + ".");
    }
}
