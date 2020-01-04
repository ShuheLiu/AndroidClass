package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class Introduction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        Intent intent = getIntent();
        String courseid = intent.getStringExtra("courseid");
        String name = intent.getStringExtra("name");
        String code = intent.getStringExtra("code");
        String openDate = intent.getStringExtra("openDate");
        String description=intent.getStringExtra("description");

        TextView text=(TextView)findViewById(R.id.name);
        text.setText(name);
        text=(TextView)findViewById(R.id.code);
        text.setText(code);
        text=(TextView)findViewById(R.id.openDate);
        text.setText(openDate);
        text=(TextView)findViewById(R.id.description);
        text.setText(description);

        Button button = null;
        button = (Button)findViewById(R.id.back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
