package group3.group3_assignment.service;

import java.util.List;

import group3.group3_assignment.entity.Favourites;

public interface FavouritesService {

  void deleteFavourites(Long userId, Long recipeId);

  List<Favourites> getFavouritesByUserId(Long userId);

  Favourites addFavourites(Long userId, Long recipeId, Favourites favourites);

}
