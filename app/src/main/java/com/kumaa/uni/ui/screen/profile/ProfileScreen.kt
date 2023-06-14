package com.kumaa.uni.ui.screen.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kumaa.uni.R
import com.kumaa.uni.ui.theme.UniTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    navigateToFavorite: () -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopBarContent()
        },
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(24.dp)
                    .align(Alignment.Center)
            ) {
                Box(
                    modifier = Modifier
                        .size(198.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.profile_picture),
                        contentDescription = "profile_picture",
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
                Text(
                    text = stringResource(R.string.name),
                    color = Color.Black,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = stringResource(R.string.email),
                    color = Color.Black
                )
            }
        }
    }
}


@Composable
fun TopBarContent(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.profile_title))
        },
        backgroundColor = MaterialTheme.colors.surface,
        modifier = modifier,
    )
}


@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    UniTheme {
        ProfileScreen(
            navigateToFavorite = {},
            navigateToHome = {}
        )
    }
}