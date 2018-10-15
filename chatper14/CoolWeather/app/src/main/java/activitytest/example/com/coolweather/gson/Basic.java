package activitytest.example.com.coolweather.gson;

import com.google.gson.annotations.SerializedName;

public class Basic {
    @SerializedName("city")//城市名
    public String cityName;
    @SerializedName("id")
    public String weatherId;//对应的天气id

    public Update update;

    public class Update{
        @SerializedName("loc")
        public String updateTime;//接口更新时间
    }
}
