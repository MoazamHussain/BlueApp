buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.0")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")

    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.50" apply false
}
ext {
    val appName by extra { "MainApp" }
    val applicationId by extra { "com.example.neoapplication" }
    val versionMajor by extra { 1 }
    val versionMinor by extra { 0 }
    val versionPatch by extra { 0 }
    val versionCode: Int by extra { versionMajor * 10000 + versionMinor * 100 + versionPatch }
    val versionName: String by extra { "${versionMajor}.${versionMinor}.${versionPatch}" }
}
//allprojects {
//    repositories {
//        maven { url =uri("https://oss.sonatype.org/content/repositories/snapshots/") }
//    }
//}