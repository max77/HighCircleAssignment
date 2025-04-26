package com.max77.tmdbsample.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.max77.tmdbsample.ui.theme.Typography


@Composable
fun RatingWidget(
    modifier: Modifier = Modifier,
    ratingPercentage: Int?
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text(
            text = ratingPercentage?.let { "$it%" } ?: "?",
            style = Typography.labelSmall
        )
        ratingPercentage?.let { CircularProgressIndicator(progress = { it / 100f }) }
    }
}
