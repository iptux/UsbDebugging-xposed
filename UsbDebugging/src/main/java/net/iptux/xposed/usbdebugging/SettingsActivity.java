/*
 * UsbDebugging - Deny USB debugging on new computers
 * Copyright (C) 2019  Tommy Alex  <iptux7@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.iptux.xposed.usbdebugging;

import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.preference.SwitchPreference;

public class SettingsActivity extends PreferenceActivity
		implements SharedPreferences.OnSharedPreferenceChangeListener {
	SwitchPreference denyUsbDebuggingPreference;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getPreferenceManager().setSharedPreferencesMode(MODE_WORLD_READABLE);
		getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
		addPreferencesFromResource(R.xml.preferences);

		denyUsbDebuggingPreference = (SwitchPreference) findPreference(Settings.PREF_DENY_USB_DEBUGGING);
		findPreference(Settings.PREF_VERSION_NAME).setSummary(BuildConfig.VERSION_NAME);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		switch (key) {
		case Settings.PREF_DENY_USB_DEBUGGING:
			if (!sharedPreferences.getBoolean(key, true)) {
				scheduleRelock(sharedPreferences);
			}
			break;
		}
	}

	void scheduleRelock(SharedPreferences sharedPreferences) {
		if (sharedPreferences.getBoolean(Settings.PREF_TEMPORARY_ALLOW, true)) {
			String value = sharedPreferences.getString(Settings.PREF_TEMPORARY_ALLOW_INTETVAL, "10");
			long interval = Long.parseLong(value);
			RelockRunnable runnable = new RelockRunnable(sharedPreferences, denyUsbDebuggingPreference);
			findViewById(android.R.id.content).postDelayed(runnable, interval * 1000);
		}
	}
}
