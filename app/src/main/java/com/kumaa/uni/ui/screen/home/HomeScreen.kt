package com.kumaa.uni.ui.screen.home

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kumaa.uni.R
import com.kumaa.uni.model.UniList
import com.kumaa.uni.ui.components.NotFound
import com.kumaa.uni.ui.components.SearchView
import com.kumaa.uni.ui.components.UniItem
import com.kumaa.uni.ui.theme.UniTheme
import com.kumaa.uni.ui.common.StateHolder


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val query by viewModel.query
    viewModel.stateHolder.collectAsState(initial = StateHolder.Loading).value.let { stateHolder ->
        when (stateHolder) {
            is StateHolder.Loading -> {
                viewModel.search(query)
            }
            is StateHolder.Success -> {
                HomeContent(
                    query = query,
                    onQueryChange = viewModel::search,
                    listUni = stateHolder.data,
                    onFavoriteIconClicked = { id, newState ->
                        Log.d("HomeContent", "Favorite icon clicked for id: $id, newState: $newState")
                        viewModel.updateUni(id, newState)
                    },
                    navigateToDetail = navigateToDetail
                )
            }
            is StateHolder.Error -> {}
        }
    }
}


@Composable
fun HomeContent(
    query: String,
    onQueryChange: (String) -> Unit,
    listUni: List<UniList>,
    onFavoriteIconClicked: (id: Int, newCountValue: Boolean) -> Unit,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column {
        SearchView(
            query = query,
            onQueryChange = onQueryChange
        )
        if (listUni.isNotEmpty()) {
            ListUni(
                listUni = listUni,
                onFavoriteIconClicked = onFavoriteIconClicked,
                navigateToDetail = navigateToDetail
            )
        } else {
            NotFound(
                contentText = stringResource(R.string.empty_list),
                modifier = Modifier
                    .testTag("empty_data")
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListUni(
    listUni: List<UniList>,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPaddingTop: Dp = 0.dp,
) {
    LazyColumn(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp, top = contentPaddingTop),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .testTag("lazy_list")
    ) {
        items(listUni, key = { it.id }) { item ->
            UniItem(
                id = item.id,
                img = item.img,
                name = item.name,
                location = item.location,
                isFavorite = item.isFavorite,
                onFavoriteIconClicked = onFavoriteIconClicked,
                modifier = Modifier
                    .animateItemPlacement(tween(durationMillis = 200))
                    .clickable { navigateToDetail(item.id) }
            )
        }
    }
}




@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {
    UniTheme {
        HomeContent(
            query = "",
            onQueryChange = {},
            listUni = emptyList(),
            onFavoriteIconClicked = { _, _ ->  },
            navigateToDetail = {}
        )
    }
}