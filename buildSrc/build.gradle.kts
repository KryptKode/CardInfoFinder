plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

repositories {
    google()
    maven("https://plugins.gradle.org/m2/")
    jcenter()
}

object Dependencies {
    const val androidGradlePlugin = "com.android.tools.build:gradle:4.1.2"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.30"
    const val detektGradlePlugin = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.15.0"
    const val klintGradlePlugin = "org.jlleitschuh.gradle:ktlint-gradle:9.4.1"
    const val daggerHilt = "com.google.dagger:hilt-android-gradle-plugin:2.28.3-alpha"
    const val jacoco = "com.vanniktech:gradle-android-junit-jacoco-plugin:0.16.0"
}

dependencies {
    implementation(Dependencies.androidGradlePlugin)
    implementation(Dependencies.kotlinGradlePlugin)
    implementation(Dependencies.detektGradlePlugin)
    implementation(Dependencies.klintGradlePlugin)
    implementation(Dependencies.daggerHilt)
    implementation(Dependencies.jacoco)
}
