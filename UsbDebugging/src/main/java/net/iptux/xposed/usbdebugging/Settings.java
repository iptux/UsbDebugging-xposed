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

import de.robv.android.xposed.XSharedPreferences;

class Settings {
	private static final String PREF_DENY_USB_DEBUGGING = "deny_usb_debugging";
	static final String PREF_VERSION_NAME = "version_name";

	private static class SingletonHelper {
		private static final Settings INSTANCE = new Settings();
	}

	static Settings getInstance() {
		return SingletonHelper.INSTANCE;
	}

	XSharedPreferences prefs;
	private Settings() {
		prefs = new XSharedPreferences(BuildConfig.APPLICATION_ID);
	}

	void reload() {
		prefs.reload();
	}

	boolean isDenyUsbDebugging() {
		return prefs.getBoolean(PREF_DENY_USB_DEBUGGING, true);
	}
}
