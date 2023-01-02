package com.liordahan.justeattakeaway.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.liordahan.justeattakeaway.data.local.models.FavoritesRestaurants

@Dao
interface FavoritesRestaurantsDao {

    @Query("SELECT * FROM FavoritesRestaurants")
    suspend fun getFavoriteRestaurants(): List<FavoritesRestaurants>

    @Insert
    suspend fun addFavoriteRestaurant(favoritesRestaurants: FavoritesRestaurants)

    @Query("DELETE FROM FavoritesRestaurants WHERE favoriteRestaurantRemoteId = :remoteId")
    suspend fun deleteFavoriteRestaurant(remoteId: Long)
}