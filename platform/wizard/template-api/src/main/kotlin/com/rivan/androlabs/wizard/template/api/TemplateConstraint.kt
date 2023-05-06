// Adapted from Template in Android Studio Wizard template-plugin package
// https://cs.android.com/android-studio/platform/tools/base/+/mirror-goog-studio-main:wizard/template-plugin/src/com/android/tools/idea/wizard/template/Template.kt;l=54

package com.rivan.androlabs.wizard.template.api

enum class TemplateConstraint {
    AndroidX,
    Kotlin,
    Material3,
    // Compose is used as a Constraint since it implies the need of a specific Kotlin Compiler
    // version
    Compose,
    Aidl
}