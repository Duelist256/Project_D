package com.dualism.proj1.Services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by glad on 29.05.17.
 */

public class PlayAudioService extends Service {
    final String LOG_TAG = "myLogs";
    String word;

    MediaPlayer mediaPlayer;
    String base64Credentials;

    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand");
        word = intent.getStringExtra("word");
        base64Credentials = intent.getStringExtra("credentials");
        someTask(word);
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "onBind");
        return null;
    }

    void someTask(final String word) {
        new Thread(new Runnable() {
            public void run() {
                playAudio(word);
                stopSelf();
            }
        }).start();
    }


    private void playAudio(String word) {
        RequestQueue queue = Volley.newRequestQueue(this);
        mediaPlayer = new MediaPlayer();
        final String TAG = "Play Audio Service: ";
        this.word = word;
        String url = "http://54.218.48.30:8080/audio/"+this.word;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //mTextView.setText("Response is: "+ response);
                        Log.d(TAG, "Word has found");
                        //decoding received response string
                        decodeAudio(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
                Log.d(TAG, "Probably, audio not found");
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                /*Intent intent = getIntent();
                base64Credentials = intent.getStringExtra("credentials");*/
                headers.put("Authorization", "Basic "+base64Credentials);
                return headers;
            }
        };
        queue.add(stringRequest);
    }

    private void decodeAudio(String strAudio) {
        byte[] audio = Base64.decode(strAudio, Base64.DEFAULT);
        File outputFile = null;

        try {
            //write received byte array to file
            outputFile = new File(getFilesDir()+File.separator+word+".flac");
            FileOutputStream fileoutputstream = new FileOutputStream(outputFile);
            fileoutputstream.write(audio);
            fileoutputstream.close();
            //done!
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            //testing audio file
            if (outputFile.exists()) {
                outputFile.setReadable(true, false);
            }

            mediaPlayer.setDataSource(outputFile.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
