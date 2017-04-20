package com.dualism.proj1;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GetAudio extends AppCompatActivity {
    public MediaPlayer mediaPlayer;
    public String word;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_audio);

        final TextView mTextView = (TextView) findViewById(R.id.textView);
        mediaPlayer = new MediaPlayer();

        RequestQueue queue = Volley.newRequestQueue(this);
        //word for search
        word = "hello";
        //change for server url
        String url = "http://10.0.2.2:8080/audio/"+word;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mTextView.setText("Response is: "+ response);
                        //decoding received response string
                        decodeAudio(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });
        queue.add(stringRequest);
    }

    public void decodeAudio(String strAudio) {
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
            mediaPlayer.setDataSource(outputFile.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}