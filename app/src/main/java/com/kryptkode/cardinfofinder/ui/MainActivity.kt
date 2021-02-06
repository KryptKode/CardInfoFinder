package com.kryptkode.cardinfofinder.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.kryptkode.cardinfofinder.R
import com.kryptkode.cardinfofinder.navigator.NavControllerProvider
import com.kryptkode.cardinfofinder.navigator.Navigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavControllerProvider {
    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycle.addObserver(navigator)
    }

    override fun getNavController(): NavController {
        return (supportFragmentManager
            .findFragmentById(R.id.nav_host) as NavHostFragment).navController
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
