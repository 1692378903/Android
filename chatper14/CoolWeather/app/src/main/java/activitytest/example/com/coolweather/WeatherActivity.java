package activitytest.example.com.coolweather;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;


import javax.security.auth.callback.Callback;

import activitytest.example.com.coolweather.gson.Forecast;
import activitytest.example.com.coolweather.gson.Weather;
import activitytest.example.com.coolweather.util.HttpUtil;
import activitytest.example.com.coolweather.util.Utility;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    private ScrollView weatherLayout;//滚动视图对象

    private TextView titleCity;//基本信息--城市名

    private TextView titleUpdateTime;//基本信息--更新时间

    //private TextView weatherInfoText;//实时天气信息--天气信息

    private TextView degreeText;//实时天气信息--温度

    private LinearLayout forecastLayout;//线性布局对象--预报天气

    private TextView aqiText;//空气质量--空气质量指数

    private TextView pm25Text;//空气质量--PM2.5指数

    private TextView comfortText;//生活建议--舒适度指数

    private TextView carWashText;//生活建议--洗车指数

    private TextView sportText;//生活建议--运动指数

    private ImageView bingPicImg;//每日一还背景图

    //手动切换城市

    public SwipeRefreshLayout swipeRefresh;
    private String mWeatherId;

    public DrawerLayout drawerLayout;
    private Button navButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //北京和上方标题栏融合在一起
        if(Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_weather);

        //初始化各控件
        //bingPicImg = (ImageView)findViewById(R.id.bing_pic_img);//背景图片
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);//滚动视图对象
        titleCity = (TextView) findViewById(R.id.title_city);//基本信息--城市
        titleUpdateTime = (TextView) findViewById(R.id.title_update_time);//基本信息--更新时间
        // weatherInfoText = (TextView) findViewById(R.id.weather_info_text);//实时天气信息--天气信息
        degreeText = (TextView) findViewById(R.id.degree_text);//实时天气信息--温度
        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);//线性布局对象--预报天气
        aqiText = (TextView) findViewById(R.id.aqi_text);//空气质量--空气质量指数
        pm25Text = (TextView) findViewById(R.id.pm25_text);//空气质量--PM2.5指数
        comfortText = (TextView) findViewById(R.id.comfort_text);//生活建议--舒适度指数
        carWashText = (TextView) findViewById(R.id.car_wash_text);//生活建议--洗车指数
        sportText = (TextView) findViewById(R.id.sport_text);//生活建议--运动指数
        bingPicImg = (ImageView) findViewById(R.id.bing_pic_img);//每日一换背景图
        //手动切换城市
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navButton = (Button)findViewById(R.id.nav_button);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather", null);
        if (weatherString != null) {
            //有缓存时直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            mWeatherId = weather.basic.weatherId;
            showWeatherInfo(weather);
        } else {
            mWeatherId = getIntent().getStringExtra("weather_id");
            //在请求数据的时候将ScrollView()隐藏
            weatherLayout.setVisibility(View.INVISIBLE);
            //从服务器请求天气数据
            requestWeather(mWeatherId);
        }
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(mWeatherId);
            }
        });
        //每日一换背景图
        String bingPic = prefs.getString("bing_pic",null);
        if(bingPic != null){
            //如果有缓存数据就直接使用Glide来加载这张图片
            Glide.with(this).load(bingPic).into(bingPicImg);
        }else{
            //如果没有缓存数据就调用loadBingPic()方法去请求今日的必应背景图
            loadBingPic();//加载每日一图
        }

        //切换城市信息按钮
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    /**
     * 根据天气id请求城市天气信息
     */
    public void requestWeather(final String weatherId) {
        //组装接口地址
        String weatherUrl = "http://guolin.tech/api/weather?cityid="+ weatherId + "&key=e39aad046e3b431c9238afd2a5876b90";
        //向组装好的地址发送请求，服务器会将相应城市的天气信息以JSON()格式返回
        HttpUtil.sendOkHttpRequest(weatherUrl, new okhttp3.Callback() {
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {

                final String responseText = response.body().string();
                //将返回的JSON数据转换成Weather对象
                final Weather weather = Utility.handleWeatherResponse(responseText);
                //将当前线程切换到主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {//请求天气成功
                            //将返回的数据缓存到SharedPreferences当中
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            mWeatherId = weather.basic.weatherId;
                            //调用showWeatherInfo()方法进行内容显示
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败1", Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败2", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
        loadBingPic();
    }

    /*
        加载每日一图
    */
    private void loadBingPic() {
            String requestBingPic = "http://guolin.tech/api/bing_pic";
            //调用HttpUtil.sendOkHttpRequest()方法获取必应背景图的链接
            HttpUtil.sendOkHttpRequest(requestBingPic, new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(okhttp3.Call call, Response response) throws IOException {

                    final String bingPic = response.body().string();
                    //将链接缓存到SharedPreferences当中
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                    editor.putString("bing_pic",bingPic);
                    editor.apply();
                    //将线程切换到主线程
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //使用Glide来加载图片
                            Glide.with(WeatherActivity.this).load(bingPic).into(bingPicImg);
                        }
                    });
                }
            });
        }




    /**
     * 处理并展示Weather实体类中的数据
     */
    private void showWeatherInfo(Weather weather) {
        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime.split(" ")[1];//更新时间
        String degree = weather.now.temperature + "℃";
       // String weatherInfo = weather.now.more.info;

        titleCity.setText(cityName);

        titleUpdateTime.setText(updateTime);

       // weatherInfoText.setText(weatherInfo);

        degreeText.setText(degree);

        forecastLayout.removeAllViews();
        for (Forecast forecast : weather.forecastList) {
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);

            dateText.setText(forecast.date);
            infoText.setText(forecast.more.info);
            maxText.setText(forecast.temperature.max);
            minText.setText(forecast.temperature.min);
            forecastLayout.addView(view);
        }

        if (weather.aqi != null) {
            aqiText.setText(weather.aqi.city.aqi);
            pm25Text.setText(weather.aqi.city.pm25);
        }

        String comfort = "舒适度:" + weather.suggestion.comfort.info;
        String carWash = "洗车指数:" + weather.suggestion.carWash.info;
        String sport = "运动建议:" + weather.suggestion.sport.info;

        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);

        //在设置完所有数据后，再将ScrollView设为可见
        weatherLayout.setVisibility(View.VISIBLE);
    }

}
