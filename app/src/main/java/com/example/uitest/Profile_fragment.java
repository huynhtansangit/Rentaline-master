package com.example.uitest;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile_fragment extends Fragment implements  EditDialogListener {
    private TextView tv_address,tv_phone,tv_gender,tv_birthday,tv_nameinProfileFrag,tv_CreatedDateInProfileFrag;
    private ImageView iv_avt_pro_frag;
    private Button btnEdit;
    private String email;
    private Current_user_cache User_cache;
    private String token;
    Retrofit.Builder builder=new Retrofit.Builder()
        .baseUrl("http://192.168.1.7:8000/")
        .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit=builder.build();
    UserInterface userInterface=retrofit.create(UserInterface.class);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view= inflater.inflate(R.layout.fragment_profile_fragment, container, false);
        findView(view);
        token=getArguments().getString("token");
        getInfoUser();
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        return view;
    }
    public void openDialog()
    {
        dialog_edit_info dialogEditInfo=new dialog_edit_info();
        dialogEditInfo.show(getFragmentManager(),"Edit_dialog");
    }
    private void findView (View view)
    {
        tv_address= view.findViewById(R.id.tv_youraddress);
        tv_birthday= view.findViewById(R.id.tv_birthday);
        tv_gender= view.findViewById(R.id.tv_gender);
        tv_phone= view.findViewById(R.id.tv_yourphone);
        iv_avt_pro_frag=view.findViewById(R.id.im_Avt_profile);
        btnEdit=view.findViewById(R.id.btn_edit_profile);
        tv_nameinProfileFrag=view.findViewById(R.id.tv_fragYourname);
        tv_CreatedDateInProfileFrag=view.findViewById(R.id.tv_FragdatebeginUsing);
    }
    private void loadImageintoAvatar (String link,final ImageView imageView )
    {
        Glide.with(this).load(link).apply(RequestOptions.circleCropTransform()).into(imageView);
    }
    private void initProfile ()
    {
        if(User_cache.getAddress()==null)
        {
            tv_address.setText("Not filled yet");
        }
        else tv_address.setText(User_cache.getAddress().trim());

        if(User_cache.getBirth_day()==null)
        {
            tv_birthday.setText("Not filled yet");
        }
        else tv_birthday.setText(User_cache.getBirth_day().trim());
        if(User_cache.getGender()==null)
        {
            tv_gender.setText("Not filled yet");
        }
        else tv_gender.setText(User_cache.getGender().trim());
        if(User_cache.getPhone()==null)
        {
            tv_phone.setText("Not filled yet");
        }
        else tv_phone.setText(User_cache.getPhone().trim());
        loadImageintoAvatar(User_cache.getAvatar(),iv_avt_pro_frag);
        tv_nameinProfileFrag.setText(User_cache.getName());
        tv_CreatedDateInProfileFrag.setText(User_cache.getCreated_at());
    }
    private void getInfoUser ()
    {
        Call<ResponseBody> call =userInterface.getInfoUser("Bearer" + " " + token);
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
                        initProfile();
                        Toast.makeText(getContext(), "  "+token, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else Toast.makeText(getContext(), "Token bad "+token, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Failure !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
