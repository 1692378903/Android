package activitytest.example.com.coolweather.gson;

import com.google.gson.annotations.SerializedName;

public class Forecast {
    public String date;
    @SerializedName("tmp")
    public Temperature temperature;
    @SerializedName("cond")
    public More more;

    public class Temperature{
        public String max;//最高温度
        public String min;//最低气温
    }

    public class More {
        @SerializedName("txt_d")//天气信息
        public String info;
    }
}
