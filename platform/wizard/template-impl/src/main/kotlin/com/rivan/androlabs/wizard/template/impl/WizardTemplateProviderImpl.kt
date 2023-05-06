package com.rivan.androlabs.wizard.template.impl

import com.rivan.androlabs.wizard.template.api.Template
import com.rivan.androlabs.wizard.template.api.WizardTemplateProvider
import com.rivan.androlabs.wizard.template.impl.activities.basicActivity.basicActivityTemplate
import com.rivan.androlabs.wizard.template.impl.activities.emptyActivity.emptyActivityTemplate

class WizardTemplateProviderImpl : WizardTemplateProvider() {
    override fun getTemplates(): List<Template> = listOf(
        basicActivityTemplate,
        emptyActivityTemplate
    )
}