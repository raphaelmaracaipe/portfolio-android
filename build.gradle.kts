// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.0.0" apply false
    id("com.android.library") version "8.0.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("androidx.navigation.safeargs.kotlin") version "2.5.3" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
}

ext {
    // Versions android
    set("compileSdkVersion", 34)
    set("minSdkVersion", 21)
    set("targetSdkVersion", 33)

    // Implementations
    set("core_ktx", "1.10.1")
    set("app_compact", "1.6.1")
    set("material", "1.12.0")
    set("lifecycle_extensions", "2.2.0")
    set("lifecycle_livedata", "2.6.1")
    set("gson", "2.10.1")
    set("navigation", "2.5.3")
    set("okhttp", "5.0.0-alpha.11")
    set("retrofit", "2.9.0")
    set("loading_button", "2.3.0")
    set("rgxgen", "1.4")
    set("http_logger_inteceptor", "4.9.1")
    set("dagger_hil", "2.44")
    set("shimmer", "0.5.0")
    set("room", "2.6.1")
    set("glide", "4.16.0")

    // Tests
    set("test_junit", "4.13.2")
    set("test_core", "1.5.0")
    set("test_mockk", "1.13.5")
    set("test_core_testing", "2.2.0")
    set("test_roboletric", "4.10.2")
    set("test_fragment", "1.5.7")
    set("test_binding_compiler", "8.0.1")
    set("test_coroutines", "1.6.4")
    set("test_mockito", "2.28.2")

    // Android test
    set("android_test_junit", "1.1.5")
    set("android_test_espresso_core", "3.5.1")
}
