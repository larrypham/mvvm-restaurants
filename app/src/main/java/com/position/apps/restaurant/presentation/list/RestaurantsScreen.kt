package com.position.apps.restaurant.presentation.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.position.apps.restaurant.domain.model.Restaurant

@Composable
fun RestaurantsScreen(
    state: RestaurantsScreenState,
    onItemClick: (id: String) -> Unit,
    onFavoriteClick: (id: String, oldValue: Boolean) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(
                vertical = 8.dp, horizontal = 8.dp
            )
        ) {
            items(state.restaurants) { restaurant ->
                RestaurantItem(restaurant, onFavoriteClick = { id, oldValue ->
                    onFavoriteClick(id, oldValue)
                }, onItemClick = { id ->
                    onItemClick(id)
                })
            }
        }
        if (state.isLoading) {
            CircularProgressIndicator()
        }
        if (state.error != null) {
            Text(text = state.error)
        }
    }
}

@Composable
fun RestaurantItem(
    item: Restaurant,
    onFavoriteClick: (id: String, oldValue: Boolean) -> Unit,
    onItemClick: (id: String) -> Unit
) {
    val icon = if (item.isFavorite) Icons.Filled.Favorite
    else Icons.Filled.FavoriteBorder

    Card(elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .padding(8.dp)
            .clickable { onItemClick(item.id) }) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)
        ) {
            RestaurantIcon(icon = Icons.Filled.Place, Modifier.weight(0.15f))
            RestaurantDetails(
                title = item.title,
                description = item.description,
                modifier = Modifier.weight(0.7f)
            )
            RestaurantIcon(icon = icon, modifier = Modifier.weight(0.15f)) {
                onFavoriteClick(item.id, item.isFavorite)
            }
        }
    }
}

@Composable
fun RestaurantIcon(icon: ImageVector, modifier: Modifier, onClick: () -> Unit = {}) {
    Image(
        imageVector = icon,
        contentDescription = "Restaurant Icon",
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }
    )
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun RestaurantDetails(
    title: String,
    description: String,
    modifier: Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
    Column(modifier = modifier, horizontalAlignment = horizontalAlignment) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.alpha(0.5f)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RestaurantsPreview() {
    RestaurantsScreen(
        state = RestaurantsScreenState(listOf(), true),
        onItemClick = {},
        onFavoriteClick = { _, _ -> }
    )
}