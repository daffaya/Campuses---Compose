package com.kumaa.uni.ui.screen.favorite

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kumaa.uni.R
import com.kumaa.uni.model.UniList
import com.kumaa.uni.ui.common.StateHolder
import com.kumaa.uni.ui.components.NotFound
import com.kumaa.uni.ui.screen.home.ListUni
import com.kumaa.uni.ui.theme.UniTheme


@Composable
fun FavoriteScreen(
    navigateToProfile: () -> Unit,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    viewModel.stateHolder.collectAsState(initial = StateHolder.Loading).value.let { stateHolder ->
        when (stateHolder) {
            is StateHolder.Loading -> {
                viewModel.getFavoriteUni()
            }
            is StateHolder.Success -> {
                FavoriteContent(
                    listUni = stateHolder.data,
                    navigateToProfile = navigateToProfile,
                    navigateToDetail = navigateToDetail,
                    onFavoriteIconClicked = { id, newCountValue ->
                        viewModel.updateUni(id, newCountValue)
                    }
                )
            }
            is StateHolder.Error -> {}
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FavoriteContent(
    listUni: List<UniList>,
    navigateToProfile: () -> Unit,
    navigateToDetail: (Int) -> Unit,
    onFavoriteIconClicked: (id: Int, newCountValue: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopBarContent()
        },
        modifier = modifier
    ) {
        if (listUni.isNotEmpty()) {
            ListUni(
                listUni = listUni,
                onFavoriteIconClicked = onFavoriteIconClicked,
                contentPaddingTop = 16.dp,
                navigateToDetail = navigateToDetail
            )
        } else {
            NotFound(
                contentText = stringResource(R.string.empty_list)
            )
        }
    }
}

@Composable
fun TopBarContent(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.favorite_title))
        },
        backgroundColor = MaterialTheme.colors.surface,
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun FavoriteContentPreview() {
    UniTheme {
        FavoriteContent(
            listUni = emptyList(),
            navigateToProfile = {},
            navigateToDetail = {},
            onFavoriteIconClicked = { _, _ ->  }
        )
    }
}