package edu.fvtc.galleryapp;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ManageFile {

    public static final String TAG = "MainActivity";

    public static String readFile(int fileId, AppCompatActivity appCompatActivity) throws IOException {

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = null;

        try {

            inputStream = appCompatActivity.getResources().openRawResource(fileId);
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            stringBuffer = new StringBuffer();

            String data = null;

            while ((data = bufferedReader.readLine()) != null) {
                stringBuffer.append(data + "\n");
            }

            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();

        }catch (IOException ex) {
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            Log.d(TAG, "readFile: Error reading file");
            throw ex;
        }
        return stringBuffer.toString();
    }
}
