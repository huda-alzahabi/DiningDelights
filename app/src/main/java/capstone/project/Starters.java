package capstone.project;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Starters {
    private String name;
    private int price;
    private Bitmap imageResourceID;
    public static ArrayList<Starters> menu = new ArrayList<Starters>();

    public Starters(String name, int price, Bitmap imageResourceID) {
        this.name = name;
        this.price = price;
        this.imageResourceID = imageResourceID;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Bitmap getImageResourceID() {
        return imageResourceID;
    }
}
