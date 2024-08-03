package com.techsavvy.ubs.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.techsavvy.ubs.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

@Composable
fun DashboardScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        val drawerState = remember { mutableStateOf(DrawerValue.Closed) }
        var rotate by remember { mutableStateOf(0f) }

        if (drawerState.value == DrawerValue.Closed) {
            Row {
                AnimatedVisibility(visible = drawerState.value == DrawerValue.Closed) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RectangleShape,
                        elevation = CardDefaults.cardElevation(8.dp),
                        colors = CardDefaults.cardColors(Color.White)
                    ) {

                        Row(
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "UBS",
                                fontFamily = FontFamily.Serif,
                                color = Color.Red,
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(5.dp)
                            )
                            IconButton(onClick = {
                                drawerState.value =
                                    if (drawerState.value == DrawerValue.Open) DrawerValue.Closed else DrawerValue.Open

                            }) {
                                Icon(
                                    Icons.Default.Menu,
                                    "",
                                    modifier = Modifier.rotate(rotate)
                                )
                            }
                        }
                    }
                }
            }
        } else {
            Row {
                AnimatedVisibility(visible = drawerState.value == DrawerValue.Open) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RectangleShape,
                        elevation = CardDefaults.cardElevation(8.dp),
                        colors = CardDefaults.cardColors(Color.White)
                    ) {

                        Row(
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            IconButton(onClick = {
                                drawerState.value =
                                    if (drawerState.value == DrawerValue.Open) DrawerValue.Closed else DrawerValue.Open

                            }) {
                                Icon(
                                    Icons.Default.Menu,
                                    "",
                                )
                            }
                            Column() {
                                Row {
                                    Text(
                                        buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    fontSize = 20.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            ) {
                                                append("4860.14")
                                            }
                                            withStyle(
                                                style = SpanStyle(
                                                    fontSize = 12.sp
                                                )
                                            ) {
                                                append("CHF")
                                            }
                                        },

                                        )
                                    Icon(
                                        Icons.Default.KeyboardArrowRight,
                                        ""
                                    )
                                }
                                Text(
                                    "Limit TWINT actuelle",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            val parentWidth = LocalConfiguration.current.screenWidthDp.dp
            val parentHeight = LocalConfiguration.current.screenHeightDp.dp
            Box {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    MainContent(navController)
                }
                LaunchedEffect(key1 = drawerState.value) {
                    if (drawerState.value == DrawerValue.Open) {
                        yield()
                        delay(10)
                        while (rotate != 90f) {
                            rotate += 5f
                        }
                    } else {
                        yield()
                        delay(10)
                        while (rotate != 0f) {
                            rotate -= 5f
                        }
                    }
                }
                if (drawerState.value == DrawerValue.Open) {
                    Box(
                        modifier = Modifier
                            .size(parentWidth, height = parentHeight)
                            .offset(x = parentWidth / 4)
                    ) {
                        SidePanel()
                    }
                }
            }
        }

    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun MainContent(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {

        val list = listOf(
            SliderObj(
                "Abc Hello World ",
                "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg"
            ),
            SliderObj(
                "Hii hd aljd aal aej sf",
                "https://media.springernature.com/lw703/springer-static/image/art%3A10.1038%2F528452a/MediaObjects/41586_2015_Article_BF528452a_Figg_HTML.jpg"
            ),
            SliderObj(
                "Hii hd aljd aal aej sf",
                "https://media.springernature.com/lw703/springer-static/image/art%3A10.1038%2F528452a/MediaObjects/41586_2015_Article_BF528452a_Figg_HTML.jpg"
            ),
            SliderObj(
                "Hii hd aljd aal aej sf",
                "https://media.springernature.com/lw703/springer-static/image/art%3A10.1038%2F528452a/MediaObjects/41586_2015_Article_BF528452a_Figg_HTML.jpg"
            ),
            SliderObj(
                "Hii hd aljd aal aej sf",
                "https://media.springernature.com/lw703/springer-static/image/art%3A10.1038%2F528452a/MediaObjects/41586_2015_Article_BF528452a_Figg_HTML.jpg"
            )
        )
        val state = rememberPagerState(0)

//        LaunchedEffect(Unit) {
//            while (true) {
//                yield()
//                delay(2600)
//                state.animateScrollToPage(
//                    page = (state.currentPage + 1) % (state.pageCount)
//                )
//            }
//        }


        val pagerState = androidx.compose.foundation.pager.rememberPagerState {
            list.size
        }
        androidx.compose.foundation.pager.HorizontalPager(
            state = pagerState,
            pageSize = PageSize.Fixed(280.dp)
        ) { page ->
            Card(
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .padding(10.dp),
                elevation = CardDefaults.cardElevation(1.dp),
                colors = CardDefaults.cardColors(Color.White)
            ) {
                AsyncImage(
                    list[page].url,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    placeholder = painterResource(id = R.drawable.ic_launcher_foreground)
                )
                Text(
                    list[page].title,
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .padding(top = 3.dp),
                    style = MaterialTheme.typography.titleSmall,

                    )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 5.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Card(
                        onClick = { /*TODO*/ },
                        border = BorderStroke(1.dp, Color.Gray),
                        elevation = CardDefaults.cardElevation(1.dp),
                        colors = CardDefaults.cardColors(Color.White),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            "Activate",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }

        HorizontalPagerIndicator(
            pagerState = PagerState(pagerState.currentPage),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp),
            pageCount = pagerState.pageCount
        )

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 5.dp)
        ) {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp),
                elevation = CardDefaults.cardElevation(1.dp),
                colors = CardDefaults.cardColors(Color.White),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_qr),
                        ""
                    )
                    Text(
                        "Bone\nabbkbks",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
            Spacer(Modifier.width(10.dp))
            Card(
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp),
                elevation = CardDefaults.cardElevation(1.dp),
                colors = CardDefaults.cardColors(Color.White),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_qr),
                        ""
                    )
                    Text(
                        "Bone\nabbkbks",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
            Spacer(Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .weight(.7f)) {
                Card(
                    modifier = Modifier
                        .padding(5.dp),
                    elevation = CardDefaults.cardElevation(1.dp),
                    colors = CardDefaults.cardColors(Color.White),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Icon(
                            Icons.Default.MoreVert,
                            "",
                            modifier = Modifier.rotate(90f)
                        )
                        Text(
                            "Plus",
                            style = MaterialTheme.typography.titleSmall,
                            minLines = 2
                        )
                    }
                }
                Icon(
                    Icons.Default.Add,
                    "",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(24.dp)
                        .background(Color.LightGray, CircleShape)
                        .padding(3.dp)
                )
            }
            Spacer(Modifier.width(10.dp))

        }
        Spacer(Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(                )
                    .align(Alignment.Center)
                    .padding(bottom = 80.dp)
            ) {
                Column(
                    modifier= Modifier.weight(1f)
                        .padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Box(
                        modifier= Modifier
                            .size(70.dp)
                            .border(1.dp, Color.Black, CircleShape),
                        contentAlignment = Alignment.Center
                    ){
                        Icon(
                            Icons.Default.ArrowDropDown,
                            ""
                        )
                    }
                    Text(text = "Bone\nAdda",
                        style = MaterialTheme.typography.titleSmall)
                }
                Spacer(Modifier.width(10.dp))
                Column(
                    modifier= Modifier.weight(1f)
                        .padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Box(
                        modifier= Modifier
                            .size(70.dp)
                            .border(1.dp, Color.Black, CircleShape),
                        contentAlignment = Alignment.Center
                    ){
                        Icon(
                            Icons.Default.KeyboardArrowUp,
                            ""
                        )
                    }
                    Text(text = "Bone\nRmeo",
                        style = MaterialTheme.typography.titleSmall)
                }

            }
            Button(
                onClick = {

                },
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(10.dp)
                    .padding(bottom = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    Color(0xFF6E6E6E)
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_qr),
                    "",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(5.dp)
                        .size(24.dp)
                )


                Text(
                    "Payer",
                    modifier = Modifier.padding(horizontal = 5.dp),
                    fontWeight = FontWeight.Bold
                )

            }
        }



    }
}

@Composable
fun SidePanel(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        val list = listOf(
            Pair(
                "Home",
                R.drawable.ic_launcher_foreground
            ),
            Pair(
                "Transaction",
                R.drawable.ic_launcher_foreground
            ),
            Pair(
                "Cartes Client",
                R.drawable.ic_launcher_foreground
            ),
            Pair(
                "Payer plus tard",
                R.drawable.ic_launcher_foreground
            )
        )
        list.forEach {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    }
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = it.second),
                    contentDescription = "",
                    modifier = Modifier.size(30.dp)
                )
                Text(
                    it.first,
                    modifier = Modifier.padding(horizontal = 5.dp),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }


    }
}


data class SliderObj(
    val title: String,
    val url: String
)