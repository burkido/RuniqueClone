plugins {
    alias(libs.plugins.runiqueclone.android.feature.ui)
}

android {
    namespace = "com.burkido.auth.presentation"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.auth.domain)
}