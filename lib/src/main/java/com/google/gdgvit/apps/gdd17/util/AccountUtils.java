/*
 * Copyright (c) 2017 Google Inc.
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

package com.google.gdgvit.apps.gdd17.util;

import android.accounts.Account;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableNotifiedException;
import com.google.android.gms.common.Scopes;
import com.google.gdgvit.apps.gdd17.provider.ScheduleContract;

import java.io.IOException;

import static com.google.gdgvit.apps.gdd17.util.LogUtils.LOGD;
import static com.google.gdgvit.apps.gdd17.util.LogUtils.LOGE;
import static com.google.gdgvit.apps.gdd17.util.LogUtils.LOGI;
import static com.google.gdgvit.apps.gdd17.util.LogUtils.LOGV;
import static com.google.gdgvit.apps.gdd17.util.LogUtils.LOGW;
import static com.google.gdgvit.apps.gdd17.util.LogUtils.makeLogTag;

/**
 * Account and login utilities. This class manages a local shared preferences object
 * that stores which account is currently active, and can store associated information
 * such as Google+ profile info (name, image URL, cover URL) and also the auth token
 * associated with the account.
 */
public class AccountUtils {
    private static final String TAG = LogUtils.makeLogTag(AccountUtils.class);

    /**
     * Code used in Google Sign In.
     */
    public static final int RC_SIGN_IN = 9009;

    public static final String DEFAULT_OAUTH_PROVIDER = "google";

    public static final String PREF_ACTIVE_ACCOUNT = "chosen_account";

    // These names are are prefixes; the account is appended to them.
    public static final String PREFIX_PREF_AUTH_TOKEN = "auth_token_";

    private static final String PREFIX_PREF_FCM_KEY = "gcm_key_";


    /**
     * Used for accessing the account display name stored in {@link SharedPreferences}.
     */
    private static final String PREF_ACTIVE_ACCOUNT_DISPLAY_NAME =
            "pref_active_account_display_name" + Constants.CONFERENCE_YEAR_PREF_POSTFIX;

    /**
     * Used for accessing the account photo url stored in {@link SharedPreferences}.
     */
    private static final String PREF_ACTIVE_ACCOUNT_PHOTO_URL =
            "pref_active_account_photo_url" + Constants.CONFERENCE_YEAR_PREF_POSTFIX;

    /**
     * Used for accessing the account id stored in {@link SharedPreferences}.
     */
    private static final String PREF_ACTIVE_ACCOUNT_ID =
            "pref_active_account_id" + Constants.CONFERENCE_YEAR_PREF_POSTFIX;

    public static final String AUTH_SCOPES[] = {
            Scopes.PROFILE};

    static final String AUTH_TOKEN_TYPE;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("oauth2:");
        for (String scope : AUTH_SCOPES) {
            sb.append(scope);
            sb.append(" ");
        }
        AUTH_TOKEN_TYPE = sb.toString();
    }

    private static SharedPreferences getSharedPreferences(final Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Specify whether the app has an active account set.
     *
     * @param context Context used to lookup {@link SharedPreferences} the value is stored with.
     */
    public static boolean hasActiveAccount(final Context context) {
        return !TextUtils.isEmpty(getActiveAccountName(context));
    }

    /**
     * Return the accountName the app is using as the active Google Account.
     *
     * @param context Context used to lookup {@link SharedPreferences} the value is stored with.
     */
    public static String getActiveAccountName(final Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getString(PREF_ACTIVE_ACCOUNT, null);
    }

    /**
     * Return the {@code Account} the app is using as the active Google Account.
     *
     * @param context Context used to lookup {@link SharedPreferences} the value is stored with.
     */
    public static Account getActiveAccount(final Context context) {
        String account = getActiveAccountName(context);
        if (account != null) {
            return new Account(account, GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
        } else {
            return null;
        }
    }

    public static void setActiveAccount(final Context context, final String accountName) {
        LogUtils.LOGD(TAG, "Set active account to: " + accountName);
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putString(PREF_ACTIVE_ACCOUNT, accountName).apply();
    }

    /**
     * Returns the display name associated with the active account.
     *
     * @param context Context used to lookup {@link SharedPreferences}
     */
    public static String getActiveAccountDisplayName(final Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getString(PREF_ACTIVE_ACCOUNT_DISPLAY_NAME, "");
    }

    /**
     * Sets the display name associated with the active account.
     *
     * @param context     Context used to lookup {@link SharedPreferences}
     * @param id The id associated with an account.
     */
    public static void setActiveAccountId(final Context context,
            final String id) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putString(PREF_ACTIVE_ACCOUNT_ID, id).apply();
    }

    /**
     * Returns the display name associated with the active account.
     *
     * @param context Context used to lookup {@link SharedPreferences}
     */
    public static String getActiveAccountId(final Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getString(PREF_ACTIVE_ACCOUNT_ID, "");
    }

    /**
     * Sets the display name associated with the active account.
     *
     * @param context     Context used to lookup {@link SharedPreferences}
     * @param displayName The display name associated with an account.
     */
    public static void setActiveAccountDisplayName(final Context context,
                                                   final String displayName) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putString(PREF_ACTIVE_ACCOUNT_DISPLAY_NAME, displayName).apply();
    }
    
    /**
     * Returns the photo url associated with the active account.
     *
     * @param context Context used to lookup {@link SharedPreferences}
     */
    public static Uri getActiveAccountPhotoUrl(final Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        String photoUrl = sp.getString(PREF_ACTIVE_ACCOUNT_PHOTO_URL, null);
        if (photoUrl != null) {
            return Uri.parse(photoUrl);
        }
        return null;
    }

    /**
     * Stores the photo url associated with the active account.
     *
     * @param context  Context used to lookup {@link SharedPreferences}
     * @param photoUrl The photo url.
     */
    public static void setActiveAccountPhotoUrl(final Context context, final Uri photoUrl) {
        SharedPreferences sp = getSharedPreferences(context);
        if (photoUrl != null) {
            sp.edit().putString(PREF_ACTIVE_ACCOUNT_PHOTO_URL, photoUrl.toString()).apply();
        }
    }

    public static void clearActiveAccount(final Context context) {
        LogUtils.LOGD(TAG, "Clearing active account");
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit()
          .remove(PREF_ACTIVE_ACCOUNT)
          .remove(PREF_ACTIVE_ACCOUNT_DISPLAY_NAME)
          .remove(PREF_ACTIVE_ACCOUNT_PHOTO_URL)
          .apply();
    }

    protected static String makeAccountSpecificPrefKey(Context ctx, String prefix) {
        return hasActiveAccount(ctx) ? makeAccountSpecificPrefKey(getActiveAccountName(ctx),
                prefix) : null;
    }

    protected static String makeAccountSpecificPrefKey(String accountName, String prefix) {
        return prefix + accountName;
    }

    public static String getAuthToken(final Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return hasActiveAccount(context) ?
                sp.getString(makeAccountSpecificPrefKey(context, PREFIX_PREF_AUTH_TOKEN), null) :
                null;
    }

    public static void setAuthToken(final Context context, final String accountName,
            final String authToken) {
        LogUtils.LOGI(TAG, "Auth token of length "
                + (TextUtils.isEmpty(authToken) ? 0 : authToken.length()) + " for "
                + accountName);
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putString(makeAccountSpecificPrefKey(accountName, PREFIX_PREF_AUTH_TOKEN),
                authToken).apply();
        LogUtils.LOGV(TAG, "Auth Token: " + authToken);
    }

    public static void setAuthToken(final Context context, final String authToken) {
        if (hasActiveAccount(context)) {
            setAuthToken(context, getActiveAccountName(context), authToken);
        } else {
            LogUtils.LOGE(TAG, "Can't set auth token because there is no chosen account!");
        }
    }

    static void invalidateAuthToken(final Context context) {
        setAuthToken(context, null);
    }

    public static boolean hasToken(final Context context, final String accountName) {
        SharedPreferences sp = getSharedPreferences(context);
        return !TextUtils.isEmpty(sp.getString(makeAccountSpecificPrefKey(accountName,
                PREFIX_PREF_AUTH_TOKEN), null));
    }

    public static void refreshAuthToken(Context mContext) {
        invalidateAuthToken(mContext);
        tryAuthenticateWithErrorNotification(mContext, ScheduleContract.CONTENT_AUTHORITY);
    }

    static void tryAuthenticateWithErrorNotification(Context context, String syncAuthority) {
        try {
            String accountName = getActiveAccountName(context);
            if (accountName != null) {
                LogUtils.LOGI(TAG, "Requesting new auth token (with notification)");
                final String token = GoogleAuthUtil
                        .getTokenWithNotification(context, accountName, AUTH_TOKEN_TYPE,
                                null, syncAuthority, null);
                setAuthToken(context, token);
            } else {
                LogUtils.LOGE(TAG, "Can't try authentication because no account is chosen.");
            }

        } catch (UserRecoverableNotifiedException e) {
            // Notification has already been pushed.
            LogUtils.LOGW(TAG, "User recoverable exception. Check notification.", e);
        } catch (GoogleAuthException e) {
            // This is likely unrecoverable.
            LogUtils.LOGE(TAG, "Unrecoverable authentication exception: " + e.getMessage(), e);
        } catch (IOException e) {
            LogUtils.LOGE(TAG, "transient error encountered: " + e.getMessage());
        }
    }

    public static String sanitizeUserId(String key) {
        if (key == null) {
            return "(null)";
        } else if (key.length() > 8) {
            return key.substring(0, 4) + "........" + key.substring(key.length() - 4);
        } else {
            return "........";
        }
    }
}
