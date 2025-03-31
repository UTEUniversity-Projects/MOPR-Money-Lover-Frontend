plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.moneylover"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.moneylover"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "${project.properties["BASE_URL"]}")
        }
        debug {
            buildConfigField("String", "BASE_URL", "${project.properties["BASE_URL"]}")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.circleimageview)

    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.extensions)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.lombok)
    annotationProcessor(libs.lombok)
    annotationProcessor(libs.lombok.mapstruct.binding)

    implementation(libs.dagger)
    annotationProcessor(libs.dagger.compiler)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.adapter.rxjava3)
    implementation(libs.rxjava3)
    implementation(libs.rxandroid3)
    implementation(libs.toasty)
    implementation(libs.timber)
    implementation(libs.hashids)
    implementation(libs.viewpager2)
    implementation(libs.circleindicator)
}
