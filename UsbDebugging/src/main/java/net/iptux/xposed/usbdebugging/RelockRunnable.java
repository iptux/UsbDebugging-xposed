package net.iptux.xposed.usbdebugging;

import android.content.SharedPreferences;
import android.preference.SwitchPreference;

import java.lang.ref.WeakReference;

class RelockRunnable implements Runnable {
	SharedPreferences sharedPreferences;
	WeakReference<SwitchPreference> weakReference;

	RelockRunnable(SharedPreferences sharedPreferences, SwitchPreference switchPreference) {
		this.sharedPreferences = sharedPreferences;
		this.weakReference = new WeakReference<>(switchPreference);
	}

	@Override
	public void run() {
		SwitchPreference preference = weakReference.get();
		if (null != preference) {
			preference.setChecked(true);
		} else {
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putBoolean(Settings.PREF_DENY_USB_DEBUGGING, true);
			editor.apply();
		}
	}
}
