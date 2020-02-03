package com.example.weatherapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.weatherapp.ui.adapter.Onclick;
import com.example.weatherapp.R;
import com.example.weatherapp.data.model.CurrentWeather;
import com.example.weatherapp.data.model.List;
import com.example.weatherapp.data.model.WeatherFiveDays;
import com.example.weatherapp.data.remote.ApiUtils;
import com.example.weatherapp.data.remote.CurrentWeatherService;
import com.example.weatherapp.data.remote.FiveDaysWeatherService;
import com.example.weatherapp.ui.adapter.DayhourAdapter;
import com.example.weatherapp.ui.adapter.LocalAdapter;
import com.example.weatherapp.ui.adapter.WeekdaysAdapter;
import com.example.weatherapp.ui.layoutmanager.CustomLayoutManager;
import com.example.weatherapp.ui.layoutmanager.CustomLayoutManager2;
import com.example.weatherapp.ui.layoutmanager.CustomLayoutManagerVerTical;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvWeekday;
    private RecyclerView rvLocalContainer;
    private RecyclerView rvDayhour;
    private TextView tvTemp;
    private TextView tvMain;
    private TextView tvMaxMin;
    private ImageView ivTempIcon;
    private ImageButton ibLocation;
    private ConstraintLayout clBackground;
    private LocalAdapter adapter;
    private ArrayList<String> locals = new ArrayList<String>();
    private ArrayList<String> days = new ArrayList<>();
    private ArrayList<String> hour = new ArrayList<String>();
    private DayhourAdapter dayhourAdapter;
    private CurrentWeatherService currentWeatherService;
    private FiveDaysWeatherService fiveDaysWeatherService;
    private String activeLocation = "Hà Nội";
    private int acviveHour;
    private int activeDay;
    private WeekdaysAdapter sampleAdapter;
    private final SnapHelper snapHelper2 = new LinearSnapHelper();
    final SnapHelper snapHelper1 = new LinearSnapHelper();
    final SnapHelper snapHelper = new LinearSnapHelper();
    private Onclick onclickHour;
    private Onclick onclickLocation;
    private Onclick onclickDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvWeekday = findViewById(R.id.rvWeekday);
        rvLocalContainer = findViewById(R.id.rvLocalContainer);
        rvDayhour = findViewById(R.id.rvTime);
        tvMain = findViewById(R.id.tvMain);
        tvTemp = findViewById(R.id.tvTemp);
        ivTempIcon = findViewById(R.id.ivWeather);
        clBackground = findViewById(R.id.clBackground);
        tvMaxMin = findViewById(R.id.tvMaxMinTemp);
        ibLocation = findViewById(R.id.ibLocationPicker);
        currentWeatherService = ApiUtils.getCurrentWeatherService();
        fiveDaysWeatherService = ApiUtils.getFiveDaysWeatherService();
        onclickHour = new Onclick() {
            @Override
            public void setOnclick(int pos) {
                rvDayhour.scrollToPosition(pos);
                changeActiveHour();
            }
        };
        onclickDay = new Onclick() {
            @Override
            public void setOnclick(int pos) {
                rvWeekday.scrollToPosition(pos);
                changeActiveDay();
            }
        };
        onclickLocation = new Onclick() {
            @Override
            public void setOnclick(int pos) {
                rvLocalContainer.scrollToPosition(pos);
                Log.d("aaa",locals.get(pos));
                activeLocation = locals.get(pos);
                changeActiveLocation();
            }
        };
        CustomLayoutManager2 lm = new CustomLayoutManager2(this, LinearLayoutManager.HORIZONTAL, false);
       // lm.setActiveBackgroundResource(R.drawable.item_select);
        //lm.setNonActiveBackgroundResource(R.drawable.item_select);
        rvLocalContainer.setLayoutManager(lm);
        CustomLayoutManager lm1 = new CustomLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        lm1.setPivotY(100);
        rvWeekday.setLayoutManager(lm1);
        CustomLayoutManagerVerTical lm2 = new CustomLayoutManagerVerTical(this, LinearLayoutManager.VERTICAL, false);
        rvDayhour.setLayoutManager(lm2);
        lm2.setActiveBackgroundResource(R.drawable.item_select);
        locals.add("Hà Nội");
        locals.add("Hưng Yên");
        locals.add("Thanh pho Ho Chi Minh");
        initData();
       if(locals.size()>0){
           callApiCurrent();
       }
        adapter = new LocalAdapter(this, locals,onclickLocation);
        rvLocalContainer.setAdapter(adapter);

        snapHelper.attachToRecyclerView(rvLocalContainer);
        rvLocalContainer.setOnFlingListener(snapHelper);
        sampleAdapter = new WeekdaysAdapter(days,onclickDay);
        rvWeekday.setAdapter(sampleAdapter);


        snapHelper1.attachToRecyclerView(rvWeekday);
        lm1.setActiveBackgroundResource(R.drawable.item_select);


        snapHelper2.attachToRecyclerView(rvDayhour);
        dayhourAdapter = new DayhourAdapter(hour,onclickHour);
        rvDayhour.setAdapter(dayhourAdapter);


        rvLocalContainer.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == 0) {
                    changeActiveLocation();
                }
            }
        });
        rvWeekday.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == 0) {
                    changeActiveDay();
                }
            }
        });

        rvDayhour.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == 0) {
                   changeActiveHour();
                }


            }
        });
        ibLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LocationActivity.class);
                intent.putStringArrayListExtra("Location",locals);
                startActivityForResult(intent,1);
                overridePendingTransition(R.anim.bottom_up, R.anim.fade_in);
            }
        });
    }
    private void changeActiveHour() {
        View view = snapHelper2.findSnapView(rvDayhour.getLayoutManager());
        acviveHour = rvDayhour.getChildLayoutPosition(view);
        callApiFiveDay();
    }
    private void changeActiveDay() {
        View view = snapHelper1.findSnapView(rvWeekday.getLayoutManager());
        activeDay = rvDayhour.getChildLayoutPosition(view);
        callApiFiveDay();
    }
    private void changeActiveLocation() {
        TextView view = (TextView) snapHelper.findSnapView(rvLocalContainer.getLayoutManager());
        callApiFiveDay();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1&& resultCode==RESULT_OK){
            locals.clear();
            locals.addAll(data.getStringArrayListExtra("Location"));
            adapter.notifyDataSetChanged();
            rvLocalContainer.scrollToPosition(0);
            changeActiveLocation();
            Log.d("ccc",locals.get(0)+locals.get(1));
        }
    }

    private void initData() {
        for(int i =0; i <8;i++){
            if(i*3<10){
                hour.add("0"+i*3+":00");
            }else {
                hour.add(i*3+":00");
            }
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("E");
        Date date = new Date();
       if(date.getHours()<3){
           rvDayhour.scrollToPosition(0);
           acviveHour = 0;
       }else if(date.getHours()<6){
           rvDayhour.scrollToPosition(1);
           acviveHour = 1;
       }else if(date.getHours()<9){
           rvDayhour.scrollToPosition(2);
           acviveHour = 2;
       }else if(date.getHours()<12){
           rvDayhour.scrollToPosition(3);
           acviveHour = 3;
       }else if(date.getHours()<15){
           rvDayhour.scrollToPosition(4);
           acviveHour = 4;
       }else if(date.getHours()<18){
           rvDayhour.scrollToPosition(5);
           acviveHour = 5;
       }else if(date.getHours()<21){
           rvDayhour.scrollToPosition(6);
           acviveHour = 6;
       }
       else{
            rvDayhour.scrollToPosition(7);
           acviveHour = 7;
       }
        for(int i = 0; i <5;i++){
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE,i);
            days.add(dateFormat.format(c.getTime()));
        }
        rvWeekday.scrollToPosition(0);
    }
    private void callApiCurrent() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", activeLocation);
        params.put("units", "metric");
        params.put("appid", "ae3ddcca15f82452050aae4573d75f1c");

        currentWeatherService.getCurrentWeather(params).enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                tvTemp.setText("" + Math.round(response.body().getMain().getTemp()));
                tvMain.setText(response.body().getWeather().get(0).getMain());
                tvMaxMin.setText(Math.round(response.body().getMain().getTempMin()) + "/" + Math.round(response.body().getMain().getTempMax()));
                updateBackground();

            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), R.string.internet_connection_error, Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void callApiFiveDay(){
        Map<String, String> params = new HashMap<String, String>();
        Log.d("ac",activeLocation);
        params.put("q", activeLocation);
        params.put("units", "metric");
        params.put("appid", "ae3ddcca15f82452050aae4573d75f1c");
        fiveDaysWeatherService.getFiveDaysWeather(params).enqueue(new Callback<WeatherFiveDays>() {
            @Override
            public void onResponse(Call<WeatherFiveDays> call, Response<WeatherFiveDays> response) {
                int countToDayHour =0;
                int countNotavailableHour = 0;
                switch (response.body().getList().get(0).getDtTxt().substring(11,13)){
                    case "00":
                        countNotavailableHour=0;
                        break;
                    case "03":
                        countNotavailableHour=1;
                        break;
                    case "06":
                        countNotavailableHour=2;
                        break;
                    case "09":
                        countNotavailableHour=3;
                        break;
                    case "12":
                        countNotavailableHour=4;
                        break;
                    case "15":
                        countNotavailableHour=5;
                        break;
                    case "18":
                        countNotavailableHour=6;
                        break;
                    case "21":
                        countNotavailableHour=7;
                        break;

                }
                Date date =  new Date();
                for (List item:response.body().getList()) {
                    if(!date.toString().substring(8,10).equals(item.getDtTxt().substring(8,10))){
                        break;
                    }
                    countToDayHour++;
                }
                if(activeDay>0){
                    int index = (activeDay-1)*8+countToDayHour+acviveHour;
                    tvTemp.setText("" + Math.round(response.body().getList().get(index).getMain().getTemp()));
                    tvMain.setText(response.body().getList().get(index).getWeather().get(0).getMain());
                    tvMaxMin.setText(Math.round(response.body().getList().get(index).getMain().getTempMin()) + "/" + Math.round(response.body().getList().get(index).getMain().getTempMax()));
                    updateBackground();

                }else {

                    if(acviveHour>=countNotavailableHour){
                        int index = acviveHour-countNotavailableHour;
                        tvTemp.setText("" + Math.round(response.body().getList().get(index).getMain().getTemp()));
                        tvMain.setText(response.body().getList().get(index).getWeather().get(0).getMain());
                        tvMaxMin.setText(Math.round(response.body().getList().get(index).getMain().getTempMin()) + "/" + Math.round(response.body().getList().get(index).getMain().getTempMax()));
                        updateBackground();

                    }else{
                        tvTemp.setText("Thời tiết không khả dụng cho khoảng thời gian này");
                        tvMain.setText("");
                        tvMaxMin.setText("");
                    }
                }


            }

            @Override
            public void onFailure(Call<WeatherFiveDays> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.internet_connection_error, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateBackground() {
        switch (tvMain.getText().toString()) {
            case "Thunderstorm":
                clBackground.setBackgroundResource(R.drawable.background_storm);
                ivTempIcon.setImageResource(R.drawable.icon_weather_thunderstorm);
                break;
            case "Drizzle":
            case "Rain":
                clBackground.setBackgroundResource(R.drawable.background_rain);
                ivTempIcon.setImageResource(R.drawable.icon_feather_cloud_rain);
                break;
            case "Clear":
                clBackground.setBackgroundResource(R.drawable.background_sunny);
                ivTempIcon.setImageResource(R.drawable.icon_weather_day_sunny);
                break;

            default:
                clBackground.setBackgroundResource(R.drawable.background_mist);
                ivTempIcon.setImageResource(R.drawable.icon_metro_weather3);

        }
    }
}

