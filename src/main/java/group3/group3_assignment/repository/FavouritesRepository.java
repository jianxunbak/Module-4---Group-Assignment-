package group3.group3_assignment.repository;

import java.util.List;
import java.util.Optional;

//import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import group3.group3_assignment.entity.Favourites;
import group3.group3_assignment.entity.Favourites.FavouritesBuilder;

public interface FavouritesRepository extends JpaRepository<Favourites, Long> {

  // Favourites save(Optional<Favourites> favouritesToUpdate);

  // List<Favourites> findAllById(Long id);

  Optional<List<Favourites>> findAllByUserId(Long userId);

  // Favourites findByUserIdAndRecipeId(Long userId, Integer recipeId);
  // Favourites findByUserIdAndRecipeId(Long userId, Long recipeId);
  Optional<Favourites> findByUserIdAndRecipeId(Long userId, Long recipeId);

  // void deleteByUserIdAndRecipeId(Long userId, Integer recipeId);
  void deleteByUserIdAndRecipeId(Long userId, Long recipeId);

  Favourites save(FavouritesBuilder user);

}
