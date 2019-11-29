package com.example.uitest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.uitest.DashBoard_Activity;
import com.example.uitest.MainActivity;
import com.example.uitest.Profile_fragment;
import com.example.uitest.R;
import com.example.uitest.contact_fragment;
import com.example.uitest.support_fragment;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.zip.Inflater;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class menu_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ImageView imageAvatar,imAvtProfile;
    private TextView tv_navi_name,tv_navi_email;
    private NavigationView navigationView;
    private LinearLayout wallpaper_Llayout;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    // Fragment
    private DashBoard_Activity dashBoardActivity ;
    private contact_fragment contactFragment;
    private Profile_fragment profileFragment;
    private support_fragment supportFragment;
    // variable to get path avt and background
    public Uri pathAvt;
    public Intent getPathIntent;
    public Uri pathProfileBackground;
    //
    private Current_user_cache User_cache;
    private String token;
    Retrofit.Builder builder=new Retrofit.Builder()
        .baseUrl("http://192.168.1.7:8000/")
        .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit=builder.build();
    UserInterface userInterface=retrofit.create(UserInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_activity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        Intent successfull_login =getIntent();
        navigationView=(NavigationView)findViewById(R.id.navigattionview);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        wallpaper_Llayout=(LinearLayout)headerView.findViewById(R.id.linearLayout) ;
        drawerLayout =(DrawerLayout)findViewById(R.id.Drawer_layout) ;
        imageAvatar=(ImageView)headerView.findViewById(R.id.im_avatar);
        tv_navi_name=(TextView)headerView.findViewById(R.id.navigation_username);
        tv_navi_email=(TextView)headerView.findViewById(R.id.navigation_email);
        // initiate action toolbar;
        initActionBar();
        // picture for testing
        int idResource=R.mipmap.ic_launcher;
        // loading image into  wall
        

        getInfoUser (successfull_login);
        //loadImageintoDrawer(image_link3,drawerLayout);
        //Create fragments
        if (savedInstanceState==null)
        {
            Bundle bundle=new Bundle();
            bundle.putString("token",token);
            profileFragment=new Profile_fragment();
            profileFragment.setArguments(bundle);
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.include_fragment,profileFragment,"Profile");
            transaction.commit();
            navigationView.setCheckedItem(R.id.item_profile);
        }
        // evevnt click to change avt image
        imageAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPathIntent=new Intent(Intent.ACTION_GET_CONTENT);
                getPathIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(getPathIntent,"Select picture"),10 );
            }
        });
        // and here is to change background
        wallpaper_Llayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder =new AlertDialog.Builder(menu_activity.this);
                builder.setTitle("Confirmation! Ask for sure")
                        .setMessage("Would you like to change another background ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getPathIntent=new Intent(Intent.ACTION_GET_CONTENT);
                                getPathIntent.setType("image/*");
                                startActivityForResult(Intent.createChooser(getPathIntent,"Select picture"),11 );
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
        //

    }
    // initialization
    private void init (Intent successfull_login)
    {
        String email=successfull_login.getStringExtra("Email");
        //User_cache=(Current_user_cache)successfull_login.getSerializableExtra("User_cache");
        tv_navi_name.setText(User_cache.getName());
        tv_navi_email.setText(email);
        loadImageintoAvatar(User_cache.getAvatar(),imageAvatar);
        if (User_cache.getWallpaper()==null)
        {
            loadImageintoWallDefault(wallpaper_Llayout);
        }else loadImageintoWallWithLink(User_cache.getWallpaper(),wallpaper_Llayout);
    }


    // load hình địa chỉ ảnh trên internet lên tường
    private void loadImageintoDrawer (String link,final DrawerLayout drawerLayout )
    {
        Glide.with(this).load(link).into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                drawerLayout.setBackground(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
            }

        });
    }
    private void loadImageintoWallWithLink (String link,final LinearLayout linearLayout )
    {
        Glide.with(this).load(link).apply(RequestOptions.fitCenterTransform()).into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                linearLayout.setBackground(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
            }

        });
    }
    private void loadImageintoWallDefault (final LinearLayout linearLayout)
    {
        Glide.with(this).load(R.mipmap.wallpaper_default).apply(RequestOptions.fitCenterTransform()).into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                linearLayout.setBackground(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
            }

        });
    }
    // load a path of image to make avatar
    private void loadImageintoAvatar (String link,final ImageView imageView )
    {
        Glide.with(this).load(link).apply(RequestOptions.circleCropTransform()).into(imageView);
    }

    public void dummyCLick(View view) {
    }
    private void initActionBar()
    {
        Toolbar toolbar= (Toolbar) findViewById(R.id.action_toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    //implement onNavigationItemSeleted
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id=menuItem.getItemId();
        if(id==R.id.item_Dashboard)
        {
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.include_fragment,new DashBoard_Activity(),"Dashboard");
            transaction.commit();

        }
        if(id==R.id.item_Contact)
        {
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.include_fragment,new contact_fragment(),"Contact");
            transaction.commit();
        }
        if(id==R.id.item_Help)
        {
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.include_fragment,new support_fragment(),"Help");
            transaction.commit();
        }
        if(id==R.id.item_profile)
        {
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.include_fragment,profileFragment,"Profile");
            transaction.commit();
        }
        if(id==R.id.item_Logout)
        {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
    @Override
    public  void onBackPressed()
    {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);

        }else {
            super.onBackPressed();
        }
    }
    @Override
    // get path image
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case 11:
                if (resultCode==RESULT_OK)
                    pathProfileBackground=data.getData();
                try {
                    loadImageintoWallWithLink(pathProfileBackground.toString(),wallpaper_Llayout);
                    editProfile(pathProfileBackground,token,"wallpaper");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 10:
                if(resultCode==RESULT_OK){
                    pathAvt=data.getData();
                    // save uri of image into database.
                    try {

                        loadImageintoAvatar(pathAvt.toString(),imageAvatar);
                        loadImageintoAvatar(pathAvt.toString(),(ImageView)profileFragment.getView().findViewById(R.id.im_Avt_profile));
                        editProfile(pathAvt,token,"avatar");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
        }
    }

    private void editProfile(Uri imgPath, String tokenAuth, String key)
    {
        HashMap<String,Object> map=new HashMap<>();
        map.put(key,imgPath.toString());
        Call<ResponseBody> post=userInterface.EditProfile("Bearer"+" "+tokenAuth,map);
        post.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(menu_activity.this, "Updated", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(menu_activity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(menu_activity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getInfoUser (final Intent successfull_login)
    {

        token=successfull_login.getStringExtra("Token");
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
                        init(successfull_login);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else Toast.makeText(menu_activity.this, "Token bad "+token, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(menu_activity.this, "Failure !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
