package activitytest.example.com.uiwidgettest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class NormalActivity extends AppCompatActivity {

    private EditText editText;
    private ImageView imageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_layout);
        Button button = (Button)findViewById(R.id.button_33);
        final EditText editText = (EditText)findViewById(R.id.edit_text_33);
        final ImageView imageView = (ImageView)findViewById(R.id.image_view_33);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar_33);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int progress = progressBar.getProgress();
                progress = progress + 20;
                progressBar.setProgress(progress);
                //imageView.setImageResource(R.drawable.img_2);//点击切换图片
                //Toast.makeText(NormalActivity.this,inputext,Toast.LENGTH_SHORT).show();
            }
        });

    }
}
