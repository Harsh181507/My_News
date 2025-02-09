package com.example.mynews.presentation.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import com.example.mynews.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.mynews.presentation.NewsAppViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.mynews.presentation.navigation.routes.CategoryScreen
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenUI(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: NewsAppViewModel
) {
    val scroolableState = rememberLazyListState()
    val searchTerm = rememberSaveable { mutableStateOf("") }
    val state = viewModel.state.collectAsState()
    val selectedCategory= rememberSaveable {mutableStateOf("")}
    val categoryToSearch = rememberSaveable {
        mutableStateOf(
            arrayListOf(
                "Business",
                "Entertainment",
                "General",
                "Health",
                "Science",
                "sports",
                "Technology"
            )
        )
    }
    if (state.value.loading == true) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            //CircularProgressIndicator()
            Indicator()
        }
    } else if (state.value.error.isNullOrBlank().not()) {
        Text(text = state.value.error.toString())
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            Color(0xFF000000)
            Row(
                modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(singleLine = true,
                    colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor =    Color(0xFF000000),
                        unfocusedBorderColor = Color.Gray
                    ),
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    shape = RoundedCornerShape(10.dp),
                    value = searchTerm.value, onValueChange = {
                        searchTerm.value = it
                    }, placeholder = { Text("Search") },
                    label = { Text("Search") },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            modifier = modifier.clickable(
                                enabled = searchTerm.value.isNullOrBlank().not(), onClick = {

                                    viewModel.getEveryThing(q = searchTerm.value)
                                })
                        )
                    })
            }
            Spacer(modifier = modifier.height(5.dp))
            LazyRow(
                modifier = modifier.fillMaxWidth(),
                state = scroolableState,
            ) {
                items(categoryToSearch.value) { category ->
                    val isSelected = category == selectedCategory.value
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) Color(0xFFFFFFFF) else Color(0xFF000000)
                        ),
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .clickable {
                                viewModel.getEveryThing(q = category)
                                selectedCategory.value = category
                            }
                    ) {
                        Text(
                            text = category,
                            fontSize = 15.sp,
                            color = if (isSelected) Color.Black else Color.White,  // Change text color based on selection
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }

            Spacer(modifier=modifier.padding(5.dp))
            val data = state.value.data
            if (data?.articles!!.isEmpty()) {
                Text(text = "No Data Found")
            } else {
                val articles = data.articles.filter { article ->
                    article.title?.contains("Removed") == false

                }

                LazyColumn(modifier = modifier.fillMaxSize()) {
                    items(articles) { article ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor =Color(0xFF000000)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(5.dp)
                                .clickable {
                                    navController.navigate(
                                        CategoryScreen(
                                            author = article.author,
                                            content = article.content,
                                            description = article.description,
                                            publishedAt = article.publishedAt,
                                            id = article.source!!.id,
                                            name = article.source.name,
                                            title = article.title,
                                            url = article.url,
                                            urlToImage = article.urlToImage
                                        )
                                    )
                                }
                        ) {

                            Column {
                                if (article.urlToImage.isNullOrBlank()) {
                                    Indicator()

                                } else {
                                    AsyncImage(
                                        model = article.urlToImage,
                                        contentDescription = null,
                                        placeholder = painterResource(R.drawable.loddddd),
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                                article.title?.let { Text(text = it, color = Color.White, fontSize =15.sp, modifier = modifier.padding(10.dp)) }
                            }

                        }
                    }
                }
            }

        }
    }

}

@Composable
fun Indicator(
    size: Dp = 32.dp, // indicator size
    sweepAngle: Float = 90f, // angle (lenght) of indicator arc
    color: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.primary, // color of indicator arc line
    strokeWidth: Dp = ProgressIndicatorDefaults.CircularStrokeWidth //width of cicle and ar lines
) {
    ////// animation //////

    // docs recomend use transition animation for infinite loops
    // https://developer.android.com/jetpack/compose/animation
    val transition = rememberInfiniteTransition()

    // define the changing value from 0 to 360.
    // This is the angle of the beginning of indicator arc
    // this value will change over time from 0 to 360 and repeat indefinitely.
    // it changes starting position of the indicator arc and the animation is obtained
    val currentArcStartAngle by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1100,
                easing = LinearEasing
            )
        )
    )


    ////// draw /////

    // define stroke with given width and arc ends type considering device DPI
    val stroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Square)
    }

    // draw on canvas
    Canvas(
        Modifier
            .progressSemantics() // (optional) for Accessibility services
            .size(size) // canvas size
            .padding(strokeWidth / 2) //padding. otherwise, not the whole circle will fit in the canvas
    ) {
        // draw "background" (gray) circle with defined stroke.
        // without explicit center and radius it fit canvas bounds
        drawCircle(Color.LightGray, style = stroke)

        // draw arc with the same stroke
        drawArc(
            color,
            // arc start angle
            // -90 shifts the start position towards the y-axis
            startAngle = currentArcStartAngle.toFloat() - 90,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = stroke
        )
    }
}

