package com.deanoffice.moaClient.fileOperaion;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class MessageUtils {
    private MessageUtils(){}

    public static void showErrorMessage(String errorMessage, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Error");
        builder.setMessage(errorMessage);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ///
            }
        });
        //builder.create();
        builder.show();
    }

    public static void showMessage(String title, String message, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ///
            }
        });
        //builder.create();
        builder.show();
    }

}
