package com.liordahan.justeattakeaway.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.liordahan.justeattakeaway.data.local.database.dao.FavoritesRestaurantsDao
import com.liordahan.justeattakeaway.data.local.models.FavoritesRestaurants

@Database(entities = [FavoritesRestaurants::class], version = 1)
abstract class JustEatTakeawayDatabase: RoomDatabase() {
    abstract fun favoriteRestaurantsDao(): FavoritesRestaurantsDao
}