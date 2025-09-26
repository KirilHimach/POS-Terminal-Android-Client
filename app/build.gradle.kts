plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("kotlin-kapt")
}

android {
    namespace = "by.terminal.posterminalandroidklient"
    compileSdk = 36

    defaultConfig {
        applicationId = "by.terminal.posterminalandroidklient"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    packaging {
        resources {
            excludes += "/META-INF/versions/9/OSGI-INF/MANIFEST.MF"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isDebuggable = false
            buildConfigField(type = "String", name = "BASE_URL", value = "\"http://localhost:8080/\"")
        }
        debug {
            isDebuggable = true
            buildConfigField(type = "String", name = "BASE_URL", value = "\"http://localhost:8080/\"")
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
        buildConfig = true
    }
}

dependencies {
    implementation (libs.dagger) //Dagger2
    kapt (libs.dagger.compiler)

    implementation (libs.converter.gson) //Retrofit
    implementation (libs.gson)
    implementation (libs.retrofit)
    implementation (libs.logging.interceptor)
    implementation (libs.okhttp)
    implementation (libs.glide) //Glide

    implementation (libs.kotlin.reflect)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(kotlin("reflect"))
}