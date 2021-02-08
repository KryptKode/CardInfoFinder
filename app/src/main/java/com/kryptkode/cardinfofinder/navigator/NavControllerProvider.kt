package com.kryptkode.cardinfofinder.navigator

import androidx.navigation.NavController

interface NavControllerProvider {
    fun getNavController(): NavController
}
