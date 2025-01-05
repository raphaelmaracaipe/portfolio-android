plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "br.com.raphaelmaracaipe.portfolio"
    compileSdk = rootProject.extra["compileSdkVersion"] as Int

    defaultConfig {
        applicationId = "br.com.raphaelmaracaipe.portfolio"
        minSdk = rootProject.extra["minSdkVersion"] as Int
        targetSdk = rootProject.extra["targetSdkVersion"] as Int
        versionCode = 1
        versionName = "1.3.0"

        testInstrumentationRunner = "br.com.raphaelmaracaipe.core.testUnit.CustomTestRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file("../key/build_test.jks")
            storePassword = "123456"
            keyAlias = "test"
            keyPassword = "123456"
        }
    }

    buildTypes {
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        create("releaseDebuggable") {
            isDebuggable = true
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
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
    implementation(project(path = ":uiauth"))
    implementation(project(path = ":uimessage"))
    implementation(project(path = ":uiprofile"))
    implementation(project(path = ":uicontacts"))
    implementation(project(path = ":core"))
    implementation(project(path = ":tests"))

    implementation("androidx.core:core-ktx:${rootProject.extra["core_ktx"]}")
    implementation("androidx.appcompat:appcompat:${rootProject.extra["app_compact"]}")
    implementation("com.google.android.material:material:${rootProject.extra["material"]}")
    implementation("androidx.navigation:navigation-fragment-ktx:${rootProject.extra["navigation"]}")
    implementation("androidx.navigation:navigation-ui-ktx:${rootProject.extra["navigation"]}")
    implementation("com.google.dagger:hilt-android:${rootProject.extra["dagger_hil"]}")

    debugImplementation("androidx.fragment:fragment-testing:${rootProject.extra["test_fragment"]}")

    testImplementation("junit:junit:${rootProject.extra["test_junit"]}")
    testImplementation("androidx.arch.core:core-testing:${rootProject.extra["test_core_testing"]}")
    testImplementation("org.robolectric:robolectric:${rootProject.extra["test_roboletric"]}")
    testImplementation("io.mockk:mockk:${rootProject.extra["test_mockk"]}")
    testImplementation("com.google.dagger:hilt-android-testing:${rootProject.extra["dagger_hil"]}")

    kapt("com.google.dagger:hilt-compiler:${rootProject.extra["dagger_hil"]}")
    kaptTest("com.google.dagger:hilt-android-compiler:${rootProject.extra["dagger_hil"]}")
    kaptTest("androidx.databinding:databinding-compiler:${rootProject.extra["test_binding_compiler"]}")
    kaptAndroidTest("androidx.databinding:databinding-compiler:${rootProject.extra["test_binding_compiler"]}")

    androidTestImplementation("androidx.test.ext:junit:${rootProject.extra["android_test_junit"]}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${rootProject.extra["android_test_espresso_core"]}")
}

kapt {
    correctErrorTypes = true
}

hilt {
    enableTransformForLocalTests = true
}

tasks.register("getVersionName") {
    doLast {
        val versionName = android.defaultConfig.versionName
        println(versionName)
    }
}
