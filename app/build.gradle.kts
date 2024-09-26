plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.android)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.google.hilt)
    alias(libs.plugins.jetbrains.compose)
}

android {
    namespace = "com.naulian.compose"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.naulian.compose"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    //core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.androidx.core.splashscreen)

    //compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphic)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.raamcosta.destination.core)
    ksp(libs.raamcosta.destination.ksp)

    //test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.test.manifest)

    //firebase
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.google.firebase.auth)
    implementation(libs.google.firebase.firestore)

    //naulian
    implementation(libs.naulian.anhance)
    implementation(libs.naulian.modify)
    implementation(libs.naulian.glow)

    implementation(libs.kotlinx.serialization.json)

    //hilt
    implementation(libs.google.hilt.android)
    ksp(libs.google.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.codigo.toolsbelt)
    implementation(libs.codigo.toolsbeltui)
}

ksp {
    arg("compose-destinations.codeGenPackageName", "com.naulian.compose") // replace package name!
}