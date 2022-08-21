/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
/*
 * Copyright 1999-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * $Id: XResources_sv.java,v 1.2.4.1 2005/09/15 08:16:09 suresh_emailid Exp $
 */
package org.openjdk.com.sun.org.apache.xml.internal.utils.res;

//
//  LangResources_sv.properties
//

/**
 * The Swedish resource bundle.
 *
 * @xsl.usage internal
 */
public class XResources_sv extends XResourceBundle {
    private static final Object[][] _contents =
            new Object[][] {
                {"ui_language", "sv"},
                {"help_language", "sv"},
                {"language", "sv"},
                {
                    "alphabet",
                    new CharArrayWrapper(
                            new char[] {
                                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
                                'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                                'Y', 'Z'
                            })
                },
                {
                    "tradAlphabet",
                    new CharArrayWrapper(
                            new char[] {
                                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
                                'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                                'Y', 'Z'
                            })
                },

                // language orientation
                {"orientation", "LeftToRight"},

                // language numbering
                {"numbering", "additive"},

                // largest numerical value
                // {"MaxNumericalValue", new Integer()},
                // These would not be used for EN. Only used for traditional numbering
                // {"numberGroups", new int[]{10,1}},
                // These only used for mutiplicative-additive numbering
                // {"multiplier", "10"},
                // {"multiplierChar", "M"},
                // {"digits", new char[]{'a','b','c','d','e','f','g','h','i'}},
                // {"digits", new
                // char[]{0x10D0,0x10D1,0x10D2,0x10D3,0x10D4,0x10D5,0x10D6,0x10D7,0x10D8}},
                // {"tens", new
                // char[]{0x10D9,0x10DA,0x10DB,0x10DC,0x10DD,0x10DE,0x10DF,0x10E0,0x10E1}},
                // hundreds, etc...
                // {"tables", new String[]{"tens", "digits"}}
            };

    /**
     * Get the association list.
     *
     * @return The association list.
     */
    public Object[][] getContents() {
        return _contents;
    }
}
