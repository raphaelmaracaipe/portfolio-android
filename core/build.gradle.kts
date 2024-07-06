plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "br.com.raphaelmaracaipe.core"
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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "URL", "\"http://10.0.2.2:3000\"")
            buildConfigField("boolean", "IS_DEV", "false")
        }
        create("releaseDebuggable") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "URL", "\"http://10.0.2.2:3000\"")
            buildConfigField("boolean", "IS_DEV", "false")
        }
        getByName("debug") {
            buildConfigField("String", "URL", "\"http://10.0.2.2:3000\"")
            buildConfigField("boolean", "IS_DEV", "true")
        }
    }

    externalNativeBuild {
        cmake {
            path = file("CMakeLists.txt")
        }
    }

    sourceSets {
        getByName("main") {
            jniLibs.srcDirs("src/main/cpp")
        }
    }

    buildFeatures {
        buildConfig = true
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
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:${rootProject.extra["core_ktx"]}")
    implementation("androidx.appcompat:appcompat:${rootProject.extra["app_compact"]}")
    implementation("com.google.android.material:material:${rootProject.extra["material"]}")
    implementation("androidx.lifecycle:lifecycle-extensions:${rootProject.extra["lifecycle_extensions"]}")
    implementation("androidx.lifecycle:lifecycle-livedata:${rootProject.extra["lifecycle_livedata"]}")
    implementation("androidx.navigation:navigation-fragment-ktx:${rootProject.extra["navigation"]}")
    implementation("androidx.navigation:navigation-ui-ktx:${rootProject.extra["navigation"]}")
    implementation("com.google.code.gson:gson:${rootProject.extra["gson"]}")
    implementation("com.squareup.retrofit2:retrofit:${rootProject.extra["retrofit"]}")
    implementation("com.squareup.retrofit2:converter-gson:${rootProject.extra["retrofit"]}")
    implementation("com.github.curious-odd-man:rgxgen:${rootProject.extra["rgxgen"]}")
    implementation("com.squareup.okhttp3:logging-interceptor:${rootProject.extra["http_logger_inteceptor"]}")
    implementation("com.google.dagger:hilt-android:${rootProject.extra["dagger_hil"]}")
    implementation("androidx.test:runner:1.5.2")

    debugImplementation("androidx.fragment:fragment-testing:${rootProject.extra["test_fragment"]}")
    debugImplementation("com.google.dagger:hilt-android-testing:${rootProject.extra["dagger_hil"]}")
    debugImplementation("org.mockito:mockito-core:${rootProject.extra["test_mockito"]}")
    debugImplementation("com.squareup.okhttp3:mockwebserver:${rootProject.extra["okhttp"]}")

    kapt("com.google.dagger:hilt-compiler:${rootProject.extra["dagger_hil"]}")

    testImplementation("junit:junit:${rootProject.extra["test_junit"]}")
    testImplementation("androidx.test:core-ktx:${rootProject.extra["test_core"]}")
    testImplementation("io.mockk:mockk:${rootProject.extra["test_mockk"]}")
    testImplementation("androidx.arch.core:core-testing:${rootProject.extra["test_core_testing"]}")
    testImplementation("org.robolectric:robolectric:${rootProject.extra["test_roboletric"]}")

    androidTestImplementation("androidx.test.ext:junit:${rootProject.extra["android_test_junit"]}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${rootProject.extra["android_test_espresso_core"]}")
}

kapt {
    correctErrorTypes = true
}