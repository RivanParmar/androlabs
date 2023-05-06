// Adapted from Android Studio template-plugin package
// https://cs.android.com/android-studio/platform/tools/base/+/mirror-goog-studio-main:wizard/template-plugin/src/com/android/tools/idea/wizard/template/Template.kt

// Modifications Copyright (C) 2023 Rivan Parmar

package com.rivan.androlabs.wizard.template.api

typealias Recipe = RecipeExecutor.(TemplateData) -> Unit

/**
 * Describes a template available in the wizard.
 *
 * This interface is used by the wizard in 3 steps:
 * 1. User is presented an option to select a template when creating a new Project,
 * Selection of the Template depends on fields like [Category], [FormFactor], etc.
 * 2. After the user selects a template, the wizards will call [Parameter]s to build the UI.
 * 3. Recipe is executed with parameters' values supplied by the user in the UI.
 **/
interface Template {
    /**
     * A template name which is also used as identified.
     */
    val name: String
    /**
     * A textual description which is shown in wizards UIs.
     */
    val description: String
    /**
     * Address of an external website with more details about the template.
     */
    val documentationUrl: String?
    /** Returns a thumbnail which are drawn in the UI. It will be called every time when any
     * parameter is updated.
     */
    // TODO: Maybe use Drawables or VectorDrawables
    fun thumb(): Thumb

    /**
     * When a [Template] is chosen by the user, the [widgets] are used by the Wizards to build the
     * user UI.
     *
     * Usually, it displays an input for [Parameter].
     */
    val widgets: Collection<Widget<*>>
    /**
     * Usually, a user provides [Parameter.value]s by interaction with the UI [widgets].
     */
    val parameters: Collection<Parameter<*>> get() =
        widgets.filterIsInstance<ParameterWidget<*>>().map { it.parameter }

    /**
     * Recipe used to generate this [Template] output. It will be called after the user provides
     * values for all [Parameter]s.
     */
    val recipe: Recipe

    /**
     * Minimum sdk version required to build this template.
     */
    val minSdk: Int
    /**
     * Determines to which menu entry the template belongs.
     */
    val category: Category
    /**
     * Determines to which template category the template belongs. Templates with particular
     * template categories may only be rendered in the project of corresponding [Category].
     *
     * Note: Do not confuse [TemplateCategory] with [Category]. See [TemplateCategory] for more
     * information.
     */
    val templateCategory: TemplateCategory
    /**
     * Conditions under which the template may be rendered. For example, some templates only
     * support AndroidX
     */
    val constraints: Collection<TemplateConstraint>
    val useGenericInstrumentedTests: Boolean
    val useGenericLocalTests: Boolean

    /**
     * Represent absence of a [Template] (null object pattern).
     */
    companion object NoActivity: Template {
        override val name: String = "No Activity"
        override val description: String = "Creates a new empty project"
        override val documentationUrl: String? = null
        override val widgets: Collection<Widget<*>> = listOf()
        override val recipe: Recipe get() = throw UnsupportedOperationException()
        override val minSdk: Int = 1
        override val category: Category = Category.Activity
        override val templateCategory: TemplateCategory = TemplateCategory.Mobile
        override val constraints: Collection<TemplateConstraint> = listOf()
        override val useGenericInstrumentedTests: Boolean = true
        override val useGenericLocalTests: Boolean = true
        override fun thumb(): Thumb = Thumb.NoThumb
    }
}