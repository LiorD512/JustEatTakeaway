package com.liordahan.justeattakeaway.data.remote.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class RestaurantResult(
    @Json(name = "restaurants") val result: List<Restaurant>? = null
)


@JsonClass(generateAdapter = true)
data class Restaurant(
    @Json(name = "id") val restaurantId: Long? = null,
    @Json(name = "coverImageUrl") val coverImage: String? = null,
    @Json(name = "name") val restaurantName: String? = null,
    @Json(name = "open") val isOpen: Boolean? = null,
    @Json(name = "minimumOrder") val minimumOrder: Int? = null,
    val isChecked: Boolean = false
)