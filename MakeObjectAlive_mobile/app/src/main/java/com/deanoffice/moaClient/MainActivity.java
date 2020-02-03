package com.deanoffice.moaClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.deanoffice.moaClient.client.Client;
import com.deanoffice.moaClient.fileOperaion.FileOperator;
import com.deanoffice.moaClient.fileOperaion.MessageUtils;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;

import okhttp3.OkHttpClient;


public class MainActivity extends AppCompatActivity{

    String url = "http://192.168.212.57:8080/webDevelop_war_exploded/Send";
    String videoURL = "http://deddj9om7jjsg.cloudfront.net/c29153a9-1044-4952-96f6-22131d44e843/hls/VID_20200123_145724.m3u8";

    PlayerView playerView;
    VideoView videoView;
    QRScan qrScan;
    FileOperator fileOperator;
    Client client;
    VideoPlayerController videoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT>=23){
            requestPermissions(new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.ACCESS_NETWORK_STATE,
            }, 2);
        }

        playerView = findViewById(R.id.exoplayer);
        client = new Client(new OkHttpClient(), url, MainActivity.this);
        videoView = findViewById(R.id.videoView);

        videoPlayer = new VideoPlayerController(videoView, playerView);

        fileOperator = new FileOperator();
        qrScan = new QRScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null && result.getContents() != null){
            client.connect(result.getContents(), fileOperator);
            MessageUtils.showMessage("Finally", "Video downloaded", this);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void buttonScan(View view){
        qrScan.scanProcess(MainActivity.this);
    }

    public void buttonStream(View view){
        try{
            videoPlayer.showVideoFromM3u8URL(videoURL, this);
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void buttonView(View view){
        try{
            videoPlayer.showVideo(this);
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

}
