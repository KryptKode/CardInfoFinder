// Gradle currently does not allow loading in the "plugins" or "buildscript" blocks
// declarations not in the root module.
// For a better package structure this calls should be in a smaller "ext" package
// but at the moment we need this hack to be able to publicly expose them
//
// See https://github.com/gradle/gradle/issues/9270 for details.
@file:Suppress("PackageDirectoryMismatch")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

object GradlePluginId {
    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinParcelize = "kotlin-parcelize"
    const val kotlinKapt = "kotlin-kapt"
    const val kotlin = "kotlin"
    const val daggerHilt = "dagger.hilt.android.plugin"
    const val jacoco = "com.vanniktech.android.junit.jacoco"
}

val PluginDependenciesSpec.androidApplication: PluginDependencySpec
    get() = id(GradlePluginId.androidApplication)

val PluginDependenciesSpec.quality: PluginDependencySpec
    get() = id(ScriptsPlugins.quality)

val PluginDependenciesSpec.kotlinAndroid: PluginDependencySpec
    get() = id(GradlePluginId.kotlinAndroid)

val PluginDependenciesSpec.kotlin: PluginDependencySpec
    get() = id(GradlePluginId.kotlin)

val PluginDependenciesSpec.kotlinParcelize: PluginDependencySpec
    get() = id(GradlePluginId.kotlinParcelize)

val PluginDependenciesSpec.kotlinKapt: PluginDependencySpec
    get() = id(GradlePluginId.kotlinKapt)

val PluginDependenciesSpec.daggerHilt: PluginDependencySpec
    get() = id(GradlePluginId.daggerHilt)


val PluginDependenciesSpec.jacoco: PluginDependencySpec
    get() = id(GradlePluginId.jacoco)
