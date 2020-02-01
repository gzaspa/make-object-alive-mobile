package com.deanoffice.moaClient.fileOperaion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class StringUtils {
    private StringUtils(){}

    public static String readFileToString(File path) throws IOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
            Reader reader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[8192];
            int read;
            while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
                builder.append(buffer, 0, read);
            }
            return builder.toString();
        } finally {
            fis.close();
        }
    }

    private static boolean pathIsExists(String path){
        File file = new File(path);
        if(file.exists()){
            return true;
        }
        return false;
    }

}
