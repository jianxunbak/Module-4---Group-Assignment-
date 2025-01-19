package group3.group3_assignment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import group3.group3_assignment.entity.User;
import group3.group3_assignment.entity.Favourites;
import group3.group3_assignment.entity.Recipe;
import group3.group3_assignment.exception.FavUserNotFoundException;
import group3.group3_assignment.exception.DuplicateFavouritesException;
//import group3.group3_assignment.exception.FavUserNotFoundException;
import group3.group3_assignment.exception.FavouritesNotFoundException;
import group3.group3_assignment.exception.GlobalExceptionHandler;
import group3.group3_assignment.exception.RecipeNotFoundException;
import group3.group3_assignment.exception.UserNotAuthorizeException;
import group3.group3_assignment.exception.UserNotFoundException;
import group3.group3_assignment.repository.UserRepo;
import group3.group3_assignment.repository.FavouritesRepository;
import group3.group3_assignment.repository.RecipeRepo;

@Service
public class FavouritesServiceImpl implements FavouritesService {

  private FavouritesRepository favouritesRepository;
  private RecipeRepo recipeRepo;
  private UserRepo userRepo;

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  public FavouritesServiceImpl(FavouritesRepository favouritesRepository, RecipeRepo recipeRepo,
      UserRepo userRepo) {
    this.favouritesRepository = favouritesRepository;
    this.recipeRepo = recipeRepo;
    this.userRepo = userRepo;
  }

  // @Override
  // public User updateUser(Long id, User user) {
  // Authentication authentication =
  // SecurityContextHolder.getContext().getAuthentication();
  // String authenticatedUsername = authentication.getName();

  // User existingUser = userRepo.findById(id)
  // .orElseThrow(() -> new UserNotFoundException("user with id: " + id + "is not
  // found."));
  // if (authenticatedUsername.equals(existingUser.getUsername())) {
  // existingUser.setUsername(user.getUsername());
  // existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
  // existingUser.setEmail(user.getEmail());
  // return userRepo.save(existingUser);
  // } else
  // throw new UserNotAuthorizeException(id, "edit", "user details");
  // }

  @Override
  public void deleteFavourites(Long userId, Long recipeId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String authenticatedUsername = authentication.getName();

    // Favourites favouritesToDelete = favouritesRepository.findById(recipeId)
        // .orElseThrow(() -> new FavouritesNotFoundException());
    Favourites favouritesToDelete = favouritesRepository.findByUserIdAndRecipeId(userId, recipeId)
        .orElseThrow(FavouritesNotFoundException::new);
    if (!favouritesToDelete.getUser().getId().equals(userId)) {
      throw new FavouritesNotFoundException();
    }
    if (!authenticatedUsername.equals(favouritesToDelete.getUser().getUsername())) {
      throw new UserNotAuthorizeException(userId, "delete", "another user's favourites");
    }

    favouritesRepository.delete(favouritesToDelete);
    // verify(favouritesRepository, times(1)).delete(favouritesToDelete);

    // if (favouritesToDelete.getUser().getId().equals(userId)) {
    // if (authenticatedUsername.equals(favouritesToDelete.getUser().getUsername()))
    // {
    // favouritesRepository.delete(favouritesToDelete);
    // } else
    // throw new UserNotAuthorizeException(userId, "delete", "favourites");
    // } else
    // throw new FavouritesNotFoundException();

  }

  @Override
  public List<Favourites> getFavouritesByUserId(Long userId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String authenticatedUsername = authentication.getName();

    User existingUser = userRepo.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("user with id: " + userId + "is not found."));
    if (!authenticatedUsername.equals(existingUser.getUsername())) {
      throw new UserNotAuthorizeException(userId, "get", "another user's favourites");
    }
    List<Favourites> userFavouritesList = favouritesRepository.findAllByUserId(userId)
        .orElseThrow(() -> new FavUserNotFoundException(userId));
    return userFavouritesList;
  }

  // @Override
  // // public Favourites addFavourites(Long userId, Integer recipeId, Favourites favourites) {
  // public Favourites addFavourites(Long userId, Long recipeId, Favourites favourites) {
  //   Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  //   String authenticatedUsername = authentication.getName();
  //   User existingUser = userRepo.findById(userId)
  //       .orElseThrow(() -> new UserNotFoundException("user with id: " + userId + "is not found."));
  //   Recipe selectedRecipe = recipeRepo.findById(recipeId).orElseThrow(() -> new RecipeNotFoundException(recipeId));
  //   User selectedUser = userRepo.findById(userId).orElseThrow(() -> new FavUserNotFoundException(userId));

  //   if (!authenticatedUsername.equals(existingUser.getUsername())) {
  //     throw new UserNotAuthorizeException(userId, "add", "recipe to another user's favourites");
  //   }
  //   if (favouritesRepository.findByUserIdAndRecipeId(userId, recipeId) != null) {
  //     throw new DuplicateFavouritesException();
  //   }

  //   favourites.setRecipe(selectedRecipe);
  //   favourites.setUser(selectedUser);
  //   return favouritesRepository.save(favourites);

  // }
}
