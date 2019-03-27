package haiphat.tech.support.customers.Utills;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Map;

import haiphat.tech.support.customers.Utills.ConstantsKey.SharePreferencesKeys;

public class SharePreferencesLoader {
    private static SharedPreferences sharedPreferences;
    private static SharePreferencesLoader instance;
    private String PREFERENCES_NAME = "PREFERENCES_NAME";

    private SharePreferencesLoader() {
    }

    public static SharePreferencesLoader getInstance() {
        if (instance == null) {
            instance = new SharePreferencesLoader();
        }
        return instance;
    }

    public SharedPreferences init(Context pContext) {
        sharedPreferences = pContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    public SharedPreferences.Editor saveValueToSharedPreferences() {
        return sharedPreferences.edit();
    }

    public void saveValue(final String pKeyName, final String pValue) {
        sharedPreferences.edit().putString(pKeyName, pValue).apply();
    }

    public void saveValue(final String pKeyName, final int pValue) {
        sharedPreferences.edit().putInt(pKeyName, pValue).apply();
    }

    public void saveValue(final String pKeyName, final long pValue) {
        sharedPreferences.edit().putLong(pKeyName, pValue).apply();
    }

    public void saveValue(final String pKeyName, final boolean pValue) {
        sharedPreferences.edit().putBoolean(pKeyName, pValue).apply();
    }

    public String getValue(final String pKeyName, final String pDefaultValue) {
        return sharedPreferences.getString(pKeyName, pDefaultValue);
    }

    public int getValue(final String pKeyName, final int pDefaultValue) {
        return sharedPreferences.getInt(pKeyName, pDefaultValue);
    }

    public long getValue(final String pKeyName, final long pDefaultValue) {
        return sharedPreferences.getLong(pKeyName, pDefaultValue);
    }

    public void clear() {
        Map<String, ?> map = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            if (!entry.getKey().contains(SharePreferencesKeys.KEY_GET_LANGUAGE)) {
                Log.d("dmg : ", entry.getKey());
                sharedPreferences.edit().remove(entry.getKey()).apply();
            }
        }
    }


    public void onDestroy() {
        sharedPreferences = null;
        instance = null;
    }

    public boolean getValue(final String pKeyName, final boolean pDefaultValue) {
        return sharedPreferences.getBoolean(pKeyName, pDefaultValue);
    }
}