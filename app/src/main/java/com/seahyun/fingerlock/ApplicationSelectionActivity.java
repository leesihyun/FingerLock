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
    private String shortcut[] = {"","","","","",""};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                    int num = prefs.getInt("tab_num", 4);
                    Log.d("어플 선택 창에서 터치탭 개수 >>", String.valueOf(num));

                    SharedPreferences prefs2 = getSharedPreferences("select_state", MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = prefs2.edit();

                    SharedPreferences prefs3 = getSharedPreferences("Message", MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor2 = prefs3.edit();

                    SharedPreferences prefs4 = getSharedPreferences("PakageName", MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor3 = prefs4.edit();

                    //if (num == 4) {//터치탭 개수가 4개일 때
                        if(prefs2.getBoolean("select1",false)==true) { //선택한 어플을 터치탭에 등록
                            Log.d("터치탭 선택 >> ", "1번 터치탭");
                            shortcut[0] = msg;

                            prefsEditor.putBoolean("select1", false);
                            prefsEditor.commit();

                            prefsEditor2.putString("msg1", shortcut[0]);
                            prefsEditor2.commit();

                            prefsEditor3.putString("name1", info.activityInfo.packageName);
                            prefsEditor3.commit();

                            Log.d("1번 바로가기 설정된 앱 >> ", shortcut[0]);
                        }
                        else if(prefs2.getBoolean("select2",false)==true){
                            Log.d("터치탭 선택 >> ", "2번 터치탭");
                            shortcut[1] = msg;

                            prefsEditor.putBoolean("select2", false);
                            prefsEditor.commit();

                            prefsEditor2.putString("msg2", shortcut[1]);
                            prefsEditor2.commit();

                            prefsEditor3.putString("name2", info.activityInfo.packageName);
                            prefsEditor3.commit();

                            Log.d("2번 바로가기 설정된 앱 >> ", shortcut[1]);
                        }
                        else if(prefs2.getBoolean("select3",false)==true){
                            Log.d("터치탭 선택 >> ", "3번 터치탭");
                            shortcut[2] = msg;

                            prefsEditor.putBoolean("select3", false);
                            prefsEditor.commit();

                            prefsEditor2.putString("msg3", shortcut[2]);
                            prefsEditor2.commit();

                            prefsEditor3.putString("name3", info.activityInfo.packageName);
                            prefsEditor3.commit();

                            Log.d("3번 바로가기 설정된 앱 >> ", shortcut[2]);
                        }
                        else if(prefs2.getBoolean("select4",false)==true){
                            Log.d("터치탭 선택 >> ", "4번 터치탭");
                            shortcut[3] = msg;

                            prefsEditor.putBoolean("select4", false);
                            prefsEditor.commit();

                            prefsEditor2.putString("msg4", shortcut[3]);
                            prefsEditor2.commit();

                            prefsEditor3.putString("name4", info.activityInfo.packageName);
                            prefsEditor3.commit();

                            Log.d("4번 바로가기 설정된 앱 >> ", shortcut[3]);
                        }
                        else if(prefs2.getBoolean("select5",false)==true){
                            Log.d("터치탭 선택 >> ", "5번 터치탭");
                            shortcut[4] = msg;

                            prefsEditor.putBoolean("select5", false);
                            prefsEditor.commit();

                            prefsEditor2.putString("msg5", shortcut[4]);
                            prefsEditor2.commit();

                            prefsEditor3.putString("name5", info.activityInfo.packageName);
                            prefsEditor3.commit();

                            Log.d("5번 바로가기 설정된 앱 >> ", shortcut[4]);
                        }
                        else if(prefs2.getBoolean("select6",false)==true){
                            Log.d("터치탭 선택 >> ", "6번 터치탭");
                            shortcut[5] = msg;

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
}