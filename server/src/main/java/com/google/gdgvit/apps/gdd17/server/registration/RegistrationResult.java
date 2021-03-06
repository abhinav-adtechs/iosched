/*
 * Copyright 2017 Google Inc. All rights reserved.
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
package com.google.gdgvit.apps.gdd17.server.registration;

/**
 * POJO used as return type for {@link RegistrationEndpoint}. Indicates whether user is registered
 * for the conference as an attendee.
 */
public class RegistrationResult {
  /** Whether the user registered for the conference as an attendee. */
  boolean registered;

  public RegistrationResult() {
  }

  public RegistrationResult(boolean registered) {
    this.registered = registered;
  }

  public boolean isRegistered() {
    return registered;
  }

  public void setRegistered(boolean registered) {
    this.registered = registered;
  }
}
