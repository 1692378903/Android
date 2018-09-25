package activitytest.example.com.listviewtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

    private List<Fruit> fruitList = new ArrayList<>();

    private String[] data = {
            "Apple","Banana","Orange","Watermalon","Pear","Grape","Pineapple","Strawberry","Cherry","Mango",
            "Apple","Banana","Orange","Watermalon","Pear","Grape","Pineapple","Strawberry","Cherry","Mango"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatper3_list_view);
        initFruits();
        FruitAdapter adapter = new FruitAdapter(ListViewActivity.this,R.layout.chatper3_fruit_item, fruitList);
        ListView listView = (ListView)findViewById(R.id.list_view1);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fruit fruit = fruitList.get(position);
                Toast.makeText(ListViewActivity.this,fruit.getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initFruits(){
        for(int i = 0; i < 2 ; i++){
            Fruit Apple = new Fruit("Apple",R.drawable.fruit);
            fruitList.add(Apple);
            Fruit Banana = new Fruit("Banana",R.drawable.fruit);
            fruitList.add(Banana);
            Fruit Orange = new Fruit("Orange",R.drawable.fruit);
            fruitList.add(Orange);
            Fruit Watermalon = new Fruit("Watermalon",R.drawable.fruit);
            fruitList.add(Watermalon);
            Fruit Pear = new Fruit("Pear",R.drawable.fruit);
            fruitList.add(Pear);
            Fruit Grape = new Fruit("Grape",R.drawable.fruit);
            fruitList.add(Grape);
            Fruit Pineapple = new Fruit("Pineapple",R.drawable.fruit);
            fruitList.add(Pineapple);
            Fruit Strawberry = new Fruit("Strawberry",R.drawable.fruit);
            fruitList.add(Strawberry);
            Fruit Cherry = new Fruit("Cherry",R.drawable.fruit);
            fruitList.add(Cherry);
            Fruit Mango = new Fruit("Mango",R.drawable.fruit);
            fruitList.add(Mango);

        }
    }

    public class Fruit{
        private String name;
        private int imageId;
        public Fruit(String name, int imageId){
            this.name = name;
            this.imageId = imageId;
        }
        public String getName() {
            return name;
        }
        public int getImageId() {
            return imageId;
        }
    }


}
