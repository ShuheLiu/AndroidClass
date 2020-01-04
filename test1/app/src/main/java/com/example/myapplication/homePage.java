package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import java.util.*;
import android.view.*;
import android.content.Intent;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.myapplication.api.Client;
import com.example.myapplication.api.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class homePage extends AppCompatActivity {

    private ListView listview;
    //ProgressDialog pd;
    private List<Course> courseList;
    List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>(); //存储数据的数组列表

    private User user;

    private int lastfragment;
    private Fragment[] fragments;
    courseListFragment courseListFragment;
    MyCourseFragment myCourseFragment;
    /*InstructorMemberListFragment instructorMemberListFragment;
    InstructorCourseListFragment instructorCourseListFragment;*/

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.AllClassItem:
                    if(lastfragment!=0) {
                        switchFragment(lastfragment,0);
                        lastfragment=0;
                    }
                    return true;
                case R.id.MyClassItem:
                    if(lastfragment!=1) {
                        switchFragment(lastfragment,1);
                        lastfragment=1;
                    }
                    return true;
                case R.id.MediaItem:
                    if(lastfragment!=2) {
                        switchFragment(lastfragment,2);
                        lastfragment=2;
                    }
                    return true;
                case R.id.MessageItem:
                    if(lastfragment!=3) {
                        switchFragment(lastfragment,3);
                        lastfragment=3;
                    }
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        user = (User)getIntent().getSerializableExtra("USER");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        initFragment();

    }

    private void initFragment() {
        courseListFragment fragment1 = new courseListFragment();
        MyCourseFragment fragment2 = new MyCourseFragment();
        MediaFragment fragment3=new MediaFragment();
        SelfInfromationFragment fragment4=new SelfInfromationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("userid",user.getUserId());
        fragment2.setArguments(bundle);

        fragment1.loadJSON();
        fragment2.loadJSON();
        fragment3.initData();

        fragments = new Fragment[]{fragment1,fragment2,fragment3,fragment4};

        lastfragment = 0;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment1).show(fragment1).commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomMenu);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void switchFragment(int lastfragment,int index) {
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastfragment]);//隐藏上个Fragment
        if(fragments[index].isAdded()==false) {
            transaction.add(R.id.fragment_container,fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }

}
