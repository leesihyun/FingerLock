package com.seahyun.fingerlock;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

/**
 * Created by seahyun on 2017-09-23.
 */

public class ColorTabPreview extends Activity{

    public ColorTabPreview(){};
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        SharedPreferences prefs = getSharedPreferences("user_tab_num", MODE_PRIVATE);
        int num = prefs.getInt("tab_num", 4);
        Log.d("Preview 터치탭 개수 >>", String.valueOf(num));
        if(num == 4){
            setContentView(R.layout.color_tab_preview_four);
        }
        else if(num == 5){
            setContentView(R.layout.color_tab_preview_five);
        }
        else if(num == 6){
            setContentView(R.layout.color_tab_preview_six);
        }
    }

}
