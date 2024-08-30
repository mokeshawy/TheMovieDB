plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {

    namespace = "com.paymob.core"
    compileSdk = 34

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions += listOf("version")
    productFlavors {
        create("staging") {
            dimension = "version"
        }
        create("live") {
            dimension = "version"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        //noinspection DataBindingWithoutKapt
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    api("androidx.core:core-ktx:1.13.1")
    api("androidx.appcompat:appcompat:1.7.0")
    api("com.google.android.material:material:1.12.0")
    api("androidx.constraintlayout:constraintlayout:2.1.4")
    api("androidx.legacy:legacy-support-v4:1.0.0")
    api("androidx.preference:preference-ktx:1.2.1")
    api("androidx.activity:activity-ktx:1.9.1")

    api("org.jetbrains.kotlin:kotlin-reflect:1.9.20")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")


    //webkit
    api("androidx.webkit:webkit:1.11.0")

    // hilt api.
    implementation("com.google.dagger:hilt-android:2.47")
    kapt("com.google.dagger:hilt-android-compiler:2.47")
    kapt("androidx.hilt:hilt-compiler:1.2.0")
    api("androidx.hilt:hilt-navigation-fragment:1.2.0")


    // Koin
    api("io.insert-koin:koin-android:3.4.0")
    api("io.insert-koin:koin-androidx-navigation:3.4.0")

    // Gson
    api("com.google.code.gson:gson:2.11.0")

    // Navigation component.
    api("androidx.navigation:navigation-fragment-ktx:2.7.7")
    api("androidx.navigation:navigation-ui-ktx:2.7.7")

    // ViewModel
    api("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")
    api("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")

    //ViewModels delegation extensions for activity
    api("androidx.activity:activity-ktx:1.9.1")
    api("androidx.fragment:fragment-ktx:1.8.2")

    // LiveData
    api("androidx.lifecycle:lifecycle-livedata-ktx:2.8.4")

    //process-phoenix
    api("com.jakewharton:process-phoenix:3.0.0")

    // Coroutines
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    // Retrofit
    api("com.squareup.retrofit2:retrofit:2.11.0")
    api("com.squareup.retrofit2:converter-gson:2.11.0")
    api("com.squareup.okhttp3:okhttp:4.12.0")
    api("com.squareup.okhttp3:okhttp-dnsoverhttps:4.10.0")
    api("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // ApolloGraphql
    api("com.apollographql.apollo3:apollo-runtime:3.8.4")
    api("com.apollographql.apollo3:apollo-api:3.8.4")


    //data-store
    api("androidx.datastore:datastore:1.1.1")
    api("androidx.datastore:datastore-preferences:1.1.1")


    val roomVersion = "2.6.1"
    api("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    api("androidx.room:room-ktx:$roomVersion")


    // sdp && ssp of font
    api("com.intuit.sdp:sdp-android:1.1.1")
    api("com.intuit.ssp:ssp-android:1.1.0")

    //FireBase
    api(platform("com.google.firebase:firebase-bom:33.2.0"))
    api("com.google.firebase:firebase-crashlytics-ktx")
    api("com.google.firebase:firebase-analytics-ktx")
    api("com.google.firebase:firebase-messaging-ktx")
    api("com.google.firebase:firebase-inappmessaging-ktx")
    api("com.google.firebase:firebase-inappmessaging-display-ktx")
    api("com.google.firebase:firebase-installations-ktx")
    api("com.google.firebase:firebase-perf-ktx")
    api("com.google.firebase:firebase-config-ktx:22.0.0")

    // leak canary
//    debugApi "com.squareup.leakcanary:leakcanary-android:2.9.1"

    // timber
    api("com.jakewharton.timber:timber:5.0.1")

    // encrypted shared preference
    api("androidx.security:security-crypto:1.1.0-alpha06")

    // Lottie
    api("com.airbnb.android:lottie:6.5.0")

    //Android Remote Debugger
    debugApi("com.github.zerobranch.android-remote-debugger:debugger:1.1.2")
    releaseApi("com.github.zerobranch.android-remote-debugger:noop:1.1.0")


    //Play Store App Update Libraries
    api("com.google.android.play:app-update:2.1.0")
    api("com.google.android.play:app-update-ktx:2.1.0")

    //paging3
    api("androidx.paging:paging-runtime-ktx:3.3.2")

    api("androidx.work:work-runtime-ktx:2.9.1")

    //Coil
    api("io.coil-kt:coil:2.7.0")
    api("io.coil-kt:coil-svg:2.7.0")


    //pluto
    debugApi("com.plutolib:pluto:2.0.6")
    releaseApi("com.plutolib:pluto-no-op:2.0.6")
    debugApi("com.plutolib.plugins:network:2.0.6")
    releaseApi("com.plutolib.plugins:network-no-op:2.0.6")


    // Zxing
    api("com.google.zxing:core:3.5.1")

    // maps
    api("com.google.android.gms:play-services-maps:19.0.0")
    api("com.google.android.gms:play-services-location:21.3.0")
    api("com.google.android.gms:play-services-places:17.1.0")
    api("com.google.android.libraries.places:places:3.5.0")



    // CameraX
    val cameraxVersion = "1.3.4"
    api("androidx.camera:camera-core:$cameraxVersion")
    api("androidx.camera:camera-camera2:$cameraxVersion")
    api("androidx.camera:camera-lifecycle:$cameraxVersion")
    api("androidx.camera:camera-view:$cameraxVersion")
    api("androidx.camera:camera-extensions:$cameraxVersion")

    //mlkit:barcode
    api("com.google.mlkit:barcode-scanning:17.3.0")


    //Glide
    api("com.github.bumptech.glide:glide:4.14.2")
    kapt("com.github.bumptech.glide:compiler:4.11.0")


    // Lingver
    api("com.github.YarikSOffice:lingver:1.3.0")

    //truetime
    api("com.github.instacart:truetime-android:4.0.0.alpha")

    //coreLibraryDesugaring
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
}