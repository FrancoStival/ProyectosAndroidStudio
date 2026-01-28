package com.example.pt5;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.MultiSelectListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import java.util.Set;

public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        EditTextPreference namePref = findPreference("full_name");
        if (namePref != null) {
            namePref.setOnPreferenceChangeListener(this);
            CharSequence cur = namePref.getText();
            namePref.setSummaryProvider(preference -> {
                String s = ((EditTextPreference)preference).getText();
                return TextUtils.isEmpty(s) ? "No establecido" : s;
            });
        }

        MultiSelectListPreference multi = findPreference("reply");
        if (multi != null) {
            multi.setSummaryProvider(preference -> {
                Set<String> values = ((MultiSelectListPreference) preference).getValues();
                if (values == null || values.isEmpty()) return "Cap seleccionat";
                return String.join(", ", values);
            });
        }

        SwitchPreferenceCompat sw = findPreference("news_notification");
        if (sw != null) {
            sw.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean on = (Boolean) newValue;
                if (on) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        Toast.makeText(getContext(), "Comprovar permisos de notificacions a l'app", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            });
        }

        ListPreference lp = findPreference("fav_movie");
        if (lp != null) lp.setSummaryProvider(ListPreference.SimpleSummaryProvider.getInstance());
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if ("full_name".equals(preference.getKey())) {
            String s = (newValue instanceof String) ? ((String) newValue).trim() : "";
            if (s.isEmpty()) {
                Toast.makeText(getContext(), "El nom no pot estar buit.", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (s.length() > 80) {
                Toast.makeText(getContext(), "Nom massa llarg.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}
