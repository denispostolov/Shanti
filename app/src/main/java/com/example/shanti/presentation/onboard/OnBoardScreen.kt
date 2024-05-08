package com.example.shanti.presentation.onboard
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.shanti.components.SimpleOnboardBottomSection
import com.example.shanti.components.SimplePagerIndicator
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState


@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardScreen(
   rootNavHostController: NavHostController,
){
    val items = ArrayList<OnBoardingData>()

    items.add(
        OnBoardingData(
            url = "https://lottie.host/56b1b0b9-5052-40f3-a4fc-f66a68c4c083/WqMQ6UdoKN.json",
            "Meditation is your way",
            ""
        )
    )

    items.add(
        OnBoardingData(
            url = "https://lottie.host/3be6ea4a-1fa6-4313-a601-008bad4220cb/HEdpwg9lfI.json",
            "Yoga is power",
            ""
        )
    )

    items.add(
        OnBoardingData(
            url = "https://lottie.host/75718fc3-ffb1-48a5-aabf-d5b4f74fa9be/YDANP9jBT3.json",
            "Take a breath",
            ""
        )
    )

    val pagerState = rememberPagerState(
        pageCount = items.size,
        initialOffscreenLimit = 2,
        infiniteLoop = false,
        initialPage = 0
    )

    OnBoardingPager(
        item = items,
        pagerState = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White),
        rootNavHostController = rootNavHostController,
    )
}

@ExperimentalPagerApi
@Composable
fun OnBoardingPager(
    item: List<OnBoardingData>,
    pagerState: PagerState,
    modifier: Modifier,
    rootNavHostController: NavHostController
) {
    Box(modifier = modifier.fillMaxHeight()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                state = pagerState
            ) { page ->
                Column(
                    modifier = Modifier
                        .padding(60.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LoaderIntro(
                        modifier = Modifier
                            .size(200.dp)
                            .fillMaxWidth()
                            .align(alignment = Alignment.CenterHorizontally), item[page].url
                    )
                    Text(
                        text = item[page].title,
                        modifier = Modifier.padding(top = 50.dp),
                        color = Color.Black,
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = item[page].desc,
                        modifier = Modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp),
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
            SimplePagerIndicator(item.size, pagerState.currentPage)
        }
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            SimpleOnboardBottomSection(
                pagerState = pagerState,
                rootNavHostController = rootNavHostController,
            )
        }
    }
}
@ExperimentalPagerApi
@Composable
fun rememberPagerState(
    pageCount: Int,
    initialPage: Int = 0,
    initialPageOffset: Float = 0f,
    initialOffscreenLimit: Int = 1,
    infiniteLoop: Boolean = false
): PagerState = rememberSaveable(
    saver = PagerState.Saver
) {
    PagerState(
        pageCount = pageCount,
        currentPage = initialPage,
        currentPageOffset = initialPageOffset,
        offscreenLimit = initialOffscreenLimit,
        infiniteLoop = infiniteLoop
    )
}


