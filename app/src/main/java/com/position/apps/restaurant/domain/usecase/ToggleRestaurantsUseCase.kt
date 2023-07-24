package com.position.apps.restaurant.domain.usecase

import com.position.apps.restaurant.domain.model.Restaurant

fun interface ToggleRestaurantsUseCase {
    suspend operator fun invoke(id: String, oldValue: Boolean): List<Restaurant>
}