plugins {
    alias(libs.plugins.runiqueclone.android.library)
    alias(libs.plugins.runiqueclone.jvm.ktor)
}

android {
    namespace = "com.burkido.run.network"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
}