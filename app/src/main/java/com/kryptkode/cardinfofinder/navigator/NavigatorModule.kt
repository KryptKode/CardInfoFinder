package com.kryptkode.cardinfofinder.navigator

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.kryptkode.cardinfofinder.ui.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
interface NavigatorModule {

    companion object {
        @Provides
        @ActivityScoped
        fun provideNavControllerProvider(activity: Activity): NavControllerProvider {
            return (activity as MainActivity)
        }

        @Provides
        @ActivityScoped
        fun provideAppCompatActivity(activity: Activity): LifecycleCoroutineScope {
            return (activity as AppCompatActivity).lifecycleScope
        }
    }
}
