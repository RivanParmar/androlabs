package com.rivan.androlabs.wizard.template.impl.activities.common.res.values

fun themeStyles(themeName: String, useAndroidX: Boolean) = """
<resources>
  <style name="$themeName" parent="${if (useAndroidX) "Theme.MaterialComponents.Light" else "Theme.AppCompat.Light"}"/>
</resources>
"""