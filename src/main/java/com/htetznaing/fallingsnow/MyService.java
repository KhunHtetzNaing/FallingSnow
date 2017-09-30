package com.htetznaing.fallingsnow;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

public class MyService extends Service {
    WindowManager windowManager;
    LinearLayout linearLayout;
    SnowView snowView;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        linearLayout = new LinearLayout(this);

                XmlPullParser parser = getResources().getXml(R.xml.test);
        AttributeSet attr = Xml.asAttributeSet(parser);
        snowView = new SnowView(this,attr);

        LinearLayout.LayoutParams llParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setBackgroundColor(Color.TRANSPARENT);
        linearLayout.setLayoutParams(llParameters);
        linearLayout.addView(snowView,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);

        final WindowManager.LayoutParams wmParameters = new WindowManager.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        wmParameters.y = 0;
        wmParameters.x = 0;
        wmParameters.gravity = Gravity.CENTER | Gravity.CENTER;
        windowManager.addView(linearLayout,wmParameters);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        windowManager.removeView(linearLayout);
        stopSelf();
    }
}
