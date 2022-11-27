-repackageclasses net.iptux.xposed.usbdebugging

# remove debug logging
-assumenosideeffects class net.iptux.xposed.usbdebugging.Utility {
	*** d(...);
}
