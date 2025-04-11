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
            buildConfigField("String", "SITE_KEY", "${project.properties["SITE_KEY"]}")

        }
        debug {
            buildConfigField("String", "BASE_URL", "${project.properties["BASE_URL"]}")
            buildConfigField("String", "SITE_KEY", "${project.properties["SITE_KEY"]}")
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
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
    testImplementation(libs.junit)

    annotationProcessor(libs.dagger.compiler)
    annotationProcessor(libs.lombok)
    annotationProcessor(libs.lombok.mapstruct.binding)

    implementation(libs.activity)
    implementation(libs.androidx.viewpager2)
    implementation(libs.appcompat)
    implementation(libs.circleimageview)
    implementation(libs.circleindicator)
    implementation(libs.constraintlayout)
    implementation(libs.dagger)
    implementation(libs.glide)
    implementation(libs.gson)
    implementation(libs.hashids)
    implementation(libs.lifecycle.extensions)
    implementation(libs.lombok)
    implementation(libs.material)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit)
    implementation(libs.retrofit.adapter.rxjava3)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.rxandroid3)
    implementation(libs.rxjava3)
    implementation(libs.sdp)
    implementation(libs.ssp)
    implementation(libs.timber)
    implementation(libs.toasty)
    implementation(libs.recaptcha)
    implementation(libs.safetynet)
    implementation(libs.splashscreen)
    implementation(libs.tooltips)
}

