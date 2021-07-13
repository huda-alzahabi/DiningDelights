package capstone.project;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Drinks {
    private String name;
    private int price;
    private Bitmap imageResourceID;
    public static ArrayList<Drinks> menu = new ArrayList<Drinks>();

    public Drinks(String name, int price, Bitmap imageResourceID) {
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
