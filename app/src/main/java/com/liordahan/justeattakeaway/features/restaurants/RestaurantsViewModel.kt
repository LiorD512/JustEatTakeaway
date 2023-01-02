package com.liordahan.justeattakeaway.features.restaurants

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liordahan.justeattakeaway.data.local.models.FavoritesRestaurants
import com.liordahan.justeattakeaway.data.remote.models.Restaurant
import com.liordahan.justeattakeaway.data.repositories.RestaurantRepository
import com.liordahan.justeattakeaway.extensions.updateListItem
import com.skydoves.sandwich.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RestaurantsState(
    val restaurants: List<Restaurant>? = emptyList(),
    val inProgress: Boolean = false
)

sealed class RestaurantsEvents {
    object Error : RestaurantsEvents()
}

@HiltViewModel
class RestaurantsViewModel @Inject constructor(
    private val restaurantRepository: RestaurantRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RestaurantsState())
    val state: StateFlow<RestaurantsState> = _state

    private val _events = MutableSharedFlow<RestaurantsEvents>()
    val events: SharedFlow<RestaurantsEvents> = _events

    init {
        getAllRestaurants()
    }

    private fun getAllRestaurants() {
        setInProgress(true)
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = restaurantRepository.getAllRestaurants()) {
                is ApiResponse.Failure.Error -> {
                    _events.emit(RestaurantsEvents.Error)
                }
                is ApiResponse.Failure.Exception -> {
                    _events.emit(RestaurantsEvents.Error)
                }
                is ApiResponse.Success -> {
                    val favoritesRestaurants = restaurantRepository.getFavoriteRestaurants()
                    setRestaurants(
                        result.data.result?.updateFavoritesInRestaurantList(
                            favoritesRestaurants.map { it.remoteId })
                    )
                }
            }

            setInProgress(false)
        }
    }


    private fun setRestaurants(restaurants: List<Restaurant>?) {
        _state.update {
            it.copy(restaurants = restaurants)
        }
    }

    private fun setInProgress(inProgress: Boolean) {
        _state.update {
            it.copy(inProgress = inProgress)
        }
    }

    fun onFavoriteClicked(isChecked: Boolean, restaurantId: Long) {
        viewModelScope.launch {
            if (isChecked) {
                restaurantRepository.addRestaurantToFavorites(FavoritesRestaurants(remoteId = restaurantId))
            } else {
                restaurantRepository.deleteRestaurantFromFavorites(remoteId = restaurantId)
            }

            _state.update {
                it.copy(
                    restaurants = updateRestaurantsWithSelected(
                        _state.value.restaurants,
                        restaurantId,
                        isChecked
                    )
                )
            }
        }
    }

    private fun updateRestaurantsWithSelected(
        restaurants: List<Restaurant>?,
        restaurantId: Long,
        isChecked: Boolean
    ): List<Restaurant>? {
        return restaurants?.updateListItem(where = { restaurant -> restaurant.restaurantId == restaurantId }) {
            it.copy(isChecked = isChecked)
        }
    }

    private fun List<Restaurant>.updateFavoritesInRestaurantList(favoritesRestaurantsId: List<Long>): List<Restaurant> {
        return this.updateListItem(where = { restaurant -> restaurant.restaurantId in favoritesRestaurantsId }) {
            it.copy(isChecked = true)
        }
    }
}