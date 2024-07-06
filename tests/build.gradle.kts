plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "br.com.raphaelmaracaipe.tests"
    compileSdk = rootProject.extra["compileSdkVersion"] as Int

    defaultConfig {
        minSdk = rootProject.extra["minSdkVersion"] as Int
        targetSdk = rootProject.extra["targetSdkVersion"] as Int

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
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
}

dependencies {
    implementation(project(path = ":core"))

    implementation("androidx.core:core-ktx:${rootProject.extra["core_ktx"]}")
    implementation("androidx.appcompat:appcompat:${rootProject.extra["app_compact"]}")
    implementation("com.google.android.material:material:${rootProject.extra["material"]}")
    implementation("com.google.dagger:hilt-android:${rootProject.extra["dagger_hil"]}")
    implementation("androidx.navigation:navigation-fragment-ktx:${rootProject.extra["navigation"]}")
    implementation("androidx.navigation:navigation-ui-ktx:${rootProject.extra["navigation"]}")

    debugImplementation("androidx.fragment:fragment-testing:${rootProject.extra["test_fragment"]}")
    debugImplementation("com.google.dagger:hilt-android-testing:${rootProject.extra["dagger_hil"]}")
    debugImplementation("org.mockito:mockito-core:${rootProject.extra["test_mockito"]}")
    debugImplementation("androidx.test:runner:1.5.2")

    kapt("com.google.dagger:hilt-compiler:${rootProject.extra["dagger_hil"]}")

    testImplementation("junit:junit:${rootProject.extra["test_junit"]}")
    testImplementation("androidx.arch.core:core-testing:${rootProject.extra["test_core_testing"]}")
    testImplementation("androidx.test:core-ktx:${rootProject.extra["test_core"]}")
    testImplementation("io.mockk:mockk:${rootProject.extra["test_mockk"]}")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

kapt {
    correctErrorTypes = true
}
