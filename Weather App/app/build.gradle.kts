plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "+"
    id("kotlin-kapt")
}

android {
    namespace = "com.example.weatherapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.weatherapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

    androidResources {
        generateLocaleConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Nav
    implementation("androidx.navigation:navigation-compose:+")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:+")

    //Work manager
    implementation("androidx.work:work-runtime:+")
    implementation("androidx.work:work-runtime-ktx:+")

    //Retrofit & glide
    implementation("com.squareup.retrofit2:retrofit:+")
    implementation("com.squareup.retrofit2:converter-gson:+")

    annotationProcessor("com.github.bumptech.glide:compiler:+")
    implementation("com.github.bumptech.glide:glide:+")

    // Glide Integration for Jetpack Compose
    implementation("com.github.skydoves:landscapist-glide:+")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:+")

    //Room
    implementation("androidx.room:room-runtime:+")
    implementation("androidx.room:room-ktx:+")
    kapt("androidx.room:room-compiler:+")

    //ViewModel & livedata
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose-android:+")
    implementation("androidx.lifecycle:lifecycle-extensions:+")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:+")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:+")

    //Livedata
    implementation("androidx.compose.runtime:runtime-livedata:+")

    //Lottie
    implementation("com.airbnb.android:lottie-compose:+")

    //Google map
    implementation("com.google.android.gms:play-services-location:21.1.0")
    implementation("com.google.maps.android:maps-compose:6.4.1")

    //Shared pref
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // WorkManager
    implementation("androidx.work:work-runtime-ktx:2.8.1")

    // Time picker
    implementation("com.maxkeppeler.sheets-compose-dialogs:core:1.2.0")
    implementation("com.maxkeppeler.sheets-compose-dialogs:clock:1.2.0")

    //material icons
    implementation("androidx.compose.material:material-icons-extended:1.5.4")

    // Dependencies for local unit tests
    testImplementation("junit:junit:+")
    testImplementation("org.hamcrest:hamcrest-all:+")
    testImplementation("androidx.arch.core:core-testing:+")
    testImplementation("org.robolectric:robolectric:+")

    // AndroidX Test - JVM testing
    testImplementation("androidx.test:core-ktx:+")
    //testImplementation "androidx.test.ext:junit:$androidXTestExtKotlinRunnerVersion"

    // AndroidX Test - Instrumented testing
    androidTestImplementation("androidx.test:core:+")
    androidTestImplementation("androidx.test.espresso:espresso-core:+")

    //Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    // hamcrest
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("org.hamcrest:hamcrest-library:2.2")
    androidTestImplementation("org.hamcrest:hamcrest:2.2")
    androidTestImplementation("org.hamcrest:hamcrest-library:2.2")

    // AndroidX and Robolectric
    testImplementation("androidx.test.ext:junit-ktx:+")
    testImplementation("androidx.test:core-ktx:+")
    testImplementation("org.robolectric:robolectric:4.11")

    // InstantTaskExecutorRule
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")

    //kotlinx-coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:+")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:+")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:+")

    //MockK
    testImplementation("io.mockk:mockk-android:1.13.17")
    testImplementation("io.mockk:mockk-agent:1.13.17")

    //Splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")
}