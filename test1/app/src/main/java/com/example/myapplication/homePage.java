package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.*;
import java.util.*;
import android.view.*;
import android.content.Intent;

public class homePage extends AppCompatActivity {

    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        listview = (ListView)findViewById(R.id.courseList);
        String[] course = {"课程1","课程2","课程3","课程4","课程4","课程4","课程4","课程4","课程4","课程2","课程3","课程4","课程4","课程4","课程4","课程4","课程4"};
        List<String> listdata = new ArrayList<String>();
        listdata.add("上海");
        listdata.add("北京");
        listdata.add("天津");
        listdata.add("江苏");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.list_item,course);//listdata和str均可
        listview.setAdapter(arrayAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  Bundle bundle = new Bundle();
                  /*int id1 = list.get(position).getId();
                  String thing = list.get(position).getT_name();
                  String place = list.get(position).getT_place();
                  String time = list.get(position).getTime();
                  bundle.putInt("id", id1);
                  bundle.putString("thing", thing);
                  bundle.putString("place", place);
                  bundle.putString("time", time);*/

                  Intent intent = new Intent();
                  intent.putExtras(bundle);
                  intent.setClass(homePage.this, Introduction.class);
                  startActivity(intent);}
        });
    }
}
