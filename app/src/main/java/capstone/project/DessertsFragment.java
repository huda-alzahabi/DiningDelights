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

public class DessertsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Desserts.menu.clear();

        // Inflate the layout for this fragment
        RecyclerView menuRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_desserts, container, false);

        SQLiteOpenHelper sqLiteOpenHelper = new AppSQLiteOpenHelper(this.getContext());
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();

        Cursor mCursor =
                db.query("DESSERTSMENU", new String[]{
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
                Desserts.menu.add(new Desserts(mCursor.getString(1), mCursor.getInt(2), bmp));

            } while (mCursor.moveToNext());
        }
        String[] dessert_name = new String[Desserts.menu.size()];
        int[] dessert_price = new int[Desserts.menu.size()];
        Bitmap[] dessert_image = new Bitmap[Desserts.menu.size()];

        for (int i = 0; i < Desserts.menu.size(); i++) {
            dessert_name[i] = Desserts.menu.get(i).getName();
            dessert_image[i] = Desserts.menu.get(i).getImageResourceID();
            dessert_price[i] = Desserts.menu.get(i).getPrice();
        }

        ImagesAdapter imagesAdapter = new ImagesAdapter(dessert_name, dessert_price, dessert_image);
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

