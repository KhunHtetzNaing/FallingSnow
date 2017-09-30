package com.htetznaing.fallingsnow;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

public class SnowView extends View {
    int NUM_SNOWFLAKES = 150;
    int DELAY = 5;

    private SnowFlake[] snowflakes;

    public SnowView(Context context) {
        super(context);
    }

    public SnowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SnowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void resize(int width, int height) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("myFile",Context.MODE_PRIVATE);
        int color = sharedPreferences.getInt("color",Color.WHITE);
        NUM_SNOWFLAKES = sharedPreferences.getInt("count",150);
        DELAY = Dly(sharedPreferences);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);

        snowflakes = new SnowFlake[NUM_SNOWFLAKES];
        for (int i = 0; i < NUM_SNOWFLAKES; i++) {
            snowflakes[i] = SnowFlake.create(width, height, paint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            resize(w, h);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (SnowFlake snowFlake : snowflakes) {
            snowFlake.draw(canvas);
        }
        getHandler().postDelayed(runnable, DELAY);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    public int Dly(SharedPreferences sharedPreferences){
        int s = sharedPreferences.getInt("speed",0);
        int result = 0;
        switch (s){
            case 0 : result = 100;break;
            case 1 : result=99;break;
            case 2: result=98;break;
            case 3 : result=97;break;
            case 4:result=96;break;
            case 5:result=95;break;
            case 6:result=94;break;
            case 7:result=93;break;
            case 8:result=92;break;
            case 9:result=91;break;
            case 10:result=90;break;
            case 11:result=89;break;
            case 12:result=88;break;
            case 13:result=87;break;
            case 14:result=86;break;
            case 15:result=85;break;
            case 16:result=84;break;
            case 17:result=83;break;
            case 18:result=82;break;
            case 19:result=81;break;
            case 20:result=80;break;
            case 21:result=79;break;
            case 22:result=78;break;
            case 23:result=77;break;
            case 24:result=76;break;
            case 25:result=75;break;
            case 26:result=74;break;
            case 27:result=73;break;
            case 28:result=72;break;
            case 29:result=71;break;
            case 30:result=70;break;
            case 31:result=69;break;
            case 32:result=68;break;
            case 33:result=67;break;
            case 34:result=66;break;
            case 35:result=65;break;
            case 36:result=64;break;
            case 37:result=63;break;
            case 38:result=62;break;
            case 39:result=61;break;
            case 40:result=60;break;
            case 41:result=59;break;
            case 42:result=58;break;
            case 43:result=57;break;
            case 44:result=56;break;
            case 45:result=55;break;
            case 46:result=54;break;
            case 47:result=53;break;
            case 48:result=52;break;
            case 49:result=51;break;
            case 50:result=50;break;
            case 51:result=49;break;
            case 52:result=48;break;
            case 53:result=47;break;
            case 54:result=46;break;
            case 55:result=45;break;
            case 56:result=44;break;
            case 57:result=43;break;
            case 58:result=42;break;
            case 59:result=41;break;
            case 60:result=40;break;
            case 61:result=39;break;
            case 62:result=38;break;
            case 63:result=37;break;
            case 64:result=36;break;
            case 65:result=35;break;
            case 66:result=34;break;
            case 67:result=33;break;
            case 68:result=32;break;
            case 69:result=31;break;
            case 70:result=30;break;
            case 71:result=29;break;
            case 72:result=28;break;
            case 73:result=27;break;
            case 74:result=26;break;
            case 75:result=25;break;
            case 76:result=24;break;
            case 77:result=23;break;
            case 78:result=22;break;
            case 79:result=21;break;
            case 80:result=20;break;
            case 81:result=19;break;
            case 82:result=18;break;
            case 83:result=17;break;
            case 84:result=16;break;
            case 85:result=15;break;
            case 86:result=14;break;
            case 87:result=13;break;
            case 88:result=12;break;
            case 89:result=11;break;
            case 90:result=10;break;
            case 91:result=9;break;
            case 92:result=8;break;
            case 93:result=7;break;
            case 94:result=6;break;
            case 95:result=5;break;
            case 96:result=4;break;
            case 97:result=3;break;
            case 98:result=2;break;
            case 99:result=1;break;
            case 100:result=0;break;
        }
        return result;
    }
}
