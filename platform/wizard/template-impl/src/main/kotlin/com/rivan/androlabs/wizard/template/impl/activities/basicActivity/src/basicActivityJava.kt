/*
 * Copyright (C) 2019 The Android Open Source Project
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

package com.rivan.androlabs.wizard.template.impl.activities.basicActivity.src

import com.rivan.androlabs.wizard.template.api.Language
import com.rivan.androlabs.wizard.template.api.getMaterialComponentName
import com.rivan.androlabs.wizard.template.api.renderIf
import com.rivan.androlabs.wizard.template.impl.activities.common.findViewById
import com.rivan.androlabs.wizard.template.impl.activities.common.importViewBindingClass
import com.rivan.androlabs.wizard.template.impl.activities.common.layoutToViewBindingClass

fun basicActivityJava(
    isNewProject: Boolean,
    applicationPackage: String?,
    packageName: String,
    useAndroidX: Boolean,
    activityClass: String,
    layoutName: String,
    menuName: String,
    navHostFragmentId: String,
    isViewBindingSupported: Boolean
): String {
    val applicationPackageBlock = renderIf(applicationPackage != null) { "import $applicationPackage.R;" }
    val newProjectImportBlock = renderIf(isNewProject) {
        """
import android.view.Menu;
import android.view.MenuItem;
"""
    }

    val newProjectBlock2 = renderIf(isNewProject) {
        """
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.${menuName}, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    """
    }

    val contentViewBlock = if (isViewBindingSupported) """
     binding = ${layoutToViewBindingClass(layoutName)}.inflate(getLayoutInflater());
     setContentView(binding.getRoot());
  """ else "setContentView(R.layout.$layoutName);"

    return """
package ${(packageName)};

import android.os.Bundle;
import ${getMaterialComponentName("android.support.design.widget.Snackbar", useAndroidX)};
import ${getMaterialComponentName("android.support.v7.app.AppCompatActivity", useAndroidX)};
import android.view.View;
import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
${importViewBindingClass(isViewBindingSupported, packageName, applicationPackage, layoutName, Language.Java)}

$newProjectImportBlock
$applicationPackageBlock

public class ${activityClass} extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
${renderIf(isViewBindingSupported) {"""
    private ${layoutToViewBindingClass(layoutName)} binding;
"""}}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        $contentViewBlock
        setSupportActionBar(${findViewById(Language.Java, isViewBindingSupported, id = "toolbar")});

        NavController navController = Navigation.findNavController(this, R.id.${navHostFragmentId});
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        ${findViewById(
        Language.Java,
        isViewBindingSupported = isViewBindingSupported,
        id = "fab")}.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });
    }
$newProjectBlock2

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.${navHostFragmentId});
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
"""
}