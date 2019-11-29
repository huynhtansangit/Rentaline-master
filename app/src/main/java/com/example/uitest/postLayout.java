package com.example.uitest;



import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class postLayout extends AppCompatActivity {
    public List<ItemObj> Items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_color_toobar));
        ListView listView=findViewById(R.id.verticalList);
        Items = new ArrayList<ItemObj>();
        GetList();
        CustomAdapter adapter=new CustomAdapter(this,R.layout.vertical_itemview, Items);
        listView.setAdapter(adapter);
        setListViewHeightBasedOnChildren(listView);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void GetList(){
        Items = new ArrayList<ItemObj>();
        Items.add(new ItemObj("Nguyễn Văn A","djjasjhasjhajsha"));
        Items.add(new ItemObj("Trần Văn B","djjasjhasjhajsha"));
        Items.add(new ItemObj("Lê Văn L","djjasjhasjhajsha"));
        Items.add(new ItemObj("Lý Tiểu L","djjasjhasjhajsha"));
        Items.add(new ItemObj("Nguyễn Văn A","djjasjhasjhajsha"));
        Items.add(new ItemObj("Trần Văn B","djjasjhasjhajsha"));
        Items.add(new ItemObj("Lê Văn L","djjasjhasjhajsha"));
        Items.add(new ItemObj("Lý Tiểu L","djjasjhasjhajsha"));
        Items.add(new ItemObj("Nguyễn Văn A","djjasjhasjhajsha"));
        Items.add(new ItemObj("Trần Văn B","djjasjhasjhajsha"));
        Items.add(new ItemObj("Lê Văn L","djjasjhasjhajsha"));
        Items.add(new ItemObj("Lý Tiểu L","djjasjhasjhajsha"));
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
