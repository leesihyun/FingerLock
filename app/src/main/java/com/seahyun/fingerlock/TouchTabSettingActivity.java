package com.seahyun.fingerlock;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by seahyun on 2017-09-20.
 */

public class TouchTabSettingActivity extends Activity {


    private static int tab_num;
    NiceSpinner tab_num_spinner;
    ArrayList<String> num_list;


    public TouchTabSettingActivity(){};


    public int getTab_num(){
        return tab_num;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Log.d("입장 >> ", "스피너 초기화 전");
        setContentView(R.layout.touch_tab_setting);
        initSpinner();
        //tab_num_spinner.setSelection(0);


        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String number = num_list.get(position);
                if(number.contains("4"))
                    tab_num = 4;
                else if(number.contains("5"))
                    tab_num = 5;
                else if(number.contains("6"))
                    tab_num = 6;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(TouchTabSettingActivity.this, "기본값으로 설정됩니다", Toast.LENGTH_SHORT).show();
                tab_num = 4;
            }
        };
    }

    public void initSpinner(){
        Log.d("init >> ", "스피너 초기화");
        tab_num_spinner = (NiceSpinner) findViewById(R.id.touch_tab_num);

        //String[] item = {"4개", "5개", "6개"};
        List<String> item = new LinkedList<>(Arrays.asList("4개", "5개", "6개"));
        tab_num_spinner.attachDataSource(item);
        /*num_list = new ArrayList<String>();
        for(int i=0; i<item.length; i++){
            num_list.add(item[i]);
            Log.d("추가 >> ", item[i]);
        }

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tab_num_spinner.setAdapter(adapter);*/
    }

}
