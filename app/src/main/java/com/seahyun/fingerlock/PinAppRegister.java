package com.seahyun.fingerlock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.attr.tag;

/**
 * Created by seahyun on 2017-09-23.
 */

public class PinAppRegister extends AppCompatActivity implements View.OnClickListener {


    public PinAppRegister() {
    }

    ;

    public String[] Message = new String[6];
    public String[] PackageName = new String[6];
    public Spinner spinner1;
    public Spinner spinner2;
    public Spinner spinner3;
    public Spinner spinner4;
    public ArrayList<String> list = new ArrayList<>();
 //   int num1;
   // int num2;
    //int num3;
   // int num4;

    private int num[] = new int[4];
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);


        ActionBar actionBar = getSupportActionBar();
        //ActionBar title 변경
        actionBar.setTitle("PIN 어플 등록");
        // ActionBar 배경색 변경
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.DarkBlue));

        getWindow().setStatusBarColor(getResources().getColor(R.color.DarkBlue));

        setContentView(R.layout.pin_app_register);

        spinner1 = (Spinner)findViewById(R.id.spinner1);
        spinner2 = (Spinner)findViewById(R.id.spinner2);
        spinner3 = (Spinner)findViewById(R.id.spinner3);
        spinner4 = (Spinner)findViewById(R.id.spinner4);

        //num변수 초기화
        for(int i=0; i<4; i++)
            num[i] = 0;
        SharedPreferences prefs = getSharedPreferences("num", MODE_PRIVATE);

        int num1 = prefs.getInt("num1", 0);
        int num2 = prefs.getInt("num2", 0);
        int num3 = prefs.getInt("num3", 0);
        int num4 = prefs.getInt("num4", 0);

        Log.d("AAA >> ", String.valueOf(num1));
        Log.d("AAA >> ", String.valueOf(num2));
        Log.d("AAA >> ", String.valueOf(num3));
        Log.d("AAA >> ", String.valueOf(num4));

        list.add("0");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");

        ArrayAdapter spinnerAdapter1;
        spinnerAdapter1 = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);
        spinner1.setAdapter(spinnerAdapter1);
        ArrayAdapter spinnerAdapter2;
        spinnerAdapter2 = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);
        spinner2.setAdapter(spinnerAdapter2);
        ArrayAdapter spinnerAdapter3;
        spinnerAdapter3 = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);
        spinner3.setAdapter(spinnerAdapter3);
        ArrayAdapter spinnerAdapter4;
        spinnerAdapter4 = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);
        spinner4.setAdapter(spinnerAdapter4);

        spinner1.setSelection(num1);
        spinner2.setSelection(num2);
        spinner3.setSelection(num3);
        spinner4.setSelection(num4);

        //기존의 바로가기 설정 어플 목록 가져오기
        applicationList();


    }

    @Override
    public void onClick(View v) {
        SharedPreferences prefs = getSharedPreferences("select_state", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        final SharedPreferences prefs5 = getSharedPreferences("num", MODE_PRIVATE);
        final SharedPreferences.Editor prefsEditor5 = prefs5.edit();
        //SharedPreferences prefs5 = getSharedPreferences("num", MODE_PRIVATE);
   // final SharedPreferences.Editor prefsEditor5 = prefs5.edit();
        switch (v.getId()) {
            case R.id.button4_1:
                //Toast.makeText(getApplicationContext(),"dddddddddddddddddddd",Toast.LENGTH_SHORT).show();
                prefsEditor.putBoolean("select1", true);
                prefsEditor.commit();
                startActivity(new Intent(this, ApplicationSelectionActivity.class));
                break;
            case R.id.button4_2:
                prefsEditor.putBoolean("select2", true);
                prefsEditor.commit();
                startActivity(new Intent(this, ApplicationSelectionActivity.class));
                break;
            case R.id.button4_3:
                prefsEditor.putBoolean("select3", true);
                prefsEditor.commit();
                startActivity(new Intent(this, ApplicationSelectionActivity.class));
                break;
            case R.id.button4_4:
                prefsEditor.putBoolean("select4", true);
                prefsEditor.commit();

                startActivity(new Intent(this, ApplicationSelectionActivity.class));
                break;
            case R.id.app_registration:
                Log.d("등록완료 클릭!","");
                SharedPreferences prefs2 = getSharedPreferences("num", MODE_PRIVATE);

                int num1 = prefs2.getInt("num1", 0);
                int num2 = prefs2.getInt("num2", 0);
                int num3 = prefs2.getInt("num3", 0);
                int num4 = prefs2.getInt("num4", 0);

                Log.d("CCC >> ", String.valueOf(num1));
                Log.d("CCC >> ", String.valueOf(num2));
                Log.d("CCC >> ", String.valueOf(num3));
                Log.d("CCC >> ", String.valueOf(num4));
                finish();
                break;
            case R.id.app_cancel:
                returnApplicationList();
                finish();
                break;

        }
//        ArrayAdapter spinnerAdapter1;

//        spinnerAdapter1 = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);
//        spinner1.setAdapter(spinnerAdapter1);
//
//        spinner1.setSelection(0, false);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
          //      Toast.makeText(getApplicationContext(),"dddddddddddddddddddd",Toast.LENGTH_SHORT).show();
                   num[0] = Integer.valueOf((String) spinner1.getItemAtPosition(position));

                   Log.d("선택한 스피너 값 : ", String.valueOf(num[0]));
                   prefsEditor5.putInt("num1", num[0]);
                   prefsEditor5.commit();

                   spinner1.setSelection(position);

                   Log.d("BBB >> ", String.valueOf(prefs5.getInt("num1", 0)));
                   Log.d("BBB >> ", String.valueOf(prefs5.getInt("num2", 0)));
                   Log.d("BBB >> ", String.valueOf(prefs5.getInt("num3", 0)));
                   Log.d("BBB >> ", String.valueOf(prefs5.getInt("num4", 0)));
                   //    Toast.makeText(getApplicationContext(), "1번째 바로가기 어플 번호:" + num[0] + "입니다", Toast.LENGTH_SHORT).show();
                   Log.d("바로가기번호", "1번째 바로가기 어플 번호 : " + num[0]);


        }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // spinner3.setAdapter(spinnerAdapter3);

        //spinner4.setAdapter(spinnerAdapter4);
//        ArrayAdapter spinnerAdapter2;
//        spinnerAdapter2 = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);
//        spinner2.setAdapter(spinnerAdapter2);
//
//        spinner2.setSelection(0, false);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    num[1] = Integer.valueOf((String) spinner2.getItemAtPosition(position));
                    Log.d("선택한 스피너 값 : ", String.valueOf(num[1]));
                    prefsEditor5.putInt("num2", num[1]);
                    prefsEditor5.commit();

                    spinner2.setSelection(position);
                    Log.d("BBB >> ", String.valueOf(prefs5.getInt("num1", 0)));
                    Log.d("BBB >> ", String.valueOf(prefs5.getInt("num2", 0)));
                    Log.d("BBB >> ", String.valueOf(prefs5.getInt("num3", 0)));
                    Log.d("BBB >> ", String.valueOf(prefs5.getInt("num4", 0)));

                    //  Toast.makeText(getApplicationContext(),"2번째 바로가기 어플 번호:"+num[1]+"입니다",Toast.LENGTH_SHORT).show();
                    Log.d("바로가기번호", "2번째 바로가기 어플 번호 : " + num[1]);
                }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        ArrayAdapter spinnerAdapter3;
//        spinnerAdapter3 = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);
//        spinner3.setAdapter(spinnerAdapter3);
//
//        spinner3.setSelection(0, false);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    num[2] = Integer.valueOf((String) spinner3.getItemAtPosition(position));
                    Log.d("선택한 스피너 값 : ", String.valueOf(num[2]));
                    prefsEditor5.putInt("num3", num[2]);
                    prefsEditor5.commit();

                    spinner3.setSelection(position);

                    Log.d("BBB >> ", String.valueOf(prefs5.getInt("num1", 0)));
                    Log.d("BBB >> ", String.valueOf(prefs5.getInt("num2", 0)));
                    Log.d("BBB >> ", String.valueOf(prefs5.getInt("num3", 0)));
                    Log.d("BBB >> ", String.valueOf(prefs5.getInt("num4", 0)));

                    //Toast.makeText(getApplicationContext(),"3번째 바로가기 어플 번호:"+num[2]+"입니다",Toast.LENGTH_SHORT).show();
                    Log.d("바로가기번호", "3번째 바로가기 어플 번호 : " + num[2]);
                }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//
//        ArrayAdapter spinnerAdapter4;
//        spinnerAdapter4 = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);
//        spinner4.setAdapter(spinnerAdapter4);
//
//        spinner4.setSelection(0, false);
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    num[3] = Integer.valueOf((String) spinner4.getItemAtPosition(position));
                    Log.d("선택한 스피너 값 : ", String.valueOf(num[3]));
                    prefsEditor5.putInt("num4", num[3]);
                    prefsEditor5.commit();

                    spinner4.setSelection(position);
                    Log.d("BBB >> ", String.valueOf(prefs5.getInt("num1", 0)));
                    Log.d("BBB >> ", String.valueOf(prefs5.getInt("num2", 0)));
                    Log.d("BBB >> ", String.valueOf(prefs5.getInt("num3", 0)));
                    Log.d("BBB >> ", String.valueOf(prefs5.getInt("num4", 0)));

                    //Toast.makeText(getApplicationContext(),"4번째 바로가기 어플 번호:"+num[3]+"입니다",Toast.LENGTH_SHORT).show();
                    Log.d("바로가기번호", "4번째 바로가기 어플 번호 : " + num[3]);
                }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        Button button4_1 = (Button) findViewById(R.id.button4_1);
        Button button4_2 = (Button) findViewById(R.id.button4_2);
        Button button4_3 = (Button) findViewById(R.id.button4_3);
        Button button4_4 = (Button) findViewById(R.id.button4_4);




//        final ArrayList<String> list = new ArrayList<>();
//        list.add("0");
//        list.add("1");
//        list.add("2");
//        list.add("3");
//        list.add("4");
//        list.add("5");
//        list.add("6");
//        list.add("7");
//        list.add("8");
//        list.add("9");

//        ArrayAdapter spinnerAdapter1;
//        spinnerAdapter1 = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);
//        spinner1.setAdapter(spinnerAdapter1);
//        ArrayAdapter spinnerAdapter2;
//        spinnerAdapter2 = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);
//        spinner2.setAdapter(spinnerAdapter2);
//        ArrayAdapter spinnerAdapter3;
//        spinnerAdapter3 = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);
//        spinner3.setAdapter(spinnerAdapter3);
//        ArrayAdapter spinnerAdapter4;
//        spinnerAdapter4 = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);
//        spinner4.setAdapter(spinnerAdapter4);


        Button register = (Button) findViewById(R.id.app_registration);
        Button cancel = (Button) findViewById(R.id.app_cancel);

        button4_1.setOnClickListener(this);
        button4_2.setOnClickListener(this);
        button4_3.setOnClickListener(this);
        button4_4.setOnClickListener(this);

        register.setOnClickListener(this);
        cancel.setOnClickListener(this);

        SharedPreferences prefs = getSharedPreferences("Message", MODE_PRIVATE);
        button4_1.setText(prefs.getString("msg1", "여기에 어플을 등록합니다"));
        button4_2.setText(prefs.getString("msg2", "여기에 어플을 등록합니다"));
        button4_3.setText(prefs.getString("msg3", "여기에 어플을 등록합니다"));
        button4_4.setText(prefs.getString("msg4", "여기에 어플을 등록합니다"));

     /*   SharedPreferences prefs5 = getSharedPreferences("num", MODE_PRIVATE);

        spinner1.setSelection(prefs5.getInt("num1", 0));
        spinner2.setSelection(prefs5.getInt("num2", 0));
        spinner3.setSelection(prefs5.getInt("num3", 0));
        spinner4.setSelection(prefs5.getInt("num4", 0));
*/

    }

    public void applicationList() {

        SharedPreferences prefs3 = getSharedPreferences("Message", MODE_PRIVATE);

        SharedPreferences prefs4 = getSharedPreferences("PakageName", MODE_PRIVATE);

        Message[0] = prefs3.getString("msg1", "여기에 어플을 등록합니다");
        Message[1] = prefs3.getString("msg2", "여기에 어플을 등록합니다");
        Message[2] = prefs3.getString("msg3", "여기에 어플을 등록합니다");
        Message[3] = prefs3.getString("msg4", "여기에 어플을 등록합니다");
        Message[4] = prefs3.getString("msg5", "여기에 어플을 등록합니다");
        Message[5] = prefs3.getString("msg6", "여기에 어플을 등록합니다");

        PackageName[0] = prefs4.getString("name1", "");
        PackageName[1] = prefs4.getString("name2", "");
        PackageName[2] = prefs4.getString("name3", "");
        PackageName[3] = prefs4.getString("name4", "");
        PackageName[4] = prefs4.getString("name5", "");
        PackageName[5] = prefs4.getString("name6", "");

        for (int i = 0; i < 6; i++) {
            Log.d("TouchTabFourActivity", "Message >> " + Message[i]);
            Log.d("TouchTabFourActivity", "PackageName >> " + PackageName[i]);
        }
    }

    public void returnApplicationList() {

        SharedPreferences prefs3 = getSharedPreferences("Message", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor2 = prefs3.edit();

        SharedPreferences prefs4 = getSharedPreferences("PakageName", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor3 = prefs4.edit();


        SharedPreferences prefs5 = getSharedPreferences("num", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor4 = prefs5.edit();

        prefsEditor2.putString("msg1", Message[0]);
        prefsEditor2.putString("msg2", Message[1]);
        prefsEditor2.putString("msg3", Message[2]);
        prefsEditor2.putString("msg4", Message[3]);
        prefsEditor2.putString("msg5", Message[4]);
        prefsEditor2.putString("msg6", Message[5]);

        prefsEditor2.commit();

        prefsEditor3.putString("name1", PackageName[0]);
        prefsEditor3.putString("name2", PackageName[1]);
        prefsEditor3.putString("name3", PackageName[2]);
        prefsEditor3.putString("name4", PackageName[3]);
        prefsEditor3.putString("name5", PackageName[4]);
        prefsEditor3.putString("name6", PackageName[5]);

        prefsEditor3.commit();

        prefsEditor4.putInt("num1", num[0]);
        prefsEditor4.putInt("num2", num[1]);
        prefsEditor4.putInt("num3", num[2]);
        prefsEditor4.putInt("num4", num[3]);

        prefsEditor4.commit();


    }
    public int[] getInputArray(){
        return num;
    }

}