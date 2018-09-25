package activitytest.example.com.activitytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class BaseActivity extends AppCompatActivity{
    @Override
        protected void onCreate(Bundle savedInstenceState) {
        super.onCreate(savedInstenceState);
        Log.d("BaseActivity",getClass().getCanonicalName());
    }

}
