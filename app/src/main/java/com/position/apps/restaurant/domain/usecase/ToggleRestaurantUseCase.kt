package com.position.apps.restaurant.domain.usecase

import com.position.apps.restaurant.domain.model.Restaurant
import com.position.apps.restaurant.domain.repository.RestaurantRepository
import javax.inject.Inject

class ToggleRestaurantUseCase @Inject constructor(
    private val repository: RestaurantRepository,
    private val getSortedRestaurantsUseCase: GetSortedRestaurantsUseCase
) {
    suspend operator fun invoke(id: String, oldValue: Boolean): List<Restaurant> {
        val newFavorite = oldValue.not()
        repository.toggleFavoriteRestaurant(id, newFavorite)
        return getSortedRestaurantsUseCase()
    }
}