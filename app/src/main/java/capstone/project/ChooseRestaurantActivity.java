package capstone.project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChooseRestaurantActivity extends AppCompatActivity {

    RecyclerView dataList;
    List<String> titles;
    List<Integer> images;
    ChooseRestaurantAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_restaurant);
        dataList = findViewById(R.id.dataList2);
        titles = new ArrayList<>();
        images = new ArrayList<>();

        titles.add("La Villa");
        titles.add("Deek Duke");
        titles.add("Pizza Hut");
        titles.add("Chopsticks");
        titles.add("Socrate");
        titles.add("Roadster Diner");
        titles.add("Domino's Pizza");
        titles.add("Crepaway");


        images.add(R.drawable.lavilla);
        images.add(R.drawable.deekduke);
        images.add(R.drawable.pizzahut);
        images.add(R.drawable.chopsticks);
        images.add(R.drawable.socrate);
        images.add(R.drawable.rd);
        images.add(R.drawable.dominoes);
        images.add(R.drawable.crepaway);

        adapter = new ChooseRestaurantAdapter(this, titles, images);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);

    }


}
