package com.dualism.proj1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
import com.android.volley.toolbox.Volley;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login_Activity extends AppCompatActivity {

    Button bLogin;
    EditText etUsername, etPassword;
    TextView tvRegisterLink;
    String credentials;

    JSONObject okayResponse;
    RequestQueue queue;

    private String okValue;

    public void setOkValue(String okValue) {
        this.okValue = okValue;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername= (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        queue = Volley.newRequestQueue(this);
    }


    public void sendCredentials() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username", etUsername.getText().toString());
        intent.putExtra("credentials", credentials);
        intent.putExtra("Ok", okValue.substring(4));
        startActivity(intent);
    }

    public void login(View view) {

        final String TAG = "Lol";

        String url = "http://54.218.48.30:8080/checkuser";

        final Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("username", etUsername.getText().toString());
        postParam.put("password", etPassword.getText().toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                okayResponse = response;
                if (!response.isNull("value")) {
                    Log.d("axaxa", "kek");
                }
                try {
                    setOkValue(response.getString("value"));
                    Log.d("sa", okValue);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                checkOkValue();
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
                String plainCredentials = etUsername.getText().toString()+":"+etPassword.getText().toString();
                String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
                credentials = base64Credentials;
                headers.put("Authorization", "Basic " + base64Credentials);
                return headers;
            }


        };

        queue.add(jsonObjReq);

    }

    public void checkOkValue() {
        if(okValue != null) {
            Log.d("sa2", okValue);
            sendCredentials();
        }
    }

    public void tvRegister(View view) {
        startActivity(new Intent(this, Register_Activity.class));
    }
}
