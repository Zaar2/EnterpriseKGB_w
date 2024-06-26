plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.zaar2.meatKGB_w"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.zaar2.meatKGB_w"
        minSdk = 26
        targetSdk = 34
        versionCode = 9
        versionName = "2.1.1"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
    viewBinding {
        this.enable = true
    }
}

//apply plugin: 'kotlin-android'
//apply plugin: 'kotlin-kapt'

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //impl liveData and viewModel
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")
    //noinspection LifecycleAnnotationProcessorWithJava8
//    annotationProcessor ("androidx.lifecycle:lifecycle-compiler:2.7.0")
    //impl room
    implementation ("androidx.room:room-runtime:2.6.1")
//    annotationProcessor ("androidx.room:room-compiler:2.6.1")
    kapt ("androidx.room:room-compiler:2.6.1")
    implementation ("androidx.room:room-ktx:2.6.1")
    implementation ("androidx.room:room-rxjava2:2.6.1")
//    implementation ("androidx.room:room-runtime:2.6.1")
//    kapt ("android.arch.persistence.room:compiler:1.1.1")
////    annotationProcessor ("androidx.room:room-compiler:2.6.1")
//    implementation ("androidx.room:room-ktx:2.6.1")
//    implementation ("androidx.room:room-rxjava2:2.6.1")
    //impl navigation
    val navVersion = "2.7.7"
    // Java language implementation
//    implementation("androidx.navigation:navigation-fragment:$navVersion")
//    implementation("androidx.navigation:navigation-ui:$navVersion")
    // Kotlin
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")
    // Feature module Support
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$navVersion")
    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:$navVersion")
//    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.7")
//    implementation ("androidx.navigation:navigation-ui-ktx:2.7.7")
    // Testing Navigation
    androidTestImplementation ("androidx.navigation:navigation-testing:2.7.7")
    //impl recyclerview
    implementation ("androidx.recyclerview:recyclerview:1.3.2")
    //impl EncryptedSharedPreferences
    implementation ("androidx.security:security-crypto:1.1.0-alpha06")
    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.retrofit2:adapter-rxjava2:2.3.0")
    //retrofit: string-converter
    implementation ("com.squareup.retrofit2:converter-scalars:2.5.0")
    //coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
}