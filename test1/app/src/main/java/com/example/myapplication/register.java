package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.api.Client;
import com.example.myapplication.R;
import com.example.myapplication.api.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class register extends AppCompatActivity {

    private Button Cancel;
    private Button ok;
    private EditText nameText;
    private EditText pwText;
    private EditText pw2Text;
    private EditText emailText;
    private EditText phoneText;

    private User user;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameText = (EditText)findViewById(R.id.username);
        pwText = (EditText)findViewById(R.id.password);
        phoneText = (EditText)findViewById(R.id.phonenumber);
        pw2Text = (EditText)findViewById(R.id.password2);
        emailText = (EditText)findViewById(R.id.email);

        Cancel = (Button)findViewById(R.id.cancel);
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(register.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ok = (Button)findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(register.this);
                pd.setMessage("Waiting...");
                pd.setCancelable(false);
                pd.show();
                loadJSON();
            }
        });
    }

    private void loadJSON() {
        try {
            final Service request = Client.retrofit.create(Service.class);
            final String name = nameText.getText().toString();
            final String password = pwText.getText().toString();
            final String phone = phoneText.getText().toString();
            String password2 = pw2Text.getText().toString();
            final String email = emailText.getText().toString();

            Call<User> call = request.putUser(name, password, email, phone,"MEMBER");
            call.enqueue(new Callback<User>() {

                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    pd.hide();
                    //user = response.body();
                    user=new User("1111",name,email,password,phone,"MEMBER");
                    Intent intent = new Intent();
                    intent.setClass(com.example.myapplication.register.this, homePage.class);
                    intent.putExtra("USER", user);
                    startActivity(intent);
                    //request.getEmail();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(register.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                    pd.hide();
                }

            });


        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(register.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
