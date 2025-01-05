plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "br.com.raphaelmaracaipe.uimessage"
    compileSdk = rootProject.extra["compileSdkVersion"] as Int

    defaultConfig {
        minSdk = rootProject.extra["minSdkVersion"] as Int

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    buildFeatures {
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    testOptions {
        animationsDisabled = true
        unitTests {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
    }

    packagingOptions {
        resources.excludes += "META-INF/*"
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":tests"))

    implementation("androidx.core:core-ktx:${rootProject.extra["core_ktx"]}")
    implementation("androidx.appcompat:appcompat:${rootProject.extra["app_compact"]}")
    implementation("com.google.android.material:material:${rootProject.extra["material"]}")
    implementation("androidx.navigation:navigation-fragment-ktx:${rootProject.extra["navigation"]}")
    implementation("androidx.navigation:navigation-ui-ktx:${rootProject.extra["navigation"]}")

    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}