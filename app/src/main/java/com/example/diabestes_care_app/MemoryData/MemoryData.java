package com.example.diabestes_care_app.MemoryData;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public final class MemoryData {

    private static final String FILE_NAME = "example.txt";

    public static void saveLastMsgTS(String data, String chatId, Context context) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(chatId + "LastMsgTS.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getLastMsgTS(Context context, String chatId) {
        String data = "";
        try {
            FileInputStream fis = context.openFileInput(chatId + "LastMsgTS.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

//    public static void saveLastMsgTS(String data, String chatId, Context context) {
//        FileOutputStream fos = null;
//        try {
//            fos = context.openFileOutput(chatId + FILE_NAME, MODE_PRIVATE);
//            fos.write(data.getBytes());
//
//            Toast.makeText(context, "Saved to " + context.getFilesDir() + "/" + FILE_NAME+ data,
//                    Toast.LENGTH_LONG).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (fos != null) {
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Log.e("TAG_L", e.getMessage());
//                }
//            }
//        }
//    }
//
//    public static String getLastMsgTS(Context context, String chatId) {
//        String data = "";
//        try {
//            FileInputStream fis = context.openFileInput(chatId + FILE_NAME);
//            InputStreamReader isr = new InputStreamReader(fis);
//            BufferedReader bufferedReader = new BufferedReader(isr);
//            StringBuilder sb = new StringBuilder();
//            String line;
//            while ((line = bufferedReader.readLine()) != null) {
//                sb.append(line);
//            }
//            data = sb.toString();
//            Toast.makeText(context, "Get from " + context.getFilesDir() + "/" + FILE_NAME + data,
//                    Toast.LENGTH_LONG).show();
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.e("TAG_G", e.getMessage());
//        }
//        return data;
//    }

    public static void savePatientUsername(String data, Context context) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(data.getBytes());

            Toast.makeText(context, "Saved to " + context.getFilesDir() + "/" + FILE_NAME + data,
                    Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("TAG_PUS", e.getMessage());
                }
            }
        }
    }

    public static String getPatientUsername(Context context) {
        String data = "";
        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            Toast.makeText(context, "Get from " + context.getFilesDir() + "/" + FILE_NAME,
                    Toast.LENGTH_LONG).show();


        } catch (IOException e) {
            e.printStackTrace();
            Log.e("TAG_PUG", e.getMessage());
        }
        return data;
    }
}
