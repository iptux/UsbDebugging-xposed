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

import de.robv.android.xposed.XposedBridge;

final class Utility {
	static final String TAG = "UsbDebugging: ";

	static void d(String fmt, Object... args) {
		if (BuildConfig.DEBUG) {
			log(fmt, args);
		}
	}

	static void log(String fmt, Object... args) {
		XposedBridge.log(TAG + String.format(fmt, args));
	}
}
