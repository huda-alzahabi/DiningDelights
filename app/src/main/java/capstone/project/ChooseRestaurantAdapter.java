package capstone.project;

import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChooseRestaurantAdapter extends RecyclerView.Adapter<ChooseRestaurantAdapter.ViewHolder> {
    public List<String> titles;
    public List<Integer> images;
    LayoutInflater inflater;

    public ChooseRestaurantAdapter(Context ctx, List<String> titles, List<Integer> images) {
        this.titles = titles;
        this.images = images;
        this.inflater = LayoutInflater.from(ctx);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.main_menu_gridlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(titles.get(position));
        holder.gridIcon.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView gridIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView2);
            gridIcon = itemView.findViewById(R.id.imageView2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    getLayoutPosition();
                    if (getLayoutPosition() == 0) {
                        Intent intent = new Intent(v.getContext(), MainMenuActivity.class);
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }
    }

    public List<String> getTitles() {
        return titles;
    }

    public List<Integer> getImages() {
        return images;
    }
}
