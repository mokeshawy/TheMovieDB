plugins {
    id("com.android.application") version "8.5.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.firebase.crashlytics") version "2.9.9" apply false
    id("com.android.library") version "8.5.1" apply false
}

buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.52")
        classpath("com.google.gms:google-services:4.4.1")
        classpath("com.google.firebase:perf-plugin:1.4.2")
        val navVersion = "2.7.7"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
    }
    repositories {
        google()
        mavenCentral()
    }
}