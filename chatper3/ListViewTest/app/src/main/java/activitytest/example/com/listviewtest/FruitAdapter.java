package activitytest.example.com.listviewtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FruitAdapter extends ArrayAdapter<ListViewActivity.Fruit>{
    private int resouceId;

    public FruitAdapter(Context context, int textViewResourceId, List<ListViewActivity.Fruit>objecrts){
        super(context,textViewResourceId,objecrts);
        resouceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ListViewActivity.Fruit fruit = getItem(position);//获取当前的Fruit实例
        View view = LayoutInflater.from(getContext()).inflate(resouceId,parent,false);
        ImageView fruitImage = (ImageView)view.findViewById(R.id.fruit_image);
        TextView fruitName = (TextView)view.findViewById(R.id.fruit_name);
        fruitImage.setImageResource(fruit.getImageId());
        fruitName.setText(fruit.getName());
        return view;
    }
}


