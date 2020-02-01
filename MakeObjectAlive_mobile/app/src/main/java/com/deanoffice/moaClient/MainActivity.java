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
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;

import okhttp3.OkHttpClient;


public class MainActivity extends AppCompatActivity implements MediaSourceEventListener{
    Button btnView, btnScan;

    String url = "http://192.168.212.57:8080/webDevelop_war_exploded/Send";
    String videoURL = "http://deddj9om7jjsg.cloudfront.net/c29153a9-1044-4952-96f6-22131d44e843/hls/VID_20200123_145724.m3u8";

    PlayerView playerView;
    QRScan qrScan;
    FileOperator fileOperator;
    Client client;
    VideoPlayer videoPlayer;

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

        btnView = findViewById(R.id.btnGenerate);
        btnScan = findViewById(R.id.btnScan);
        playerView = findViewById(R.id.exoplayer);
        client = new Client(new OkHttpClient(), url, MainActivity.this);
        videoPlayer = new VideoPlayer();

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

    public void buttonView(View view){
        try{
            videoPlayer.showVideoFromM3u8URL(videoURL, this, playerView, this);
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    @Override
    public void onMediaPeriodCreated(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
        ///
    }

    @Override
    public void onMediaPeriodReleased(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
        ///
    }

    @Override
    public void onLoadStarted(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        ///
    }

    @Override
    public void onLoadCompleted(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        ///
    }

    @Override
    public void onLoadCanceled(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        ///
    }

    @Override
    public void onLoadError(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException error, boolean wasCanceled) {
        ///
    }

    @Override
    public void onReadingStarted(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
        ///
    }

    @Override
    public void onUpstreamDiscarded(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
        ///
    }

    @Override
    public void onDownstreamFormatChanged(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
        ///
    }
}
