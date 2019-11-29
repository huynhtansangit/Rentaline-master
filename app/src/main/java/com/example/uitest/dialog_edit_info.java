package com.example.uitest;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class dialog_edit_info extends AppCompatDialogFragment {
    public EditText et_add,et_phone,et_editusername;
    public TextView tv_gender,tv_birthday;
    public RadioButton rbtnmale,rbtnFemale,rbtnOther;
    public RadioGroup radioGroup;
    public Intent getPathIntent;
    public String pathAvt;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private EditDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.edit_dialog,null);

        builder.setView(view).setTitle("Edit Infomation").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        tv_birthday=view.findViewById(R.id.tv_birthday);
        et_add =view.findViewById(R.id.et_youraddress);
        et_phone=view.findViewById(R.id.et_phoneprofile);
        et_editusername=view.findViewById(R.id.et_editUsername);
        tv_gender =view.findViewById(R.id.tv_gender_dialog);
        rbtnmale =view.findViewById(R.id.rbtn_male);
        rbtnFemale =view.findViewById(R.id.rbtn_female);
        rbtnOther =view.findViewById(R.id.rbtn_other);
        radioGroup=view.findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkid) {
                if(checkid==rbtnmale.getId())
                {
                    tv_gender.setText("Male");
                }
                if(checkid==rbtnFemale.getId())
                {
                    tv_gender.setText("Female");
                }
                if(checkid==rbtnOther.getId())
                {
                    tv_gender.setText("3rd");
                }
            }
        });
        tv_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                    getContext(),
                    android.R.style.Theme_DeviceDefault_Dialog,
                    onDateSetListener,
                    1990,1,1);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        onDateSetListener =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month+=1;
                Calendar cal=Calendar.getInstance();
                String s_date=day +"/"+month+"/"+year;
                if (cal.get(Calendar.YEAR)-year<=10)
                {
                    tv_birthday.setText("Invalid age");
                    tv_birthday.setTextColor(Color.RED);
                }
                else {tv_birthday.setText(s_date);tv_birthday.setTextColor(Color.parseColor("##008B8B"));}
            }
        };
        return builder.create();
    }
    // to push data

}
