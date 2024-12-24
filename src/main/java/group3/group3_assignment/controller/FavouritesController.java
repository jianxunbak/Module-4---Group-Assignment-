package group3.group3_assignment.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import group3.group3_assignment.entity.Favourites;
import group3.group3_assignment.service.FavouritesService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Transactional
@RestController
@RequestMapping("/favourites")
public class FavouritesController {
  @PersistenceContext
  EntityManager entityManager;

  private FavouritesService favouritesService;
  private static final Logger logger = LoggerFactory.getLogger(RecipeController.class);

  public FavouritesController(FavouritesService favouritesService) {
    this.favouritesService = favouritesService;
  }

  @PostMapping("/{userId}/{recipeId}")
  public ResponseEntity<Favourites> addFavouriteToRecipe(@PathVariable Long userId, @PathVariable Integer recipeId,
      @RequestBody Favourites favourites) {
    Favourites newFavourites = favouritesService.addFavourites(userId, recipeId, favourites);
    logger.info(
        "User (userId " + userId + ") added one recipe (recipeId " + recipeId + ") to favourites list");
    return new ResponseEntity<>(newFavourites, HttpStatus.CREATED);
  }

  // Delete one favourite ("unfavourite one recipe item")
  @DeleteMapping("/{userId}/{FavId}")
  public ResponseEntity<HttpStatus> deleteOneFavourite(@PathVariable Long userId, @PathVariable Long FavId) {
    favouritesService.deleteFavourites(userId, FavId);
    logger.info(
        "User (userId " + userId + ") removed recipe (recipeId " + FavId + ") from favourites list");
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  // Retrieve all favourites recipes
  @GetMapping("/{userId}")
  public ResponseEntity<List<Favourites>> getAllFavourites(@PathVariable Long userId) {
    List<Favourites> foundfFavourites = favouritesService.getFavouritesByUserId(userId);
    logger.info("User (userId " + userId + ") retrieved array of recipes in favourites list");
    return new ResponseEntity<>(foundfFavourites, HttpStatus.OK);
  }

}
