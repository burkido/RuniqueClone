plugins {
    alias(libs.plugins.runiqueclone.android.library)
}

android {
    namespace = "com.burkido.run.location"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
}