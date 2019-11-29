package com.example.uitest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uitest.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class DashBoard_Activity extends Fragment {

    private String UserSession;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_dash_board_);
        View view =inflater.inflate(R.layout.activity_dash_board_,container,false);
        setUserSession();
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getActivity(), getChildFragmentManager(),getUserSession());
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        return view;
    }

    public void setUserSession(){
        UserSession="xxx";
    }
    public String getUserSession(){
        return UserSession;
    }
}
