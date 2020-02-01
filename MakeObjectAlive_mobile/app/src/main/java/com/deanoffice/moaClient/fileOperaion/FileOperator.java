package com.deanoffice.moaClient.fileOperaion;

import android.content.Context;
import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class FileOperator {

    public FileData fileData;
    final File path = new File(Environment.getExternalStorageDirectory() + "/MakeObjectAlive");

    public void createFile(){
        if(path.exists()){
            fileData = new FileData(path.getAbsolutePath());
            saveToJSON();

        }else{
            path.mkdir();
            fileData = new FileData(path.getAbsolutePath());
            saveToJSON();
        }
    }

    private void saveToJSON(){
        File file;
        File jsonPath = new File(path+"/JSON");
        try{
            if(!jsonPath.exists()){
                jsonPath.mkdir();
            }
            file = new File(jsonPath + "/test.json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String form = gson.toJson(fileData);
            gson.toJsonTree(form);
            System.out.println(form);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(form);
            bw.close();
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }

    public File loadFileJSON(Context context) {
        Gson gson = new Gson();
        String jsonForm;
        File filePath = new File(path + "/JSON/test.json");

        try{
            if(filePath.exists()){
                jsonForm = StringUtils.readFileToString(filePath);
                fileData = gson.fromJson(jsonForm, FileData.class);
            }else{
                MessageUtils.showErrorMessage("Error with get video, try scan QR-code", context);
                return null;
            }
        }catch (IOException e) {
            System.out.println("Exception in fileOperation:" + e.toString());
        }

        File file = new File(path + "/" + fileData.fileName);
        return file;
    }

}
