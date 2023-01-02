package com.liordahan.justeattakeaway.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/* I have decided to save only the RestaurantRemoteId because the mission is to save only the state
// if in any case i need to show entire screen of only favorite restaurants with only local memory
   i would have save the info i need for that screen from the remote object */

@Entity
data class FavoritesRestaurants(
    @PrimaryKey(autoGenerate = true) val _id: Int? = null,
    @ColumnInfo(name = "favoriteRestaurantRemoteId") val remoteId: Long
)