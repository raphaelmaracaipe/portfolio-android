plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.dagger.hilt.android")
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
        viewBinding = true
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

}

dependencies {
    implementation(project(":core"))
    implementation(project(":tests"))

    implementation("androidx.core:core-ktx:${rootProject.extra["core_ktx"]}")
    implementation("androidx.appcompat:appcompat:${rootProject.extra["app_compact"]}")
    implementation("com.google.android.material:material:${rootProject.extra["material"]}")
    implementation("androidx.navigation:navigation-fragment-ktx:${rootProject.extra["navigation"]}")
    implementation("androidx.navigation:navigation-ui-ktx:${rootProject.extra["navigation"]}")
    implementation("com.google.dagger:hilt-android:${rootProject.extra["dagger_hil"]}")
    implementation("androidx.work:work-runtime:2.10.0")
    implementation("androidx.work:work-runtime-ktx:2.10.0")

    testImplementation("androidx.work:work-testing:2.7.1")
    testImplementation("com.google.dagger:hilt-android-testing:${rootProject.extra["dagger_hil"]}")
    testImplementation("junit:junit:${rootProject.extra["test_junit"]}")
    testImplementation("org.robolectric:robolectric:${rootProject.extra["test_roboletric"]}")
    testImplementation("androidx.test:core:${rootProject.extra["test_core"]}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${rootProject.extra["test_coroutines"]}")
    testImplementation("androidx.arch.core:core-testing:${rootProject.extra["test_core_testing"]}")
    testImplementation("io.mockk:mockk:${rootProject.extra["test_mockk"]}")
    testImplementation("io.mockk:mockk-android:${rootProject.extra["test_mockk"]}")

    kapt("com.google.dagger:hilt-compiler:${rootProject.extra["dagger_hil"]}")
    kaptTest("com.google.dagger:hilt-android-compiler:${rootProject.extra["dagger_hil"]}")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}