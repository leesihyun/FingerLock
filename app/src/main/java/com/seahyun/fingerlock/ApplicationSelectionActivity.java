package com.seahyun.fingerlock;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;
public class ApplicationSelectionActivity extends Activity {

    Activity act = this;
    GridView gridView;
    private List<ResolveInfo> apps;
    private PackageManager pm;
    private String shortcut[] = new String[6];
    private String msg;
    private String name;
    PinAppRegister o = new PinAppRegister();
    private int num[] = new int[4];


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for(int i=0;i<4;i++){
            num[i]=0;}
        num = o.getInputArray();
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.7f;
        getWindow().setAttributes(layoutParams);


        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();

        getWindow().getAttributes().width = (int) (dm.widthPixels*0.9);
        getWindow().getAttributes().height =(int) (dm.heightPixels*0.9);

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        pm = getPackageManager();
        apps = pm.queryIntentActivities(mainIntent, 0);

        setContentView(R.layout.application_selection_info);

        gridView = (GridView) findViewById(R.id.gridView1);
        gridView.setAdapter(new gridAdapter());
        for(int i=0; i<6; i++){
            shortcut[i] = "";
        }


    }

    public class gridAdapter extends BaseAdapter{
        LayoutInflater inflater;

        public gridAdapter(){
            inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public final int getCount() {
            return apps.size();
        }

        @Override
        public final Object getItem(int position) {
            return apps.get(position);
        }

        @Override
        public final long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = inflater.inflate(R.layout.app_item, parent, false);
            }
            final ResolveInfo info = apps.get(position);

            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView1);
            TextView textView = (TextView) convertView.findViewById(R.id.textView1);
            imageView.setImageDrawable(info.activityInfo.loadIcon(getPackageManager()));
            textView.setText(info.activityInfo.loadLabel(pm).toString());

            Log.v("[Program]", info.activityInfo.packageName+","+info.activityInfo.name);

            imageView.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String msg = info.activityInfo.loadLabel(pm).toString();
                    Toast.makeText(act, msg + "클릭!", Toast.LENGTH_SHORT).show();

                    SharedPreferences prefs = getSharedPreferences("pref", MODE_PRIVATE);
                   //
                    // int num = prefs.getInt("tab_num", 4);
                    Log.d("어플 선택 창에서 터치탭 개수 >>", String.valueOf(num));
                    SharedPreferences prefs2 = getSharedPreferences("select_state", MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = prefs2.edit();

                    SharedPreferences prefs3 = getSharedPreferences("Message", MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor2 = prefs3.edit();

                    SharedPreferences prefs4 = getSharedPreferences("PakageName", MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor3 = prefs4.edit();
                    SharedPreferences prefs5 = getSharedPreferences("num", MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor5 = prefs5.edit();


                    //if (num == 4) {//터치탭 개수가 4개일 때
                        if(prefs2.getBoolean("select1",false)==true) { //선택한 어플을 터치탭에 등록
                            Log.d("터치탭 선택 >> ", "1번 터치탭");

                            //int i = prefs.getInt("passwd", 0);
                            //shortcut[i] = msg;
                            shortcut[0] = msg;

                            //applicationSave(0, info.activityInfo.packageName);

                            prefsEditor.putBoolean("select1", false);
                            prefsEditor.commit();

                            prefsEditor2.putString("msg1", shortcut[0]);
                            prefsEditor2.commit();

                            prefsEditor3.putString("name1", info.activityInfo.packageName);
                            prefsEditor3.commit();

                            Log.d("1번 바로가기 설정된 앱 >> ", shortcut[0]);
                            Log.d("패키지 이름 >> ", prefs4.getString("name1", ""));
                        }
                        else if(prefs2.getBoolean("select2",false)==true){
                            Log.d("터치탭 선택 >> ", "2번 터치탭");

                            //int i = prefs.getInt("passwd", 0);

                            //shortcut[i] = msg;
                            shortcut[1] = msg;
                            //applicationSave(1, info.activityInfo.packageName);

                            prefsEditor.putBoolean("select2", false);
                            prefsEditor.commit();

                            prefsEditor2.putString("msg2", shortcut[1]);
                            prefsEditor2.commit();
                            prefsEditor3.putString("name2", info.activityInfo.packageName);
                            prefsEditor3.commit();


                           // prefsEditor5.putInt("num1",num[0]);
                           // prefsEditor5.commit();
                            Log.d("2번 바로가기 설정된 앱 >> ", shortcut[1]);
                        }
                        else if(prefs2.getBoolean("select3",false)==true){
                            Log.d("터치탭 선택 >> ", "3번 터치탭");

                            //int i = prefs.getInt("passwd", 0);
                            //shortcut[i] = msg;
                            shortcut[2] = msg;
                            //applicationSave(2, info.activityInfo.packageName);
                            prefsEditor.putBoolean("select3", false);
                            prefsEditor.commit();

                            prefsEditor2.putString("msg3", shortcut[2]);
                            prefsEditor2.commit();

                            prefsEditor3.putString("name3", info.activityInfo.packageName);
                            prefsEditor3.commit();

                           // prefsEditor5.putInt("num2",num[1]);
                            //prefsEditor5.commit();
                            Log.d("3번 바로가기 설정된 앱 >> ", shortcut[2]);
                        }
                        else if(prefs2.getBoolean("select4",false)==true){
                            Log.d("터치탭 선택 >> ", "4번 터치탭");

//                            int i = prefs.getInt("passwd", 0);
//                            shortcut[i] = msg;
                            shortcut[3] = msg;
                            //applicationSave(3, info.activityInfo.packageName);
                            prefsEditor.putBoolean("select4", false);
                            prefsEditor.commit();

                            prefsEditor2.putString("msg4", shortcut[3]);
                            prefsEditor2.commit();

                            prefsEditor3.putString("name4", info.activityInfo.packageName);
                            prefsEditor3.commit();
                           // prefsEditor5.putInt("num3",num[2]);
                           // prefsEditor5.commit();
                            Log.d("4번 바로가기 설정된 앱 >> ", shortcut[3]);
                        }
                        else if(prefs2.getBoolean("select5",false)==true){
                            Log.d("터치탭 선택 >> ", "5번 터치탭");
//                            int i = prefs.getInt("passwd", 0);
//                            shortcut[i] = msg;
                            shortcut[4] = msg;
                            prefsEditor.putBoolean("select5", false);
                            prefsEditor.commit();

                            prefsEditor2.putString("msg5", shortcut[4]);
                            prefsEditor2.commit();

                            prefsEditor3.putString("name5", info.activityInfo.packageName);
                            prefsEditor3.commit();
                            //applicationSave(4, info.activityInfo.packageName);
                          //  prefsEditor5.putInt("num4",num[3]);
                           // prefsEditor5.commit();
                            Log.d("5번 바로가기 설정된 앱 >> ", shortcut[4]);
                        }
                        else if(prefs2.getBoolean("select6",false)==true){
                            Log.d("터치탭 선택 >> ", "6번 터치탭");
//                            int i = prefs.getInt("passwd", 0);
                            shortcut[5] = msg;
                            //applicationSave(5, info.activityInfo.packageName);
                            prefsEditor.putBoolean("select6", false);
                            prefsEditor.commit();

                            prefsEditor2.putString("msg6", shortcut[5]);
                            prefsEditor2.commit();

                            prefsEditor3.putString("name6", info.activityInfo.packageName);
                            prefsEditor3.commit();


                            Log.d("6번 바로가기 설정된 앱 >> ", shortcut[5]);
                        }
                    //}
                    finish();
                }
            });
            return convertView;
        }
    }
    /*public void applicationSave(int i, String pakcageName){

        if(i==1){
           msg = "msg1";
           name = "name1";
        }
        else if(i==2){
            msg = "msg2";
            name = "name2";
        }
        else if(i==3){
            msg = "msg3";
            name = "name3";
        }
        else if(i==4){
            msg = "msg4";
            name = "name4";
        }
        else if(i==5) {
            msg = "msg5";
            name = "name5";
        }
        else if(i==6) {
            msg = "msg6";
            name = "name6";
        }

        SharedPreferences prefs3 = getSharedPreferences("Message", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor2 = prefs3.edit();

        SharedPreferences prefs4 = getSharedPreferences("PakageName", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor3 = prefs4.edit();

        prefsEditor2.putString(msg, shortcut[i]);
        prefsEditor2.commit();

        prefsEditor3.putString(name, pakcageName);
        prefsEditor3.commit();
    }*/
}