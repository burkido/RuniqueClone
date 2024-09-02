plugins {
    alias(libs.plugins.runiqueclone.android.library)
}

android {
    namespace = "com.burkido.run.network"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
}