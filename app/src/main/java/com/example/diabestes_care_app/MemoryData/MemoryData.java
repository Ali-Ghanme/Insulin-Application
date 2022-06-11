package com.example.diabestes_care_app.MemoryData;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public final class MemoryData {

    public static void savePatientData(String data, Context context) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("data.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPatientData(Context context) {
        String data = "";
        try {
            FileInputStream fis = context.openFileInput("data.text");
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

    public static void saveDoctorData(String data, Context context) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("data.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getDoctorData(Context context) {
        String data = "";
        try {
            FileInputStream fis = context.openFileInput("data.text");
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
            FileInputStream fis = context.openFileInput(chatId + ".text");
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


}
