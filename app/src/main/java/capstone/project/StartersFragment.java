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

public class StartersFragment extends Fragment {
    SQLiteDatabase db;
    SQLiteOpenHelper sqLiteOpenHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Starters.menu.clear();
        // Inflate the layout for this fragment
        RecyclerView menuRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_starters, container, false);

        SQLiteOpenHelper sqLiteOpenHelper = new AppSQLiteOpenHelper(this.getContext());
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();

        Cursor mCursor =
                db.query("STARTERSMENU", new String[]{
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
                Starters.menu.add(new Starters(mCursor.getString(1), mCursor.getInt(2), bmp));

            } while (mCursor.moveToNext());
        }
        String[] starter_name = new String[Starters.menu.size()];
        int[] starter_price = new int[Starters.menu.size()];
        Bitmap[] starter_image = new Bitmap[Starters.menu.size()];

        for (int i = 0; i < Starters.menu.size(); i++) {
            starter_name[i] = Starters.menu.get(i).getName();
            starter_image[i] = Starters.menu.get(i).getImageResourceID();
            starter_price[i] = Starters.menu.get(i).getPrice();
        }

        ImagesAdapter imagesAdapter = new ImagesAdapter(starter_name, starter_price, starter_image);
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

