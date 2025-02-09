package com.example.mynews.presentation.screens
import android.R.attr.shape
import androidx.compose.foundation.border
import com.example.mynews.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.mynews.data.model.Article
import com.example.mynews.data.model.Source

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDetailScreenUI(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    article: Article = Article(
        source = Source(id = "the-washington-post", name = "The Washington Post"),
        author = "Maxine Joselow, Nicolás Rivero",
        title = "In the Trump era, renewable energy isn’t green — it’s ‘dominant’ - The Washington Post",
        description = "Clean-energy executives are tailoring their pitches for President Donald Trump and his political allies.",
        url = "https://www.washingtonpost.com/climate-environment/2025/02/05/solar-clean-energy-dominance-trump/",
        urlToImage = "https://www.washingtonpost.com/wp-apps/imrs.php?src=https://arc-anglerfish-washpost-prod-washpost.s3.amazonaws.com/public/R5GCJIYYXKW73LGLWCHYHIJFBI.JPG&w=1440",
        publishedAt = "2025-02-06T07:16:17Z",
        content = "More than 160 solar energy executives converged Wednesday on Capitol Hill for what the clean-energy industry described as its largest-ever lobbying blitz. And they tailored their pitch for a new audi… [+8444 chars]"
    )
) {

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(modifier=modifier.height(80.dp),
                title = { Text(text = "News App", color = Color.White)},
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(Icons.Default.Close, contentDescription = "Back",modifier=modifier.padding(bottom = 17.dp).height(30.dp), tint =Color(
                            0xFFFFFFFF
                        )
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF000000)),
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = article.title ?: "No Title",
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    color = Color(0xFF000000),
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    lineHeight = 24.sp,
                    letterSpacing = 2.sp
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            AsyncImage(
                model = article.urlToImage,
                contentDescription = null,
                modifier = Modifier.height(230.dp).padding(3.dp).border(
                    width = 3.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(10.dp)
                ),
            )
            Spacer(modifier=modifier.height(20.dp))

            Text(
                text = article.description ?: "No Title",
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleSmall.fontSize.times(1.5f),
                    color = Color(0xFF000000),
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Default,
                    lineHeight = 24.sp,
                    letterSpacing = 0.sp,


                )
            )


            Spacer(modifier=modifier.height(20.dp))
            Text(
                text = article.content ?: "No Title",
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleSmall.fontSize.times(1.5f),
                    color = Color(0xFF000000),
                    fontWeight = FontWeight.Light,
                    fontFamily = FontFamily.Default,
                    lineHeight = 24.sp,
                    letterSpacing = 0.sp,
                )
            )



        }
    }
}
