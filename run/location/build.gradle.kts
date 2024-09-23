plugins {

    alias(libs.plugins.runiqueclone.android.library)
}

android {
    namespace = "com.burkido.run.location"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.google.android.gms.play.services.location)
    implementation(libs.bundles.koin)

    implementation(projects.core.domain)
    implementation(projects.run.domain)
}