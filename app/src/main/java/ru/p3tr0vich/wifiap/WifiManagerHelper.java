package ru.p3tr0vich.wifiap;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import java.lang.reflect.Method;

class WifiManagerHelper {
    public static final int WIFI_AP_STATE_DISABLING = 10;
    public static final int WIFI_AP_STATE_DISABLED = 11;
    public static final int WIFI_AP_STATE_ENABLING = 12;
    public static final int WIFI_AP_STATE_ENABLED = 13;
    public static final int WIFI_AP_STATE_FAILED = 14;

    private final WifiManager mWifiManager;

    private Method setWifiApEnabledMethod;
    private Method getWifiApConfigurationMethod;
    private Method getWifiApStateMethod;
    private Method isWifiApEnabledMethod;

    public WifiManagerHelper(Context context) throws NoSuchMethodException {
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        setWifiApEnabledMethod = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
        getWifiApConfigurationMethod = mWifiManager.getClass().getMethod("getWifiApConfiguration");
        getWifiApStateMethod = mWifiManager.getClass().getMethod("getWifiApState");
        isWifiApEnabledMethod = mWifiManager.getClass().getDeclaredMethod("isWifiApEnabled");
    }

    public boolean setWifiEnabled(boolean enabled) {
        return mWifiManager.setWifiEnabled(enabled);
    }

    public boolean setWifiApEnabled(boolean enabled) throws Exception {
        return (boolean) setWifiApEnabledMethod.invoke(mWifiManager, getWifiApConfiguration(), enabled);
    }

    public WifiConfiguration getWifiApConfiguration() throws Exception {
        return (WifiConfiguration) getWifiApConfigurationMethod.invoke(mWifiManager);
    }

    public int getWifiApState() throws Exception {
        return (Integer) getWifiApStateMethod.invoke(mWifiManager);
    }

    public boolean isWifiEnabled()  {
        return mWifiManager.isWifiEnabled();
    }

    public boolean isWifiApEnabled() throws Exception {
        return (boolean) isWifiApEnabledMethod.invoke(mWifiManager);
    }
}
