package com.toters.marvelapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.toters.marvelapp.R;
import com.toters.marvelapp.fragments.MainFragment;
import com.toters.marvelapp.models.Characters;

import java.util.ArrayList;
import java.util.List;

public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.ViewHolder> {

    private static final String TAG = "HorizontalAdapter";
    private List<Characters> charactersList;
    private MainFragment mainFragment;

    public VerticalAdapter(MainFragment mainFragment, List<Characters> charactersList) {
        this.charactersList = filterList(charactersList);
        this.mainFragment = mainFragment;

    }

    private List<Characters> filterList(List<Characters> charactersList) {
        List<Characters> filteredList = new ArrayList<>();
        for (int i = 0; i < charactersList.size(); i++) {
            if (i % 2 == 0) {
                filteredList.add(charactersList.get(i));
            }
        }
        return filteredList;
    }

    @NonNull
    @Override
    public VerticalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_characters_vertical, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerticalAdapter.ViewHolder holder, int position) {

        Characters characters = charactersList.get(position);

        holder.title.setText(characters.getTitle());
        holder.description.setText(characters.getDescription());
        holder.ratingBar.setRating(Float.parseFloat(characters.getRate()));
        holder.year.setText(characters.getYear());

        Glide.with(mainFragment.requireActivity())
                .load(characters.getPicPath())
                .transition(DrawableTransitionOptions.withCrossFade()) //Here a fading animation
                .dontTransform()
                .placeholder(R.drawable.image_placeholder)
                .into(holder.image);

        holder.image.setTransitionName("trans1" + position);

        holder.mainLayout.setOnClickListener(v -> mainFragment.openDetail(characters, holder.image));
    }

    @Override
    public int getItemCount() {
        return charactersList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title, description, year;
        CardView mainLayout;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.recycler_vertical_image);
            title = itemView.findViewById(R.id.recycler_vertical_title);
            mainLayout = itemView.findViewById(R.id.recycler_vertical_mainLayout);
            description = itemView.findViewById(R.id.recycler_vertical_description);
            ratingBar = itemView.findViewById(R.id.recycler_vertical_rate);
            year = itemView.findViewById(R.id.recycler_vertical_dateStart);
        }
    }
}
