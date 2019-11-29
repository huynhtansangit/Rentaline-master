package com.example.uitest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class register extends AppCompatActivity {
    private ImageButton Ibtn_back;
    private Button  btn_Create;
    private EditText et_yourname,et_password,et_email,et_password_confirm;
    private TextView tv_create_account;
    private ProgressBar pro_register;
    TextInputLayout til_name,til_email,til_pass,til_passconfirm;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[!@#$%^&()+=])" +    //at least 1 special character
                    //"(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 4 characters
                    "$");

private static String URL_REGIST = "http://192.168.1.7:8000/api/register";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        findView();
        Ibtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                // make effect for changing activity
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });
        btn_Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (confirmInput(view)) {
                    btn_Create.setVisibility(View.GONE);
                    pro_register.setVisibility(View.VISIBLE);
                    Regist();
                }
                else return;
            }
        });
        tv_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(register.this, "Create account\n\tnghĩa là\ntạo tài khoản đó bạn", Toast.LENGTH_SHORT).show();
            }
        });

    }
private  void findView ()
{
    Ibtn_back=(ImageButton)findViewById(R.id.Ibtn_back);
    btn_Create=(Button)findViewById(R.id.btn_create);
    et_yourname=(EditText)findViewById(R.id.et_register_name);
    et_email=(EditText)findViewById(R.id.et_regis_email);
    et_password=(EditText)findViewById(R.id.et_regis_password);
    et_password_confirm=(EditText)findViewById(R.id.et_regis_password_confirm);
    tv_create_account=(TextView)findViewById(R.id.tv_CreateAccount);
    til_pass=findViewById(R.id.tv_regis_password);
    til_name=findViewById(R.id.tv_regis_name);
    til_email=findViewById(R.id.tv_regis_email);
    til_passconfirm=findViewById(R.id.tv_regis_password_confirm);
    pro_register=findViewById(R.id.progress_bar_register);
}

    private boolean validateEmail() {
        String emailInput = til_email.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            til_email.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            til_email.setError("Please enter a valid email address");
            return false;
        } else {
            til_email.setError(null);
            return true;
        }
    }

    private boolean validateUsername() {
        String usernameInput = til_name.getEditText().getText().toString().trim();

        if (usernameInput.isEmpty()) {
            til_name.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 15) {
            til_name.setError("Username too long");
            return false;
        } else {
            til_name.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = til_pass.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            til_pass.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            til_pass.setError("Password too weak");
            return false;
        } else {
            til_pass.setError(null);
            return true;
        }
    }
    private boolean validateConfirmPassword() {
        String password_confirmInput = et_password_confirm.getText().toString().trim();
        if (password_confirmInput.isEmpty()) {
            til_passconfirm.setError("Field can't be empty");
            return false;
        } else if (!password_confirmInput.equals(et_password.getText().toString().trim())) {
            til_passconfirm.setError("Not match");
            return false;
        } else {
            til_passconfirm.setError(null);
            return true;
        }
    }
    private boolean confirmInput(View v) {
        if (!validateEmail() | !validateUsername() | !validatePassword() |!validateConfirmPassword()) {
            return false;}
            else return true;
        }





        private void Regist(){
        final String name = this.et_yourname.getText().toString().trim();
        final String email = this.et_email.getText().toString().trim();
        final String password = this.et_password.getText().toString().trim();
        final String c_password = this.et_password_confirm.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            final String message = jsonObject.getString("message");
                            Thread thread=new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        sleep(2000);
                                        if(message.contains("Successful")) {
                                            Toast.makeText(register.this, "Register Success!", Toast.LENGTH_SHORT).show();
                                            Intent Successfull_intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(Successfull_intent);
                                        }
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            thread.start();
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(register.this, " ", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        btn_Create.setVisibility(View.VISIBLE);
                        pro_register.setVisibility(View.GONE);
                        Toast.makeText(register.this, "Register fail "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("password_confirmation", c_password);
                return params;
            }
        };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                4000,
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
