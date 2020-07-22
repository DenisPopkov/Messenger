package ru.popkovden.messengerapplication.utils.helper

import androidx.navigation.fragment.NavHostFragment
import ru.popkovden.messengerapplication.R

fun changeStartDestination(navHost: NavHostFragment?, destination: Int) {
    val navController = navHost?.navController
    val navInflater = navController?.navInflater
    val graph = navInflater?.inflate(R.navigation.nav_graph)
    graph?.startDestination = destination
    navController?.graph = graph!!
}