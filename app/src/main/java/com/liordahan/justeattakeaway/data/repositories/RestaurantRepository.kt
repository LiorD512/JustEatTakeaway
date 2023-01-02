package com.liordahan.justeattakeaway.data.repositories

import com.liordahan.justeattakeaway.data.local.database.dao.FavoritesRestaurantsDao
import com.liordahan.justeattakeaway.data.local.models.FavoritesRestaurants
import com.liordahan.justeattakeaway.data.remote.RestaurantApiService
import com.liordahan.justeattakeaway.data.remote.models.RestaurantResult
import com.skydoves.sandwich.ApiResponse

class RestaurantRepository(
    private val restaurantApiService: RestaurantApiService,
    private val favoritesRestaurantsDao: FavoritesRestaurantsDao
) {

    suspend fun getAllRestaurants(): ApiResponse<RestaurantResult> {
        return restaurantApiService.getAllRestaurants()
    }

    suspend fun getFavoriteRestaurants(): List<FavoritesRestaurants>{
        return favoritesRestaurantsDao.getFavoriteRestaurants()
    }

    suspend fun addRestaurantToFavorites(favoritesRestaurants: FavoritesRestaurants){
        favoritesRestaurantsDao.addFavoriteRestaurant(favoritesRestaurants)
    }

    suspend fun deleteRestaurantFromFavorites(remoteId: Long){
        favoritesRestaurantsDao.deleteFavoriteRestaurant(remoteId)
    }


}