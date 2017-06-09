package com.dualism.proj1.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dualism.proj1.Login_Activity;
import com.dualism.proj1.R;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Duelist on 09.06.2017.
 */

public class ExitDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {
    final String LOG_TAG = "DialogFragment";

    private String credentials;
    private RequestQueue queue;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
                .setTitle("Log Out").setPositiveButton(R.string.yes, this)
                .setNegativeButton(R.string.no, this)
                .setMessage(R.string.message_text);
        queue = Volley.newRequestQueue(getActivity());
        return adb.create();
    }

    public void onClick(DialogInterface dialog, int which) {
        int i = 0;
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                i = R.string.yes;

                String url = "http://54.218.48.30:8080/customlogout";
                final Map<String, String> postParam= new HashMap<String, String>();
                credentials = getArguments().getString("credentials");
                postParam.put("credentials", credentials);


                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                        url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(LOG_TAG, response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Log.d("TE", "TimeoutError");
                        } else if (error instanceof AuthFailureError) {
                            Log.d("AFE", "AuthFailureError");
                        } else if (error instanceof ServerError) {
                            Log.d("SE", "ServerError");
                        } else if (error instanceof NetworkError) {
                            Log.d("NE", "NetworkError");
                        } else if (error instanceof ParseError) {
                            Log.d("PE", "ParseError");
                        }
                    }
                }) {

                    /*
                     Passing some request headers
                     */
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        //put credentials here
                        headers.put("Authorization", "Basic " + credentials);
                        return headers;
                    }


                };
                //queue.add(jsonObjReq);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //mTextView.setText("Response is: "+ response);
                                Log.d(LOG_TAG, response);
                                //decoding received response string
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(LOG_TAG, "Log Out failed");
                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String>  headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                /*Intent intent = getIntent();
                base64Credentials = intent.getStringExtra("credentials");*/
                        headers.put("Authorization", "Basic "+credentials);
                        return headers;
                    }
                };

                queue.add(stringRequest);


                Intent intent = new Intent(getActivity(), Login_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                // getActivity().finish();
                break;
            case Dialog.BUTTON_NEGATIVE:
                i = R.string.no;
                break;
        }
        if (i > 0)
            Log.d(LOG_TAG, "Dialog 2: " + getResources().getString(i));
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d(LOG_TAG, "Dialog 2: onDismiss");
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d(LOG_TAG, "Dialog 2: onCancel");
    }
}
