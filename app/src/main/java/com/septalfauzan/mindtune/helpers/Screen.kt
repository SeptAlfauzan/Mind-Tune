package com.septalfauzan.mindtune.helpers

enum class RecommendationType {
    DEPRESSION,
    ANXIETY,
    INSOMNIA,
}

sealed class Screen(val route: String) {
    object Home : Screen(route = "home")
    object Auth : Screen(route = "auth")
    object Profile : Screen(route = "profile")
    object TopSongsList : Screen(route = "top_song_list"){
        fun createRoute(id: String): String = "top_song_list/$id"
    }
    object Recommendation : Screen(route = "recommendation/{type}") {
        fun createRoute(type: RecommendationType): String = when (type) {
            RecommendationType.DEPRESSION -> "recommendation/depression"
            RecommendationType.ANXIETY -> "recommendation/anxiety"
            RecommendationType.INSOMNIA -> "recommendation/insomnia"
        }
    }
    object DetailSong : Screen(route = "detail") {
        fun createRoute(id: String): String = "detail/$id"
    }
}