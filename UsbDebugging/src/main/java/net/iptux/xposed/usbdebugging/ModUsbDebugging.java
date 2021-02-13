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

import android.os.Build;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class ModUsbDebugging implements IXposedHookLoadPackage {
	private static final String PACKAGE_ANDROID = "android";
	private static final String USB_DEBUGGING_MANAGER_SERVICE = "com.android.server.usb.UsbDebuggingManager";
	private static final String ADB_DEBUGGING_MANAGER_SERVICE = "com.android.server.adb.AdbDebuggingManager";

	private static Settings sSettings = Settings.getInstance();

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		if (PACKAGE_ANDROID.equals(lpparam.packageName)) {
			Utility.d("init");
			if (Build.VERSION.SDK_INT >= 30) {
				findAndHookMethod(ADB_DEBUGGING_MANAGER_SERVICE, lpparam.classLoader, "startConfirmationForNetwork", String.class, String.class, new startConfirmationHook());
				findAndHookMethod(ADB_DEBUGGING_MANAGER_SERVICE, lpparam.classLoader, "startConfirmationForKey", String.class, String.class, new startConfirmationHook());
			} else if (Build.VERSION.SDK_INT >= 29) {
				findAndHookMethod(ADB_DEBUGGING_MANAGER_SERVICE, lpparam.classLoader, "startConfirmation", String.class, String.class, new startConfirmationHook());
			} else {
				findAndHookMethod(USB_DEBUGGING_MANAGER_SERVICE, lpparam.classLoader, "startConfirmation", String.class, String.class, new startConfirmationHook());
			}
		}
	}

	class startConfirmationHook extends XC_MethodHook {
		@Override
		protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
			sSettings.reload();
			if (sSettings.isDenyUsbDebugging()) {
				param.setResult(null);
				Utility.log("deny: %s", (String) param.args[1]);
				if (Build.VERSION.SDK_INT >= 29) {
					XposedHelpers.callMethod(param.thisObject, "denyDebugging");
				} else {
					XposedHelpers.callMethod(param.thisObject, "denyUsbDebugging");
				}
			}
		}
	}
}
