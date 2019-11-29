package com.example.uitest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
private Button btn_login,btn_face,btn_google;
    private TextView tv_forgotpass,tv_register;
    private ImageView logo_inLogin;
    private EditText et_emailname,et_pass;
    private String token, email_sending;
    private CheckBox cb_showpass;
    private ProgressBar prog_login;
    Retrofit.Builder builder=new Retrofit.Builder()
        .baseUrl("http://192.168.1.7:8000/")
        .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit=builder.build();
    UserInterface userInterface=retrofit.create(UserInterface.class);
String URL_LOGIN="http://192.168.1.7:8000/api/login";
    Current_user_cache User_cache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        prog_login.setVisibility(View.GONE);
        // remove status
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        cb_showpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    et_pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                else et_pass.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register_intent=new Intent(getApplicationContext(),register.class);
                Pair [] pairs =new Pair[2];
                pairs[0]=new Pair<View,String>(logo_inLogin,"logoImage");
                pairs[1]=new Pair<View,String>(btn_login,"btnTrans");
                ActivityOptions activityOptions=ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                startActivity(register_intent,activityOptions.toBundle());
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(confirmInput(view))
                {
                        Login();
                        btn_login.setEnabled(false);
                        prog_login.setVisibility(View.VISIBLE);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "invalidated input", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // greeting for fun
        final Random r=new Random();
        logo_inLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int i=r.nextInt((4-1)+1)+1;
                switch (i)
                {
                    case 1:
                        Toast.makeText(MainActivity.this, "Nice to see you agian", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, "Bonne journée", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(MainActivity.this, "È il nostro grande onore darvi il benvenuto", Toast.LENGTH_SHORT).show();
                        break;
                        default:
                            Toast.makeText(MainActivity.this, "Be our guest", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // REad Json
    private void findView ()
    {
        logo_inLogin=(ImageView)findViewById(R.id.im_rentaline);
        btn_login=(Button)findViewById(R.id.btn_login);
        btn_face=(Button)findViewById(R.id.btn_face);
        btn_google=(Button)findViewById(R.id.btn_google);
        tv_forgotpass=(TextView)findViewById(R.id.tv_forgotpass);
        tv_register=(TextView)findViewById(R.id.tx_register);
        et_emailname=findViewById(R.id.et_username);
        et_pass=findViewById(R.id.et_password);
        cb_showpass=findViewById(R.id.cb_showpass);
        prog_login=findViewById(R.id.pro_login);
    }
    private boolean validateUserEmail() {
        String usernameInput = et_emailname.getText().toString().trim();

        if (usernameInput.isEmpty()) {
            et_emailname.setError("Field can't be empty");
            return false;
        }
        else {
            et_emailname.setError(null);
            return true;
        }
    }
    private boolean validatePass() {
        String passInput = et_pass.getText().toString().trim();

        if (passInput.isEmpty()) {
            et_pass.setError("Field can't be empty");
            return false;
        }
        else {
            et_pass.setError(null);
            return true;
        }
    }
    public boolean confirmInput(View v) {
        if (!validateUserEmail()||!validatePass()) {
            return false;}
        else return true;
    }

    private void getInfoUser ()
    {
        Call<ResponseBody>call =userInterface.getInfoUser("Bearer" + " " + token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    try {

                        JsonParser parser = new JsonParser();
                        JsonElement mJson =  parser.parse(response.body().string());
                        Gson gson = new Gson();
                        // convert json obj into class and send it
                        User_cache = gson.fromJson(mJson, Current_user_cache.class);
                        // send User_cache to menu_activity

                        Thread thread=new Thread() {
                            @Override
                            public void run() {
                                try {
                                    sleep(1000);
                                    Intent Successfull_intent = new Intent(MainActivity.this, menu_activity.class);
                                    Successfull_intent.putExtra("Email",email_sending);
                                    Successfull_intent.putExtra("Token",token);
                                    startActivity(Successfull_intent);
                                    finish();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        thread.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else Toast.makeText(MainActivity.this, "Token bad "+token, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failure !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Login ()
    {
        final String email = this.et_emailname.getText().toString().trim();
        final String password = this.et_pass.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("token");

                            // get token
                            token=message;
                            email_sending = email;
                            if(!message.isEmpty()) {
                                getInfoUser();
                                Toast.makeText(MainActivity.this, "Login Success! ", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Login fail "+error.toString(), Toast.LENGTH_SHORT).show();
                        prog_login.setVisibility(View.GONE);
                        btn_login.setEnabled(true);
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
        }

    };
        // set timeout for Volley response.
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
            6000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void dummyCLick (View view)
    {
        // set visible for text click event
    }

}
