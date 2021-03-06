/*
 * Copyright 2015 Google Inc. All rights reserved.
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

package com.google.gdgvit.apps.gdd17.debug;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.samples.apps.iosched.lib.R;

/**
 * Activity that implements the debug UI. This UI has buttons and other widgets
 * that allows the user to invoke tests and tweak other debug settings.
 * <p>
 * This screen is only accessible if the app is built with the debug
 * configuration.
 */
public class DebugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug_act);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
