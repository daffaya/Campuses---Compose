package com.kumaa.uni

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kumaa.uni.ui.navigation.Screen
import com.kumaa.uni.ui.screen.detail.DetailScreen
import com.kumaa.uni.ui.screen.favorite.FavoriteScreen
import com.kumaa.uni.ui.screen.home.HomeScreen
import com.kumaa.uni.ui.screen.profile.ProfileScreen
import com.kumaa.uni.ui.theme.UniTheme
import com.kumaa.uni.ui.navigation.NavigationItem


@Composable
fun UniApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        Scaffold(
            bottomBar = {
                if (currentRoute != Screen.Detail.route) {
                    BottomBar(navController)
                }
            },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { uniId ->
                        navController.navigate(Screen.Detail.createRoute(uniId))
                    }
                )
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(
                    navigateToProfile = {
                        navController.navigate(Screen.Profile.route)
                    },
                    navigateToDetail = { uniId ->
                        navController.navigate(Screen.Detail.createRoute(uniId))
                    }
                )
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(
                    navArgument("uniId") { type = NavType.IntType }
                )
            ) {
                val id = it.arguments?.getInt("uniId") ?: -1
                DetailScreen(
                    uniId = id,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    navigateToFavorite = {
                        navController.navigate(Screen.Favorite.route)
                    },
                    navigateToHome = {
                        navController.navigate(Screen.Home.route)
                    }
                )
            }
        }
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    BottomNavigation (
        backgroundColor = Color.LightGray,
        modifier = modifier
            .shadow(48.dp)
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.home_title),
                icon = Icons.Rounded.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.favorite_title),
                icon = Icons.Rounded.FavoriteBorder,
                screen = Screen.Favorite
            ),
            NavigationItem(
                title = stringResource(R.string.profile_title),
                icon = Icons.Rounded.Person,
                screen = Screen.Profile
            ),
        )
        BottomNavigation(
            backgroundColor = Color.White,
        ) {
            navigationItems.map { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                    label = { Text(item.title) },
                    selected = currentRoute == item.screen.route,
                    selectedContentColor = MaterialTheme.colors.primaryVariant,
                    unselectedContentColor = Color.Gray,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UniAppPreview() {
    UniTheme {
        UniApp()
    }
}