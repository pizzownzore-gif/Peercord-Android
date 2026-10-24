package com.peercord.app;

import android.content.Intent;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "KeepAlive")
public class KeepAlivePlugin extends Plugin {
    @PluginMethod
    public void start(PluginCall call) {
        boolean voice = Boolean.TRUE.equals(call.getBoolean("voice", false));
        Intent i = new Intent(getContext(), KeepAliveService.class);
        i.putExtra("voice", voice);
        try {
            if (Build.VERSION.SDK_INT >= 33) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.POST_NOTIFICATIONS"}, 77);
            }
            if (Build.VERSION.SDK_INT >= 26) getContext().startForegroundService(i);
            else getContext().startService(i);
            call.resolve();
        } catch (Exception e) {
            call.reject(String.valueOf(e.getMessage()));
        }
    }

    @PluginMethod
    public void stop(PluginCall call) {
        getContext().stopService(new Intent(getContext(), KeepAliveService.class));
        call.resolve();
    }
}
