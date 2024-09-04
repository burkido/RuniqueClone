plugins {
    alias(libs.plugins.runiqueclone.jvm.library)
}

dependencies {
    implementation(projects.core.domain)
}