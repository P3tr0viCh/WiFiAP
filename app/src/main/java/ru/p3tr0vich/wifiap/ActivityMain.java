package ru.p3tr0vich.wifiap;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class ActivityMain extends Activity {

    private static final String LOG_TAG = "XXX";

    private static final String WIFI_ENABLED_KEY = "WIFI_ENABLED_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate");

        boolean wifiEnabled;
        int textId = R.string.wifi_ap_failed;

        try {
            WifiManagerHelper wifiManagerHelper = new WifiManagerHelper(this);

            switch (wifiManagerHelper.getWifiApState()) {
                case WifiManagerHelper.WIFI_AP_STATE_DISABLING:
                    textId = R.string.wifi_ap_disabling;
                    break;
                case WifiManagerHelper.WIFI_AP_STATE_DISABLED:
                    Log.d(LOG_TAG, "Wi-Fi AP disabled, perform enable");

                    textId = R.string.wifi_ap_enabled;

                    wifiEnabled = wifiManagerHelper.isWifiEnabled();

                    PreferenceManager.getDefaultSharedPreferences(this)
                            .edit()
                            .putBoolean(WIFI_ENABLED_KEY, wifiEnabled)
                            .apply();

                    if (wifiEnabled)
                        wifiManagerHelper.setWifiEnabled(false);

                    wifiManagerHelper.setWifiApEnabled(true);

                    break;
                case WifiManagerHelper.WIFI_AP_STATE_ENABLING:
                    textId = R.string.wifi_ap_enabling;
                    break;
                case WifiManagerHelper.WIFI_AP_STATE_ENABLED:
                    Log.d(LOG_TAG, "Wi-Fi AP enabled, perform disable");

                    textId = R.string.wifi_ap_disabled;

                    wifiManagerHelper.setWifiApEnabled(false);

                    wifiEnabled = PreferenceManager.getDefaultSharedPreferences(this)
                            .getBoolean(WIFI_ENABLED_KEY, false);
                    if (wifiEnabled)
                        wifiManagerHelper.setWifiEnabled(true);
            }
        } catch (Exception e) {
            Log.d(LOG_TAG, e.toString() + ": " + e.getCause().toString());
        }

        Log.d(LOG_TAG, getString(textId));

        Toast.makeText(this, textId, Toast.LENGTH_SHORT).show();

        finish();
    }

    @Override
    protected void onDestroy() {
        Log.d(LOG_TAG, "onDestroy");
        super.onDestroy();
    }
}
