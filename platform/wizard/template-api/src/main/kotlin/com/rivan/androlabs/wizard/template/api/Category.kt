// Adapted from Template in Android Studio Wizard template-plugin package
// https://cs.android.com/android-studio/platform/tools/base/+/mirror-goog-studio-main:wizard/template-plugin/src/com/android/tools/idea/wizard/template/Template.kt;l=22

package com.rivan.androlabs.wizard.template.api

/**
 * Determines to which menu entry the template belongs.
 */
// TODO: Add/remove categories from here as per needed
// TODO: Remove this class if categories won't be needed
enum class Category {
    Activity,
    Fragment,
    Application,
    Folder,
    Service,
    UiComponent,
    Automotive,
    XML,
    Wear,
    AIDL,
    Widget,
    Google,
    Compose,
    Other,
}