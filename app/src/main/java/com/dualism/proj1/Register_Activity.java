package com.dualism.proj1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Register_Activity extends AppCompatActivity {
    //Button bRegister;
    EditText etName, etEmail, etPassword;
    JSONObject okResponse;
    String okResp;

    private boolean isResponsesEqual = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = (EditText) findViewById(R.id.etName);
        //etAge = (EditText) findViewById(R.id.etAge);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword= (EditText) findViewById(R.id.etPassword);

        /*try {
            okResponse = new JSONObject("{\"value\":\"Ok\"}");
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        //lelResponse = "{\"value\":\"Ok\"}";
        /*bRegister = (Button) findViewById(R.id.bRegister);
        bRegister.setOnClickListener(this);*/
    }

    public void register(View view) {
        //showProgressDialog();

        final String TAG = "Lol";
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://25.80.63.196:8080/saveuser";

        final Map<String, String> postParam= new HashMap<String, String>();
        //postParam.put("id", "228");
        postParam.put("email", etEmail.getText().toString());
        postParam.put("username", etName.getText().toString());
        postParam.put("password", etPassword.getText().toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        okResponse = response;
                        try {
                            okResp = response.getString("value");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //okResponse = response.toString();
                        //msgResponse.setText(response.toString());
                        //hideProgressDialog();
                        checkOkResp();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //VolleyLog.d(TAG, "Error: " + error.getMessage());
                        //hideProgressDialog();
                        // System.out.println("Ty che mm?");
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Log.d("TimeoutError", "TE");
                        } else if (error instanceof AuthFailureError) {
                            Log.d("AuthFailureError", "AFE");
                        } else if (error instanceof ServerError) {
                            Log.d("ServerError", "SE");
                        } else if (error instanceof NetworkError) {
                            Log.d("NetworkError", "NE");
                        } else if (error instanceof ParseError) {
                            Log.d("ParseError", "PE");
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
                return headers;
            }



        };

        // Adding request to request queue
        //AppController.getInstance().addToRequestQueue(jsonObjReq,tag_json_obj);
        queue.add(jsonObjReq);

        /*//mPostCommentResponse.requestStarted();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //mPostCommentResponse.requestCompleted();
                Log.d("norm", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mPostCommentResponse.requestEndedWithError(error);
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                *//*Map<String,String> params = new HashMap<String, String>();
                params.put("user",userAccount.getUsername());
                params.put("pass",userAccount.getPassword());
                params.put("comment", Uri.encode(comment));
                params.put("comment_post_ID",String.valueOf(postId));
                params.put("blogId",String.valueOf(blogId));*//*
                Map<String, String> params = postParam; // bred
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        queue.add(sr);*/
    }

    public void checkOkResp() {
        if (okResp.equals("Ok")) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("Ok", etName.getText().toString());
            startActivity(intent);
        }
    }
}
