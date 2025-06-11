/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rivan.androlabs.wizard.template.api

enum class MaterialColor(val colorName: String, val color: String) {
    GRAY_400("gray_400", "FFBDBDBD"),
    GRAY_600("gray_600", "FF757575"),
    LIGHT_BLUE_50("light_blue_50", "FFE1F5FE"),
    LIGHT_BLUE_200("light_blue_200", "FF81D4FA"),
    LIGHT_BLUE_400("light_blue_400", "FF29B6F6"),
    LIGHT_BLUE_600("light_blue_600", "FF039BE5"),
    LIGHT_BLUE_900("light_blue_900", "FF01579B"),
    LIGHT_BLUE_A200("light_blue_A200", "FF40C4FF"),
    LIGHT_BLUE_A400("light_blue_A400", "FF00B0FF"),
    PURPLE_40("purple_40", "FF6650a4"),
    PURPLE_80("purple_80", "FFD0BCFF"),
    PURPLE_GREY_40("purple_grey_40", "FF625b71"),
    PURPLE_GREY_80("purple_grey_80", "FFCCC2DC"),
    PURPLE_200("purple_200", "FFBB86FC"),
    PURPLE_500("purple_500", "FF6200EE"),
    PURPLE_700("purple_700", "FF3700B3"),
    PINK_40("pink_40", "FF7D5260"),
    PINK_80("pink_80", "FFEFB8C8"),
    RED_400("red_400", "FFCF6679"),
    TEAL_200("teal_200", "FF03DAC5"),
    TEAL_700("teal_700", "FF018786"),
    BLACK("black", "FF000000"),
    WHITE("white", "FFFFFFFF");

    fun xmlElement(): String = """<color name="$colorName">#$color</color>"""

    fun kotlinComposeVal(): String = """val ${underscoreToCamelCase(colorName)} = Color(0x$color)"""
}