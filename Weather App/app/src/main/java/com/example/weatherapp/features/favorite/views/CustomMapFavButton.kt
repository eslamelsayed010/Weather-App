package com.example.weatherapp.features.favorite.views

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.weatherapp.R
import com.example.weatherapp.core.AppColors
import com.example.weatherapp.features.favorite.model.FavoriteModel
import com.example.weatherapp.features.favorite.viewmodel.FavoriteViewModel
import com.google.android.gms.maps.model.LatLng

@Composable
fun CustomMapFavButton(
    favoriteViewModel: FavoriteViewModel,
    navController: NavHostController,
    selectedPosition: MutableState<LatLng>,
    city: String,
    country: String
) {
    val context = LocalContext.current

    Button(
        onClick = {
            favoriteViewModel.insertToFavorite(
                FavoriteModel(
                    city = city,
                    country = country,
                    lat = selectedPosition.value.latitude,
                    lon = selectedPosition.value.longitude
                )
            )
            Toast.makeText(context, context.getString(R.string.DONE), Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = AppColors.PrimaryColor,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 2.dp,
            focusedElevation = 4.dp
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Icon(
            imageVector = Icons.Rounded.LocationOn,
            contentDescription = "Set Location",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "${stringResource(R.string.select_location)} $country $city".take(35),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}