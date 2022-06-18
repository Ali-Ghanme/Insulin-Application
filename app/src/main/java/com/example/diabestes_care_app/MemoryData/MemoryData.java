package com.example.diabestes_care_app.MemoryData;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public final class MemoryData {

    private static final String FILE_NAME = "example.txt";

    public static void SaveData(String data, String chatId, Context context) throws IOException {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(FILE_NAME + chatId, MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("TAG",e.getMessage());
        }
    }

    public static String GetData(Context context, String chatId) {
        String data = "0";
        try {
            FileInputStream fis = context.openFileInput(chatId + FILE_NAME);
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
            Log.e("TAG",e.getMessage());
        }
        return data;
    }

    public static void saveLastMsgTS(String data, String chatId, Context context) {
            FileOutputStream fos = null;
            try {
                fos = context.openFileOutput(chatId + FILE_NAME, MODE_PRIVATE);
                fos.write(data.getBytes());

                Toast.makeText(context, "Saved to " + context.getFilesDir() + "/" + FILE_NAME,
                        Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("TAG",e.getMessage());
                    }
                }
            }
        }

    public static String getLastMsgTS(Context context, String chatId) {
        String data = "";
        try {
            FileInputStream fis = context.openFileInput(chatId + FILE_NAME);
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

//    public void save(String data, String chatId, Context context) {
//        FileOutputStream fos = null;
//
//        try {
//            fos = context.openFileOutput(chatId + FILE_NAME, MODE_PRIVATE);
//            fos.write(data.getBytes());
//
//            Toast.makeText(context, "Saved to " + context.getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (fos != null) {
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Log.e("TAG", e.getMessage());
//                }
//            }
//        }
//    }
//
//    public String load(String chatId) {
//        FileInputStream fis = null;
//        String data = "0";
//
//        try {
//            fis = getActivity().openFileInput(chatId + FILE_NAME);
//            InputStreamReader isr = new InputStreamReader(fis);
//            BufferedReader br = new BufferedReader(isr);
//            StringBuilder sb = new StringBuilder();
//            String text;
//
//            while ((text = br.readLine()) != null) {
//                sb.append(text).append("\n");
//            }
//            data = sb.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (fis != null) {
//                try {
//                    fis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return data;
//    }
}
