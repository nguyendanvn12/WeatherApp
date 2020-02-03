package com.example.weatherapp.ui.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.weatherapp.R;

import java.util.ArrayList;

public class LocationActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView lvLocaltions;
    private Button btnAddLocation;
    private ImageButton btnBack;
    private AutoCompleteTextView tvAdd;
    private ArrayList<String> locals;
    private ArrayList<String> autoComplete;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        lvLocaltions = findViewById(R.id.lvLocation);
        btnBack = findViewById(R.id.btnBack);
        Intent intent = getIntent();
        locals = intent.getStringArrayListExtra("Location");
        adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,locals);
        lvLocaltions.setAdapter(adapter);
        btnBack.setOnClickListener(this);
        tvAdd = findViewById(R.id.tvLocation);
        btnAddLocation = findViewById(R.id.btnAdd);
        btnAddLocation.setOnClickListener(this);
        lvLocaltions.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showAlertDialog(position);
                return false;
            }
        });
        initData();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,autoComplete);
        tvAdd.setAdapter(arrayAdapter);
    }

    private void showAlertDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn xoá '"+locals.get(position)+"' không");
        builder.setCancelable(true);
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Xoá", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                locals.remove(position);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void initData() {
        autoComplete = new ArrayList<>();
        autoComplete.add("Hà Nội");
        autoComplete.add("Hưng yên");
        autoComplete.add("Haiphong");
        autoComplete.add("Cairo,eg");
        autoComplete.add("Cairo,us");
        autoComplete.add("Danang");
        autoComplete.add("Hue,vn");
        autoComplete.add("Hue,us");
        autoComplete.add("Manchester, GB");
        autoComplete.add("Thai Binh");
        autoComplete.add("Thai nguyen");
        autoComplete.add("Bac Ninh");
        autoComplete.add("Lao Cai");
        autoComplete.add("Nam Dinh");
        autoComplete.add("Thanh Hoa");
        autoComplete.add("Ca Mau");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAdd:
                String s = tvAdd.getText().toString();
                locals.add(s);
                adapter.notifyDataSetChanged();
                tvAdd.setText("");
                break;
            case R.id.btnBack:
            Intent intent = new Intent(this, MainActivity.class);
            intent.putStringArrayListExtra("Location",locals);
            setResult(RESULT_OK,intent);
            finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putStringArrayListExtra("Location",locals);
        setResult(RESULT_OK,intent);
        finish();
    }
}
