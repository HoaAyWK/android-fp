package com.hoavy.orapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.hoavy.orapp.R;

import java.util.Date;

public class SharedHelper {
    private static final String ID = "id";
    private static final String TOKEN = "token";
    private static final String REFRESH_TOKEN = "refreshToken";
    private static final String STOREDDATE = "storedDate";
    private static final String EMAIL = "email";
    private static final String FIRSTNAME = "firstName";
    private static final String LASTNAME = "lastName";
    private static final String PHONE = "phone";
    private static final String DOB = "dateOfBirth";
    private static final String ADDRESS = "address";
    private static final String COUNTRY = "country";
    private static final String AVATAR = "featuredAvatar";
    private static final String BACKGROUND = "featuredBackground";
    private static final String ROLE = "role";

    private static final String RECENT = "recent";
    private static final String SELECTED_POST = "selected_post_id";

    private static SharedHelper instance;

    private final Gson gson = new Gson();
    private final SharedPreferences preferences;

    public SharedHelper(Context context) {
        preferences = context.getSharedPreferences(context.getString(R.string.app_name), context.MODE_PRIVATE);
    }

    public static synchronized SharedHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SharedHelper(context);
        }
        return instance;
    }


    public void setUserInfo(String userId, String token, String refreshToken, String email, String firstName,
                            String lastName, @Nullable String userAvatar, @Nullable String userBackground,
                            @Nullable String phone, @Nullable String address, @Nullable String country, String role,
                            long storedDate) {
        preferences.edit().putString(ID, userId).apply();
        preferences.edit().putString(TOKEN, token).apply();
        preferences.edit().putString(REFRESH_TOKEN, refreshToken).apply();
        preferences.edit().putString(EMAIL, email).apply();
        preferences.edit().putString(FIRSTNAME, firstName).apply();
        preferences.edit().putString(LASTNAME, lastName).apply();
        preferences.edit().putString(AVATAR, userAvatar).apply();
        preferences.edit().putString(BACKGROUND, userBackground).apply();
        preferences.edit().putString(PHONE, phone).apply();
        preferences.edit().putString(ADDRESS, address).apply();
        preferences.edit().putString(COUNTRY, country).apply();
        preferences.edit().putString(ROLE, role).apply();
        preferences.edit().putLong(STOREDDATE, storedDate).apply();
    }

    public boolean isLoggedIn() {
        return preferences.contains(TOKEN);
    }

    public long getStoredDate() {
        return preferences.getLong(STOREDDATE, 0);
    }

    public void setStoredDate(long date) {
        preferences.edit().putLong(STOREDDATE, date).apply();
    }

    @NonNull
    public String getId() { return preferences.getString(ID, ""); }

    public void setId(String id) { preferences.edit().putString(ID, id).apply(); }

    @NonNull
    public String getToken() {
        return preferences.getString(TOKEN, "");
    }

    public void setToken(@NonNull String token) {
        preferences.edit().putString(TOKEN, token).apply();
    }

    @NonNull
    public String getRefreshToken() {
        return preferences.getString(REFRESH_TOKEN, "");
    }

    public void setRefreshToken(@NonNull String refreshToken) {
        preferences.edit().putString(REFRESH_TOKEN, refreshToken).apply();
    }

    @NonNull
    public String getEmail() {
        return preferences.getString(EMAIL, "");
    }

    public void setEmail(@NonNull String email) {
        preferences.edit().putString(EMAIL, email).apply();
    }

    @NonNull
    public String getFistName() {
        return preferences.getString(FIRSTNAME, "");
    }

    public void setFirstName(@NonNull String firstName) {
        preferences.edit().putString(FIRSTNAME, firstName).apply();
    }

    @NonNull
    public String getLastName() {
        return preferences.getString(LASTNAME, "");
    }

    public void setLastName(@NonNull String lastName) {
        preferences.edit().putString(LASTNAME, lastName).apply();
    }

    @NonNull
    public String getPhone() {
        return preferences.getString(PHONE, "");
    }

    public void setPhone(@NonNull String phone) {
        preferences.edit().putString(PHONE, phone).apply();
    }

    @NonNull
    public String getDob() {
        return preferences.getString(DOB, "");
    }

    public void setDob(@NonNull String dob) {
        preferences.edit().putString(DOB, dob).apply();
    }

    @NonNull
    public String getAddress() {
        return preferences.getString(ADDRESS, "");
    }

    public void setAddress(@NonNull String address) {
        preferences.edit().putString(ADDRESS, address).apply();
    }

    @NonNull
    public String getCountry() {
        return preferences.getString(COUNTRY, "");
    }

    public void setCountry(@NonNull String country) {
        preferences.getString(COUNTRY, "");
    }

    @Nullable
    public String getAvatar() {
        return preferences.getString(AVATAR, "");
    }

    public void setAvatar(@Nullable String avatar) {
        if (avatar != null) {
            preferences.edit().putString(AVATAR, avatar).apply();
        }
    }

    @Nullable
    public String getBackground() {
        return preferences.getString(BACKGROUND, "");
    }

    public void setBackground(@Nullable String background) {
        if (background != null) {
            preferences.edit().putString(BACKGROUND, background).apply();
        }
    }

    @Nullable
    public String getRole() {
        return preferences.getString(ROLE, null);
    }

    public void setRole(String role) {
        preferences.edit().putString(ROLE, role).apply();
    }

    public void logout() {
        preferences.edit().clear().apply();
    }
}
