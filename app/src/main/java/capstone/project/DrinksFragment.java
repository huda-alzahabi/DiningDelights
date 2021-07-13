package capstone.project;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DrinksFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Drinks.menu.clear();
        // Inflate the layout for this fragment
        RecyclerView menuRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_drinks, container, false);

        SQLiteOpenHelper sqLiteOpenHelper = new AppSQLiteOpenHelper(this.getContext());
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();

        Cursor mCursor =
                db.query("DRINKSMENU", new String[]{
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
                Drinks.menu.add(new Drinks(mCursor.getString(1), mCursor.getInt(2), bmp));

            } while (mCursor.moveToNext());
        }
        String[] drink_name = new String[Drinks.menu.size()];
        int[] drink_price = new int[Drinks.menu.size()];
        Bitmap[] drink_image = new Bitmap[Drinks.menu.size()];

        for (int i = 0; i < Drinks.menu.size(); i++) {
            drink_name[i] = Drinks.menu.get(i).getName();
            drink_image[i] = Drinks.menu.get(i).getImageResourceID();
            drink_price[i] = Drinks.menu.get(i).getPrice();
        }

        ImagesAdapter imagesAdapter = new ImagesAdapter(drink_name, drink_price, drink_image);
        menuRecycler.setAdapter(imagesAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        menuRecycler.setLayoutManager(layoutManager);

        imagesAdapter.setListener(new ImagesAdapter.Listener() {
            public void onClick(int position) {

            }
        });
        return menuRecycler;
    }
}

