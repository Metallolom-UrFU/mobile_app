
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp") version "2.1.20-1.0.32"
    id("kotlin-parcelize")

}

android {
    namespace = "com.example.myapplication"
    compileSdk = 36

    packaging {
        resources {
            excludes += "META-INF/versions/9/OSGI-INF/MANIFEST.MF"
        }
    }


    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 31
        targetSdk = 36
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
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.6.0"
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
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.localbroadcastmanager)
    implementation(libs.androidx.runtime.livedata)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.compose.foundation)
    ksp(libs.androidx.room.room.compiler)
    implementation(libs.androidx.room.ktx)

    implementation(libs.koin.androidx.compose)
    implementation(libs.modo.compose)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.coil.compose)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.maps.mobile)

    implementation(libs.androidx.material.icons.extended)


}