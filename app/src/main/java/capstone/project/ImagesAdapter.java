package capstone.project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {
    private String name[];
    private int price[];
    private Bitmap imageId[];
    private Listener listener;
    String order;


    public static interface Listener {
        public void onClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_captioned_image, parent, false);
        return new ViewHolder(cardView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CardView cardView = holder.cardView;
        ImageView imageView = cardView.findViewById(R.id.food_image);
        TextView foodName = cardView.findViewById(R.id.food_name);
        TextView foodPrice = cardView.findViewById(R.id.food_price);

        imageView.setImageBitmap(imageId[position]);
        foodName.setText(name[position]);
        foodPrice.setText(String.valueOf(price[position]));


        cardView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(cardView.getContext(), DishActivity.class);
                String fname = foodName.getText() + "";
                int fprice = Integer.parseInt(foodPrice.getText().toString());
                Bitmap imgRes = imageId[position];

                intent.putExtra(DishActivity.ITEM_NAME, fname);
                intent.putExtra(DishActivity.ITEM_PRICE, fprice);
                intent.putExtra("BitmapImage", imgRes);

                cardView.getContext().startActivity(intent);

            }
        });


    }

    public String getOrder() {
        return order;
    }

    @Override
    public int getItemCount() {
        return name.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView cv) {
            super(cv);
            cardView = cv;
        }
    }

    public ImagesAdapter(String[] name, int[] price, Bitmap[] imageId) {
        this.name = name;
        this.price = price;
        this.imageId = imageId;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

}