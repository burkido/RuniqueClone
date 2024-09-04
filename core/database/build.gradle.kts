plugins {
    alias(libs.plugins.runiqueclone.android.library)
    alias(libs.plugins.runiqueclone.android.room)
}

android {
    namespace = "com.burkido.core.database"
}

dependencies {
    implementation(libs.org.mongodb.bson)
    implementation(projects.core.domain)
}