package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.*;
import android.view.*;
import android.content.Intent;

import com.google.gson.Gson;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import com.example.myapplication.api.Client;
import com.example.myapplication.api.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button Register;
    private Button Login;
    private Button QQlogin;
    private User user;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private List<User> userList;

    private AccountDatabaseHelper databaseHelper;
    ProgressDialog pd;
    private NewDatabaseHelper newdatabaseHelper;

    /*
     * qq登录功能
     */
    public static Tencent mTencent;
    private UserInfo mInfo;
    public static String mAppid="1108071510";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        if (mTencent == null) {
            mTencent = Tencent.createInstance(mAppid, this);
        }

        usernameEdit = (EditText)findViewById(R.id.userAccount);
        passwordEdit = (EditText)findViewById(R.id.userPwd);

        Register = (Button)findViewById(R.id.register);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(com.example.myapplication.MainActivity.this, register.class);
                startActivity(intent);
                finish();
            }
        });

        Login = (Button)findViewById(R.id.login);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent();
                intent.setClass(com.example.myapplication.MainActivity.this, homePage.class);
                startActivity(intent);
                finish();*/

                pd = new ProgressDialog(MainActivity.this);
                pd.setMessage("Waiting...");
                pd.setCancelable(false);
                pd.show();
                loadJSON();
            }
        });

        QQlogin = (Button)findViewById(R.id.QQlogin);
        QQlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onClickLogin();


            }
        });

    }

    private boolean checkout(String username, String password){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String selectsql = "where username = '" + username + "' and password = '" + password + "'";
        Cursor cursor = db.rawQuery("select * from account " + selectsql, null);

        if(cursor.getCount() == 0) {
            Toast.makeText(MainActivity.this, "用户名或密码不正确", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            if(cursor.moveToFirst()){
                do{
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String level = cursor.getString(cursor.getColumnIndex("level"));

                    user = new User(username, password);
                    user.setRoal(level);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
        return true;
    }

    private void loadJSON() {
        try {
            Service request = Client.retrofit.create(Service.class);
            Call<List<User>> call = request.getUsers();
            call.enqueue(new Callback<List<User>>() {

                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    userList = response.body();

                    String name = usernameEdit.getText().toString();
                    String password = passwordEdit.getText().toString();

                    pd.hide();

                    boolean isfind = false;
                    for(int i = 0 ;i < userList.size(); i++){
                        if(userList.get(i).getName().equals(name)){
                            isfind = true;
                            if(userList.get(i).getPassword().equals(password)){
                                user = userList.get(i);

                                Intent intent;
                                if (user.getRoal().equals("MEMBER")){
                                    intent = new Intent();
                                    intent.setClass(com.example.myapplication.MainActivity.this, homePage.class);
                                    intent.putExtra("USER", user);
                                    startActivity(intent);
                                }
                                else {
                                    intent = new Intent("AdminMainActivity");
                                }

                                //intent.putExtra("USER", user);

                                newdatabaseHelper = new NewDatabaseHelper(MainActivity.this, "myapplication.db", null, 1);
                                SQLiteDatabase db = newdatabaseHelper.getWritableDatabase();
                                newdatabaseHelper.updateUsers(db, user);

                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(MainActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    if(!isfind) Toast.makeText(MainActivity.this, "没有这个用户", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(MainActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                    pd.hide();
                }

            });

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 继承的到BaseUiListener得到doComplete()的方法信息
     */
    IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {//得到用户的ID  和签名等信息  用来得到用户信息
            Log.i("lkei",values.toString());
            initOpenidAndToken(values);
            updateUserInfo();
        }
    };
    /***
     * QQ平台返回返回数据个体 loginListener的values
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode,resultCode,data,loginListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            if (null == response) {
                Toast.makeText(MainActivity.this, "登录失败",Toast.LENGTH_LONG).show();
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                Toast.makeText(MainActivity.this, "登录失败",Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(MainActivity.this, "登录成功",Toast.LENGTH_LONG).show();
            //doComplete((JSONObject)response);
            String username = null;
            try {
                username = ((JSONObject)response).getString("nickname");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            user = new User("username");
            user.setRoal("");
            Intent intent = new Intent();
            intent.setClass(com.example.myapplication.MainActivity.this, homePage.class);
            intent.putExtra("USER", user);
            startActivity(intent);
        }

        protected void doComplete(JSONObject values) {

            String username = null;
            try {
                username = values.getString("nickname");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            user = new User("username");
            user.setRoal("MEMBER");
            Intent intent = new Intent();
            intent.setClass(com.example.myapplication.MainActivity.this, homePage.class);
            intent.putExtra("USER", user);
            startActivity(intent);
        }

        @Override
        public void onError(UiError e) {
            //登录错误
        }

        @Override
        public void onCancel() {
            // 运行完成
        }
    }

    /**
     * 获取登录QQ腾讯平台的权限信息(用于访问QQ用户信息)
     * @param jsonObject
     */
    public static void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch(Exception e) {
        }
    }
    private void onClickLogin() {
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", loginListener);
        }
    }
    private void updateUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
            IUiListener listener = new IUiListener() {
                @Override
                public void onError(UiError e) {
                }
                @Override
                public void onComplete(final Object response) {
                    Message msg = new Message();
                    msg.obj = response;
                    Log.i("tag", response.toString());
                    msg.what = 0;
                    mHandler.sendMessage(msg);
                }
                @Override
                public void onCancel() {
                }
            };
            mInfo = new UserInfo(this, mTencent.getQQToken());
            mInfo.getUserInfo(listener);

        }
    }
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                JSONObject response = (JSONObject) msg.obj;
                if (response.has("nickname")) {
                    Gson gson=new Gson();
                    User user=gson.fromJson(response.toString(),User.class);
                }
            }
        }
    };

}
