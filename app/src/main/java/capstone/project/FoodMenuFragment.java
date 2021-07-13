package capstone.project;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FoodMenuFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FoodMenu.menu.clear();
        // Inflate the layout for this fragment

        RecyclerView menuRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_food_menu, container, false);

        SQLiteOpenHelper sqLiteOpenHelper = new AppSQLiteOpenHelper(this.getContext());
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();

        Cursor mCursor =
                db.query("FOODMENU", new String[]{
                                "_id",
                                "NAME",
                                "PRICE",
                                "IMGID"},
                        null, null,
                        null, null, "_id", null);

        if (mCursor.moveToFirst()) {
            do {

                byte[] image = mCursor.getBlob(3);
                Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
                FoodMenu.menu.add(new FoodMenu(mCursor.getString(1), mCursor.getInt(2), bmp));

            } while (mCursor.moveToNext());
        }
        String[] food_name = new String[FoodMenu.menu.size()];
        int[] food_price = new int[FoodMenu.menu.size()];
        Bitmap[] food_image = new Bitmap[FoodMenu.menu.size()];

        for (int i = 0; i < FoodMenu.menu.size(); i++) {
            food_name[i] = FoodMenu.menu.get(i).getName();
            food_image[i] = FoodMenu.menu.get(i).getImageResourceID();
            food_price[i] = FoodMenu.menu.get(i).getPrice();
        }


        ImagesAdapter imagesAdapter = new ImagesAdapter(food_name, food_price, food_image);
        menuRecycler.setAdapter(imagesAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        menuRecycler.setLayoutManager(layoutManager);

        imagesAdapter.setListener(new ImagesAdapter.Listener() {
            public void onClick(int position) {
                Log.d("HEYY", "HEYYY");
                Log.d("NAME", food_name[position]);
                Log.d("PRICE", String.valueOf(food_price[position]));
            }
        });

        return menuRecycler;
    }
}

