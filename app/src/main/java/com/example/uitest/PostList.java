package com.example.uitest;

import android.content.Intent;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PostList extends AppCompatActivity {
    private String ussersSession;
    public List<ItemObj> Items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);
        ussersSession = getIntent().getStringExtra("userSession");
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_color_toobar));
        ListView listView=findViewById(R.id.verticalList);
        Items = new ArrayList<ItemObj>();
        GetList();
        CustomAdapter adapter=new CustomAdapter(this,R.layout.post_item, Items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }
    //@Override
    //public boolean onOptionsItemSelected(MenuItem item) {
    //    switch (item.getItemId()) {
    //        case android.R.id.home:
     //           this.finish();
      //          return true;
    //        default:
     //           return super.onOptionsItemSelected(item);
    //    }
    //}
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
