package com.position.apps.restaurant.domain.usecase

import com.position.apps.restaurant.domain.model.Restaurant

fun interface GetSortedRestaurantsUseCase: suspend () -> List<Restaurant>
