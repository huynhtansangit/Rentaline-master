package com.example.uitest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;

public class sorts_list extends AppCompatActivity {
    private String ussersSession;
    private String sortType="all";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorts_list);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_color_toobar));
        //Lấy session và tag
        // tag dùng để lấy item theo tag
        ussersSession = getIntent().getStringExtra("userSession");
        sortType = getIntent().getStringExtra("sort_Type");
        ListView listView=findViewById(R.id.sort_listview);
        SortItems adapter =new SortItems(this,R.layout.normalitemview,GetCategoryFromType(sortType));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

               @Override
               public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                      TextView tv= view.findViewById(R.id.normal_tv);
                      Intent intent = new Intent();
                      intent.setClass(sorts_list.this,postslist.class);
                      intent.putExtra("userSession",ussersSession);
                      intent.putExtra("sort_Type", tv.getTag().toString());
                      startActivity(intent);

               }
        });



    }
    public List<Item> GetCategoryFromType(String Tag){
        List<Item> list=new ArrayList<Item>();
        switch (Tag){
            case "type":{
                //list=new String[]{ "Xe ôtô","Xe du lịch","Xe bán tải"};
                list.add(new Item("Ôtô","oto"));
                list.add(new Item("Xe du lịch","xedl"));
                list.add(new Item("Xe bán tải","xebt"));
            } break;
            case "price": {
                //list=new String[]{"0-500k","500k-1000k","1000k-1500k","1500k-2000k",
                //        "3000k-3500k","3500k-4000k","4000k-5000k","trên 5000k",};
                list.add(new Item("0-1 triệu","1000"));
                list.add(new Item("1-2 triệu","2000"));
                list.add(new Item("2-3 triệu","3000"));
                list.add(new Item("3-4 triệu","4000"));
                list.add(new Item("4-5 triệu","5000"));
                list.add(new Item("5-6 triệu","6000"));
                list.add(new Item("6-7 triệu","7000"));
                list.add(new Item("7-8 triệu","8000"));
                list.add(new Item("8-9 triệu","9000"));
                list.add(new Item("9-10 triệu","10000"));
            } break;
            case "no_seat": {
                //list=new String[]{"4 chỗ","7 chỗ","16 chỗ", "29 chỗ", "45 chỗ"};
                list.add(new Item("4 chỗ","4ch"));
                list.add(new Item("7 chỗ","7ch"));
                list.add(new Item("16 chỗ","16ch"));
                list.add(new Item("29 chỗ","29ch"));
                list.add(new Item("45 chỗ","45ch"));
            } break;
            default: return null;
        }
        return list;
    }
    public class Item{
        private String name;
        private String tag;
        public Item(String _name, String _tag){
            this.name=_name;
            this.tag=_tag;
        }

        public String getName() {
            return name;
        }

        public String getTag() {
            return tag;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }
    }
    public class SortItems extends ArrayAdapter {
        Context context;
        List<Item> items;
        int resource;
        public SortItems(Context context, int resource, List<Item> items) {
            super(context, resource, items);
            this.context=context;
            this.items=items;
            this.resource=resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view= inflater.inflate(R.layout.normalitemview,null);
            TextView txt= view.findViewById(R.id.normal_tv);
            txt.setTag(items.get(position).getTag());
            txt.setText(items.get(position).getName());
            return view;
        }
    }
}
