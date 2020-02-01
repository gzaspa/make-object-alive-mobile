package com.deanoffice.moaClient.client;

import android.app.Activity;
import android.content.Context;

import com.deanoffice.moaClient.fileOperaion.FileOperator;
import com.deanoffice.moaClient.fileOperaion.MessageUtils;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Client {
    private OkHttpClient okHttpClient;
    private String url;

    public Client(OkHttpClient client, String url, Context context){
        this.url = url;
        this.okHttpClient = client;
    }

    public boolean connect(String QRkey, final FileOperator fileOperator){

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("Request", QRkey)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println(e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                fileOperator.createFile();
                if(response.body().byteStream() != null){
                    InputStream os = response.body().byteStream();
                    FileOutputStream fos = new FileOutputStream(fileOperator.fileData.getFile());
                    BufferedInputStream bin = new BufferedInputStream(os);
                    BufferedOutputStream bout = new BufferedOutputStream(fos);

                    int ch = 0;
                    while((ch=bin.read())!=-1){
                        bout.write(ch);
                    }

                    bin.close();
                    os.close();
                    bout.close();
                    fos.close();
                }
            }

        });
        return true;
    }
}
