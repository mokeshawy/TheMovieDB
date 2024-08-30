plugins {
    id("com.android.application") version "8.5.2" apply false
    id("org.jetbrains.kotlin.android") version "2.0.0" apply false
    id("com.google.firebase.crashlytics") version "3.0.2" apply false
    id("com.android.library") version "8.5.2" apply false
}

buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.47")
        classpath("com.google.gms:google-services:4.4.2")
        classpath("com.google.firebase:perf-plugin:1.4.2")
        val navVersion = "2.7.7"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
    }
    repositories {
        google()
        mavenCentral()
    }
}