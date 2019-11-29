package com.example.uitest;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class tab1 extends Fragment {
    private String usr_session;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        usr_session=bundle.getString("usr_ss");
        //createRecycleView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab1_frag, container, false);
        createRecycleView(view,R.id.rcleView1);
        createRecycleView(view,R.id.rcleView2);
        createRecycleView(view,R.id.rcleView3);
        return view;

    }

    public void createRecycleView(View view, int resource ){
        RecyclerView recyclerView= view.findViewById(resource);//R.id.rcleView
        ArrayList<ItemObj> list =new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        list.add(new ItemObj("post 1","15000đ"));
        list.add(new ItemObj("post 2","55000đ"));
        list.add(new ItemObj("post 3","65000đ"));
        list.add(new ItemObj("post 4","35000đ"));
        list.add(new ItemObj("post 5","85000đ"));
        list.add(new ItemObj("post 6","195000đ"));
        list.add(new ItemObj("post 1","15000đ"));
        list.add(new ItemObj("post 2","55000đ"));
        list.add(new ItemObj("post 3","65000đ"));
        list.add(new ItemObj("post 4","35000đ"));
        list.add(new ItemObj("post 5","85000đ"));
        list.add(new ItemObj("post 6","195000đ"));
        cusAdapter4rv adapter4rv=new cusAdapter4rv(list,this.getContext());
        recyclerView.setAdapter(adapter4rv);
        LinearLayoutManager manager=new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setNestedScrollingEnabled(false);
    }
}
