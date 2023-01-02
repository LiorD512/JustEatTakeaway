package com.liordahan.justeattakeaway.data.remote

import com.liordahan.justeattakeaway.data.remote.models.RestaurantResult
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface RestaurantApiService {

    @GET("10bis/10bis-mobile-home-assignment/db")
    suspend fun getAllRestaurants(): ApiResponse<RestaurantResult>

}