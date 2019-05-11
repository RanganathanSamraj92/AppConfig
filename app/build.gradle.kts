import org.gradle.internal.impldep.com.amazonaws.util.IOUtils.release

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    kotlin("kapt")
}

android {
    compileSdkVersion(AppConfig.targetSdkVersion)
    flavorDimensions("default")
    defaultConfig {
        minSdkVersion(AppConfig.minSdkVersion)
        targetSdkVersion(AppConfig.targetSdkVersion)
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        maybeCreate("release").apply {
            isMinifyEnabled = false
        }
    }


}

dependencies {
    /*kotlin*/
    implementation(Libs.Kotlin.kotlin_std)
    /*androidx*/
    implementation(Libs.Kotlin.core_ktx)
    implementation(Libs.Support.appcompat)
    implementation(Libs.Support.constraint)

    implementation(Libs.picasso)
    implementation(Libs.runtimePermissions)
    implementation(Libs.material)
    implementation(Libs.coroutines)
}


//Step II: Add the below publish details
/*publish {

    def groupProjectID = 'dev.app.baseappconfig'
    def artifactProjectID = 'baseappconfig'
    def publishVersionID = '1.0.0'

    userOrg = 'androidplant'
    repoName = 'BaseAppConfig'
    groupId = groupProjectID
    artifactId = artifactProjectID
    publishVersion = publishVersionID
    desc = 'Android library for displaying toast based on msg from caller. '
    website = 'https://github.com/RanganathanSamraj92/AppConfig'
}*/

