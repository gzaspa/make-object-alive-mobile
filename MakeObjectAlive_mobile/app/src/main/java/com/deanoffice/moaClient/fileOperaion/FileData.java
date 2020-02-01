package com.deanoffice.moaClient.fileOperaion;

import java.io.File;

public class FileData {

    private transient File file;

    int videoID;
    String fileName;
    String type;


    public File getFile(){
        if(file!=null){
            return file;
        }
        return null;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public FileData(String path){
        videoID++;
        createNewFile(path);
    }

    private void createNewFile(String path) {
        file = new File(path+"/video" + videoID + ".mp4");

        fileName = file.getName();
        type = "video";
    }



}
