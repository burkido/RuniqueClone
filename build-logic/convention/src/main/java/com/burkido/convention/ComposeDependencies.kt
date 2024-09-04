package com.burkido.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project

fun DependencyHandlerScope.addUiLayerDependencies(target: Project) {
    "implementation"(project(":core:presentation:ui"))
    "implementation"(project(":core:presentation:designsystem"))

    "implementation"(target.libs.findBundle("koin.compose").get())
    "implementation"(target.libs.findBundle("compose").get())
    "debugImplementation"(target.libs.findBundle("compose.debug").get())
    "androidTestImplementation"(target.libs.findLibrary("androidx.compose.ui.test.junit4").get())
}