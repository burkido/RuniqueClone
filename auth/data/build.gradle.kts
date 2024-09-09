plugins {
    alias(libs.plugins.runiqueclone.android.library)
    alias(libs.plugins.runiqueclone.jvm.ktor)
}

android {
    namespace = "com.burkido.auth.data"
}

dependencies {
    implementation(libs.bundles.koin)

    implementation(projects.auth.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)
}