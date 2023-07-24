package com.position.apps.restaurant.presentation.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.position.apps.restaurant.data.remote.RestaurantsApiService
import com.position.apps.restaurant.di.RestaurantsModule
import com.position.apps.restaurant.domain.model.Restaurant
import com.position.apps.restaurant.util.K
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RestaurantDetailsViewModel @Inject constructor (private val stateHandle: SavedStateHandle): ViewModel() {
    private var restInterface: RestaurantsApiService
    val state = mutableStateOf<Restaurant?>(null)

    init {
        val retrofit = RestaurantsModule.provideRetrofit()
        restInterface = RestaurantsModule.provideRetrofitApi(retrofit)

        val id = stateHandle.get<String>("id") ?: K.EMPTY_VALUE
        viewModelScope.launch {
            val restaurant = getRemoteRestaurant(id)
            state.value = restaurant
        }
    }

    private suspend fun getRemoteRestaurant(id: String): Restaurant {
        return withContext(Dispatchers.IO) {
            val response = restInterface.getRestaurant(id)
            return@withContext response.let {
                Restaurant(id = it.id, title = it.title, description = it.description)
            }
        }
    }
}