plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "br.com.raphaelmaracaipe.uiauth"
    compileSdk = rootProject.extra["compileSdkVersion"] as Int

    defaultConfig {
        minSdk = rootProject.extra["minSdkVersion"] as Int
        targetSdk = rootProject.extra["targetSdkVersion"] as Int

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), file("proguard-rules.pro"))
        }
        create("releaseDebuggable") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), file("proguard-rules.pro"))
        }
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
    implementation("androidx.lifecycle:lifecycle-extensions:${rootProject.extra["lifecycle_extensions"]}")
    implementation("androidx.lifecycle:lifecycle-livedata:${rootProject.extra["lifecycle_livedata"]}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${rootProject.extra["lifecycle_livedata"]}")
    implementation("com.google.code.gson:gson:${rootProject.extra["gson"]}")
    implementation("androidx.navigation:navigation-fragment-ktx:${rootProject.extra["navigation"]}")
    implementation("androidx.navigation:navigation-ui-ktx:${rootProject.extra["navigation"]}")
    implementation("com.squareup.okhttp3:okhttp:${rootProject.extra["okhttp"]}")
    implementation("com.github.leandroborgesferreira:loading-button-android:${rootProject.extra["loading_button"]}")
    implementation("com.google.dagger:hilt-android:${rootProject.extra["dagger_hil"]}")

    debugImplementation("androidx.fragment:fragment-testing:${rootProject.extra["test_fragment"]}")

    testImplementation("com.google.dagger:hilt-android-testing:${rootProject.extra["dagger_hil"]}")
    testImplementation("junit:junit:${rootProject.extra["test_junit"]}")
    testImplementation("androidx.arch.core:core-testing:${rootProject.extra["test_core_testing"]}")
    testImplementation("org.robolectric:robolectric:${rootProject.extra["test_roboletric"]}")
    testImplementation("androidx.test:core:${rootProject.extra["test_core"]}")
    testImplementation("io.mockk:mockk:${rootProject.extra["test_mockk"]}")
    testImplementation("io.mockk:mockk-android:${rootProject.extra["test_mockk"]}")
    testImplementation("com.squareup.okhttp3:mockwebserver:${rootProject.extra["okhttp"]}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${rootProject.extra["test_coroutines"]}")

    kapt("com.google.dagger:hilt-compiler:${rootProject.extra["dagger_hil"]}")
    kaptTest("com.google.dagger:hilt-android-compiler:${rootProject.extra["dagger_hil"]}")
    kaptTest("androidx.databinding:databinding-compiler:${rootProject.extra["test_binding_compiler"]}")
    kaptAndroidTest("androidx.databinding:databinding-compiler:${rootProject.extra["test_binding_compiler"]}")

    androidTestImplementation("androidx.navigation:navigation-testing:${rootProject.extra["navigation"]}")
    androidTestImplementation("androidx.arch.core:core-testing:${rootProject.extra["test_core_testing"]}")
    androidTestImplementation("io.mockk:mockk:${rootProject.extra["test_mockk"]}")
    androidTestImplementation("io.mockk:mockk-android:${rootProject.extra["test_mockk"]}")
    androidTestImplementation("androidx.test.ext:junit:${rootProject.extra["android_test_junit"]}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${rootProject.extra["android_test_espresso_core"]}")
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:${rootProject.extra["okhttp"]}")
}
