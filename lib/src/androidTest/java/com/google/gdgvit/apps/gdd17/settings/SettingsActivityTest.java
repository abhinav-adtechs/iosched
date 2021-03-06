/*
 * Copyright (c) 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.gdgvit.apps.gdd17.settings;

import android.support.test.filters.Suppress;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.google.gdgvit.apps.gdd17.testutils.BaseActivityTestRule;

import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
@Suppress
public class SettingsActivityTest {
    @Rule
    public BaseActivityTestRule<SettingsActivity> mActivityRule =
            new BaseActivityTestRule<>(SettingsActivity.class, null, true);

//    DISABLED: Broken
//    @Test
//    @Suppress // We don't currently show the bottom nav here
//    public void navigation_WhenShown_CorrectItemIsSelected() {
//        NavigationUtils.checkNavigationItemIsSelected(NavigationModel.NavigationItemEnum.SETTINGS);
//    }
}
