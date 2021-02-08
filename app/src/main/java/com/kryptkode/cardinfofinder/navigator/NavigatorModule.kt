package com.kryptkode.cardinfofinder.navigator

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
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
        fun provideNavControllerProvider(activity: AppCompatActivity): NavControllerProvider {
            return (activity as MainActivity)
        }

        @Provides
        @ActivityScoped
        fun provideAppCompatActivity(activity: Activity): AppCompatActivity {
            return activity as AppCompatActivity
        }
    }
}
