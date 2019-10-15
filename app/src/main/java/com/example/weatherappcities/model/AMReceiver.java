package com.example.weatherappcities.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AMReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent service_intent = new Intent(context, AMService.class);
        String get_your_name = intent.getStringExtra("citynameR");
        service_intent.putExtra("nMW", get_your_name);
        context.startService(service_intent);
    }
}
