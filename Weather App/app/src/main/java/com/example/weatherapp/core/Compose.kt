package com.example.weatherapp.core

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.weatherapp.features.home.viewmodel.HomeViewModel

@Composable
fun CustomAnmiLoading() {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("loading.json"))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(150.dp),
            contentScale = ContentScale.FillBounds
        )
    }
}

@Composable
fun CustomError(viewModel: HomeViewModel) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "There is an error ${viewModel.toastEvent.collectAsState(String()).value}",
            color = Color.Red,
            fontSize = 30.sp
        )
    }
}

@Composable
fun DashedDivider() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(4f.dp)
    ) {
        val width = size.width
        var startX = 0f
        while (startX < width) {
            drawLine(
                color = AppColors.Gray300,
                start = Offset(startX, 0f),
                end = Offset(startX + 40f, 0f),
                strokeWidth = 4f
            )
            startX += 40f + 20f
        }
    }
}

@Composable
fun LottieBackgroundBox(
    lottieResId: String = "rain_bk.json",
    content: @Composable BoxScope.() -> Unit
) {

    val composition by rememberLottieComposition(LottieCompositionSpec.Asset(lottieResId))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        content()
    }
}

@Composable
fun CircularAvatar(@DrawableRes imageRes: Int) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .border(width = 2.dp, color = Color.White, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "contentDescription",
            modifier = Modifier.size(40.dp),
            colorFilter = ColorFilter.tint(AppColors.IconCat)
        )
    }

}

@Composable
fun CustomSettingsTitle(
    title: String,
    @DrawableRes imageRes: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "contentDescription",
            modifier = Modifier.size(40.dp),
            colorFilter = ColorFilter.tint(AppColors.IconCat)
        )
        Spacer(Modifier.width(5.dp))
        Text(
            title,
            color = AppColors.IconCat,
            fontSize = 30.sp
        )
    }
}

@Composable
fun CustomForecastDivider(txt: String) {
    Text(
        txt,
        fontSize = 25.sp,
        color = AppColors.Gray300
    )
    Spacer(Modifier.height(10.dp))
    DashedDivider()
    Spacer(Modifier.height(15.dp))
}

@Composable
fun CustomSettingsDivider() {
    Spacer(Modifier.height(8.dp))
    HorizontalDivider(color = Color.LightGray)
    Spacer(Modifier.height(15.dp))
}