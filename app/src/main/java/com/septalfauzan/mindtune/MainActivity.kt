package com.septalfauzan.mindtune

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.septalfauzan.mindtune.data.repositories.AuthRepository
import com.septalfauzan.mindtune.data.repositories.SongsRepository
import com.septalfauzan.mindtune.data.repositories.UserRepository
import com.septalfauzan.mindtune.data.ui.MentalHealth
import com.septalfauzan.mindtune.data.viewmodel.*
import com.septalfauzan.mindtune.di.Injection
import com.septalfauzan.mindtune.helpers.RecommendationType
import com.septalfauzan.mindtune.helpers.Screen
import com.septalfauzan.mindtune.ui.common.ScreenArguments
import com.septalfauzan.mindtune.ui.common.UiState
import com.septalfauzan.mindtune.ui.theme.MindTuneTheme
import com.septalfauzan.mindtune.ui.views.auth.AuthScreen
import com.septalfauzan.mindtune.ui.views.components.MentalHealthTypes
import com.septalfauzan.mindtune.ui.views.home.HomeScreen
import com.septalfauzan.mindtune.ui.views.profile.ProfileScreen
import com.septalfauzan.mindtune.ui.views.recommendation.RecommendationScreen
import com.septalfauzan.mindtune.ui.views.songs.DetailSongScreen
import com.spotify.sdk.android.auth.AuthorizationResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class MainActivity : ComponentActivity() {
    private lateinit var authViewModel: AuthViewModel
    lateinit var navHostController: NavHostController

    companion object {
        const val REQUEST_CODE = 1337
        const val REDIRECT_URI = "com.septalfauzan.mindtune://callback"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashViewModel =
            ViewModelFactory(Injection.provideInjection(this) as AuthRepository).create(
                SplashViewModel::class.java
            )
        val userViewModel =
            ViewModelFactory(Injection.provideInjection(this) as UserRepository).create(
                UserViewModel::class.java
            )
        val songViewModel =
            ViewModelFactory(Injection.provideInjection(this) as SongsRepository).create(
                SongViewModel::class.java
            )
        authViewModel =
            ViewModelFactory(Injection.provideInjection(this) as AuthRepository).create(
                AuthViewModel::class.java
            )

        super.onCreate(savedInstanceState)

        installSplashScreen().setKeepOnScreenCondition {
            splashViewModel.isLoading.value
        }

        setContent {
            MindTuneTheme {
                val isUserLogged by authViewModel.isLogged.collectAsState()
                navHostController = rememberNavController()
                val currentRoute =
                    navHostController.currentBackStackEntryAsState().value?.destination?.route

                val context = LocalContext.current

                //for screen arguments
                val homeArguments = ScreenArguments.HomeArguments(
                    itemList = listOf(
                        MentalHealth(1, MentalHealthTypes.DEPRESSION),
                        MentalHealth(10, MentalHealthTypes.ANXIETY),
                        MentalHealth(8, MentalHealthTypes.INSOMNIA),
                    ),
                    openUserProfile = { navHostController.navigate(Screen.Profile.route) },
                    openRecommendations = mapOf(
                        MentalHealthTypes.DEPRESSION to {
                            navHostController.navigate(
                                Screen.Recommendation.createRoute(RecommendationType.DEPRESSION)
                            )
                        },
                        MentalHealthTypes.ANXIETY to {
                            navHostController.navigate(
                                Screen.Recommendation.createRoute(RecommendationType.ANXIETY)
                            )
                        },
                        MentalHealthTypes.INSOMNIA to {
                            navHostController.navigate(
                                Screen.Recommendation.createRoute(RecommendationType.INSOMNIA)
                            )
                        },
                    ),
                    loadTopTracks = { songViewModel.getTopTrack() },
                    topTrackStateFlow = songViewModel.topTracks,
                    loadTopArtist = { songViewModel.getTopArtist() },
                    topArtistStateFlow = songViewModel.topArtist
                )

                val authArguments = ScreenArguments.AuthArguments(
                    spotifyAuth = { activity, requestCode, redirectUri ->
                        authViewModel.spotifyAuth(
                            activity,
                            requestCode,
                            redirectUri
                        )
                    }
                )

                val profileArguments = ScreenArguments.ProfileArguments(
                    logoutAction = {
                        authViewModel.spotifyLogout(context, openSuccessView = {
                            navHostController.navigate(Screen.Auth.route) {
                                popUpTo(Screen.Profile.route) {
                                    inclusive = true
                                }
                            }
                        })
                    },
                    loadUserProfile = { userViewModel.loadUserProfile() },
                    userProfileStateFlow = userViewModel.userProfile,
                )

                val recommendationArguments = ScreenArguments.RecommendationSongArguments(
                    openDetail = { navHostController.navigate(Screen.DetailSong.route) },
                    loadTopTracks = { songViewModel.getTopTrack() },
                    topTrackStateFlow = songViewModel.topTracks
                )

                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = {
                            if (currentRoute != Screen.Home.route && currentRoute != Screen.Auth.route) {
                                TopAppBar(
                                    backgroundColor = MaterialTheme.colors.background.copy(alpha = 0f),
                                    title = {},
                                    elevation = 0.dp,
                                    navigationIcon = {
                                        IconButton(onClick = { navHostController.popBackStack() }) {
                                            Icon(
                                                imageVector = Icons.Default.ArrowBack,
                                                contentDescription = "back"
                                            )
                                        }
                                    },
                                )
                            }
                        }
                    ) { padding ->

                        NavHost(
                            modifier = Modifier.padding(padding),
                            navController = navHostController,
                            startDestination = if (isUserLogged) Screen.Home.route else Screen.Auth.route,
                        ) {
                            composable(Screen.Auth.route) {
                                AuthScreen(authArguments = authArguments)
                            }
                            composable(Screen.Home.route) {
                                HomeScreen(arguments = homeArguments)
                            }
                            composable(
                                route = Screen.Profile.route,
                            ) {
                                ProfileScreen(profileArguments = profileArguments)
                            }
                            composable(
                                route = Screen.TopSongsList.route,
                                arguments = listOf(navArgument("id") { type = NavType.StringType })
                            ) {}
                            composable(
                                route = Screen.Recommendation.route,
                                arguments = listOf(navArgument("type") {
                                    type = NavType.StringType
                                    nullable = true
                                })
                            ) {
                                val type = it.arguments?.getString("type") ?: ""
                                RecommendationScreen(
                                    arguments = recommendationArguments
                                )
                            }
                            composable(
                                route = Screen.DetailSong.route
                            ) {
                                DetailSongScreen()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {

        super.onNewIntent(intent)
        val uri = intent?.data
        uri?.let {
            val response = AuthorizationResponse.fromUri(it)
            when (response.type) {
                // Response was successful and contains auth token
                AuthorizationResponse.Type.TOKEN -> {
                    Log.d("TAG", "spotify auth: ${response.accessToken}")
                    authViewModel.setSpotifyTokenAuth(response.accessToken)
                    navHostController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Auth.route) {
                            inclusive = true
                        }
                    }
                }
                AuthorizationResponse.Type.ERROR -> Toast.makeText(
                    this,
                    "error: ${response.error}",
                    Toast.LENGTH_SHORT
                ).show()
                else -> Log.d("TAG", "else spotify auth: ${response.type}")
            }
        }
    }
}