package com.example.uitest;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class postslist extends AppCompatActivity {
    private String ussersSession;
    public List<ItemObj> Items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postslist);
        //getActionBar().setDisplayShowHomeEnabled(true);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setDisplayShowTitleEnabled(false);
        //getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_color_toobar));
        ussersSession = getIntent().getStringExtra("userSession");
        ListView listView=findViewById(R.id.verticalList);
        Items = new ArrayList<ItemObj>();
        GetList();
        CustomAdapter adapter=new CustomAdapter(this,R.layout.post_item, Items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            // đi đến post
            }
        });
    }
    public void GetList(){
        Items = new ArrayList<ItemObj>();
        Items.add(new ItemObj("Xe A","99000000đ"));
        Items.add(new ItemObj("Xe B","10500000đ"));
        Items.add(new ItemObj("Xe L","10034000đ"));
        Items.add(new ItemObj("Xe L","10340000đ"));
        Items.add(new ItemObj("Xe A","10003400đ"));
        Items.add(new ItemObj("Xe B","231000000đ"));
        Items.add(new ItemObj("Xe L","8000000đ"));
        Items.add(new ItemObj("Xe L","1400500đ"));
        Items.add(new ItemObj("Xe A","1700000đ"));
        Items.add(new ItemObj("Xe B","500000đ"));
        Items.add(new ItemObj("Xe L","3000000đ"));
        Items.add(new ItemObj("Xe L","1004000đ"));
    }

}
