package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.myapplication.api.Client;
import com.example.myapplication.api.Service;

import java.io.File;

import retrofit2.Call;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;


public class Video extends AppCompatActivity {
    String mid;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        Intent intent = getIntent();
        mid = intent.getStringExtra("mid");
        Button button = null;
        button = (Button)findViewById(R.id.back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadJSON() {
        try {
            Service request = Client.retrofit.create(Service.class);
            Call<ResponseBody> call = request.getVideo(mid);
            call.enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    final String videopath = Uri.parse(getExternalFilesDir(null) + File.separator + "vtest.mp4").toString();
                    videoView=(VideoView) findViewById(R.id.videoView);
                    videoView.setVideoPath(videopath);
                    //videoew.start();
                    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mediaPlayer.start();
                            mediaPlayer.setLooping(true);
                        }
                    });
                    videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            videoView.setVideoPath(videopath);
                            videoView.start();
                        }
                    });

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(Video.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(Video.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
