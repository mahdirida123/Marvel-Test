package com.toters.marvelapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.toters.marvelapp.R;
import com.toters.marvelapp.activities.MainActivity;
import com.toters.marvelapp.fragments.MainFragment;
import com.toters.marvelapp.models.Characters;

import java.util.ArrayList;
import java.util.List;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.ViewHolder> {

    private static final String TAG = "HorizontalAdapter";
    private List<Characters> charactersList;
    private MainFragment mainFragment;

    public HorizontalAdapter(MainFragment mainFragment, List<Characters> charactersList) {
        this.charactersList = filterList(charactersList);
        this.mainFragment = mainFragment;
    }

    private List<Characters> filterList(List<Characters> charactersList) {
        List<Characters> filteredList = new ArrayList<>();
        for (int i = 0; i < charactersList.size(); i++) {
            if (i % 2 != 0) {
                filteredList.add(charactersList.get(i));
            }
        }
        return filteredList;
    }

    @NonNull
    @Override
    public HorizontalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_characters_horizontal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalAdapter.ViewHolder holder, int position) {

        Characters characters = charactersList.get(position);

        holder.title.setText(characters.getTitle());
        holder.rate.setText(String.valueOf((float) (Math.random() * 5)).substring(0,3));

        Glide.with(mainFragment.requireActivity())
                .load(characters.getPicPath())
                .transition(DrawableTransitionOptions.withCrossFade()) //Here a fading animation
                .dontTransform()
                .placeholder(R.drawable.image_placeholder)
                .into(holder.image);

        holder.image.setTransitionName("trans"+position);

        holder.mainLayout.setOnClickListener(v -> mainFragment.openDetail(characters,holder.image));
    }

    @Override
    public int getItemCount() {
        return charactersList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title, rate;
        CardView mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.recycler_horizontal_image);
            title = itemView.findViewById(R.id.recycler_horizontal_title);
            mainLayout = itemView.findViewById(R.id.recycler_horizontal_mainLayout);
            rate = itemView.findViewById(R.id.recycler_horizontal_rate);
        }
    }
}
