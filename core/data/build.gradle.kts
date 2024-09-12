plugins {
    alias(libs.plugins.runiqueclone.android.library)
    alias(libs.plugins.runiqueclone.jvm.ktor)
}

android {
    namespace = "com.burkido.core.data"
}

dependencies {
    implementation(libs.timber)

    implementation(libs.bundles.koin)

    implementation(projects.core.domain)
    implementation(projects.core.database)
}