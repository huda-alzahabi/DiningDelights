package capstone.project;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class FoodMenu {
    private String name;
    private int price;
    private Bitmap imageResourceID;
    public static ArrayList<FoodMenu> menu = new ArrayList<FoodMenu>();


    public FoodMenu(String name, int price, Bitmap imageResourceID) {
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

