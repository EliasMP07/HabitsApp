@file:Suppress("DEPRECATION")

package com.devdroid.habitsapp.onboarding.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.devdroid.habitsapp.R
import com.devdroid.habitsapp.core.presentation.HabitButton
import com.devdroid.habitsapp.core.presentation.HabitTitle
import com.devdroid.habitsapp.onboarding.presentation.OnBoardingPagerInformation
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingPager(
    pages: List<OnBoardingPagerInformation>,
    onFinish: () -> Unit,
    modifier: Modifier = Modifier

) {
    val pagerState = rememberPagerState()
    val corountineScope = rememberCoroutineScope()

    Box(
        modifier = modifier,

    ) {
        HorizontalPager(count = pages.size, state = pagerState) { index ->
            val information = pages[index]
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                HabitTitle(title = stringResource(id = information.title))
                Spacer(modifier = Modifier.height(32.dp))
                Image(
                    modifier = Modifier.aspectRatio(1f),
                    painter = painterResource(id = information.image),
                    contentDescription = "onboarding",
                    contentScale = ContentScale.FillHeight
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = stringResource(id = information.subtitle).uppercase(),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiary
                    ),
                    textAlign = TextAlign.Center
                )

            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (pagerState.currentPage == pages.lastIndex) {
                HabitButton(text = "Get Started", modifier = Modifier.fillMaxWidth()) {
                    onFinish()
                }

            } else {
                TextButton(
                    onClick = {
                        onFinish()
                    }) {
                    Text(
                        text = "Skip",
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
                HorizontalPagerIndicator(
                    activeColor = MaterialTheme.colorScheme.tertiary,
                    inactiveColor = MaterialTheme.colorScheme.primary,
                    pagerState = pagerState
                )
                TextButton(
                    onClick = {
                        corountineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                ) {
                    Text(
                        text = "Next",
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
    }

}