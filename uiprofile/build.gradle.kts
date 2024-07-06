plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "br.com.raphaelmaracaipe.uiprofile"
    compileSdk = rootProject.ext["compileSdkVersion"] as Int
    defaultConfig {
        minSdk = rootProject.ext["minSdkVersion"] as Int
        targetSdk = rootProject.ext["targetSdkVersion"] as Int
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), file("proguard-rules.pro"))
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
        dataBinding = true
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

    implementation("androidx.core:core-ktx:${rootProject.ext["core_ktx"]}")
    implementation("androidx.appcompat:appcompat:${rootProject.ext["app_compact"]}")
    implementation("com.google.android.material:material:${rootProject.ext["material"]}")
    implementation("androidx.navigation:navigation-fragment-ktx:${rootProject.ext["navigation"]}")
    implementation("androidx.navigation:navigation-ui-ktx:${rootProject.ext["navigation"]}")
    implementation("com.google.dagger:hilt-android:${rootProject.ext["dagger_hil"]}")

    debugImplementation("androidx.fragment:fragment-testing:${rootProject.ext["test_fragment"]}")

    kapt("com.google.dagger:hilt-compiler:${rootProject.ext["dagger_hil"]}")
    kaptTest("com.google.dagger:hilt-android-compiler:${rootProject.ext["dagger_hil"]}")

    testImplementation("com.google.dagger:hilt-android-testing:${rootProject.ext["dagger_hil"]}")
    testImplementation("junit:junit:${rootProject.ext["test_junit"]}")
    testImplementation("androidx.arch.core:core-testing:${rootProject.ext["test_core_testing"]}")
    testImplementation("org.robolectric:robolectric:${rootProject.ext["test_roboletric"]}")
    testImplementation("androidx.test:core:${rootProject.ext["test_core"]}")
    testImplementation("io.mockk:mockk:${rootProject.ext["test_mockk"]}")
    testImplementation("io.mockk:mockk-android:${rootProject.ext["test_mockk"]}")
    testImplementation("com.squareup.okhttp3:mockwebserver:${rootProject.ext["okhttp"]}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${rootProject.ext["test_coroutines"]}")

    androidTestImplementation("androidx.test.ext:junit:${rootProject.ext["android_test_junit"]}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${rootProject.ext["android_test_espresso_core"]}")
}
