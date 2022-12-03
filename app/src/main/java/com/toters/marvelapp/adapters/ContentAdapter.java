package com.toters.marvelapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.toters.marvelapp.R;
import com.toters.marvelapp.models.Comics;
import com.toters.marvelapp.models.Events;
import com.toters.marvelapp.models.Series;
import com.toters.marvelapp.models.Stories;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int EVENTS = 1;
    private final int STORIES = 2;
    private final int SERIES = 3;
    private static final String TAG = "ContentAdapter";
    private List<Object> objectList;
    private Context context;

    public ContentAdapter(List<Object> objectList, Context context) {
        this.objectList = objectList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (objectList.get(position) instanceof Events) {
            return EVENTS;
        }
        if (objectList.get(position) instanceof Series) {
            return SERIES;
        }
        if (objectList.get(position) instanceof Stories) {
            return STORIES;
        }
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_comics, parent, false);
        switch (viewType) {
            case EVENTS:
                view = LayoutInflater.from(context).inflate(R.layout.recycler_comics, parent, false);
                return new EventsViewHolder(view);

            case STORIES:
                view = LayoutInflater.from(context).inflate(R.layout.recycler_stories, parent, false);
                return new StoriesViewHolder(view);

            case SERIES:
                view = LayoutInflater.from(context).inflate(R.layout.recycler_series, parent, false);
                return new SeriesViewHolder(view);
        }

        return new ComicsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        if (holder1 instanceof ComicsViewHolder) {
            ComicsViewHolder holder = (ComicsViewHolder) holder1;
            Comics comics = (Comics) objectList.get(position);
            Glide.with(context)
                    .load(comics.getPicPath())
                    .placeholder(R.drawable.image_placeholder)
                    .into(holder.image);
            holder.title.setText(comics.getTitle());
            Log.d(TAG, "onBindViewHolder: image " + comics.getPicPath());
            holder.description.setText(comics.getDescription());

        } else if (holder1 instanceof StoriesViewHolder) {
            StoriesViewHolder holder = (StoriesViewHolder) holder1;
            Stories stories = (Stories) objectList.get(position);
            Log.d(TAG, "onBindViewHolder: story "+stories);
            holder.title.setText(stories.getTitle());
            if(stories.getType().length()>0){
                holder.cover.setText(String.format("(%s)", stories.getType()));
            }else{
                holder.cover.setText("");
            }
            holder.description.setText(stories.getDescription());

        } else if (holder1 instanceof EventsViewHolder) {
            EventsViewHolder holder = (EventsViewHolder) holder1;
            Events events = (Events) objectList.get(position);
            Glide.with(context)
                    .load(events.getPicPath())
                    .placeholder(R.drawable.image_placeholder)
                    .into(holder.image);
            holder.title.setText(events.getTitle());
            holder.description.setText(events.getDescription());

        } else if (holder1 instanceof SeriesViewHolder) {
            SeriesViewHolder holder = (SeriesViewHolder) holder1;
            Series series = (Series) objectList.get(position);
            Glide.with(context)
                    .load(series.getPicPath())
                    .placeholder(R.drawable.image_placeholder)
                    .into(holder.image);
            holder.title.setText(series.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    static class ComicsViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView title, description;

        public ComicsViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.recycler_comics_image);
            title = itemView.findViewById(R.id.recycler_comics_title);
            description = itemView.findViewById(R.id.recycler_comics_description);
        }
    }

    static class StoriesViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, cover;

        public StoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.recycler_stories_title);
            description = itemView.findViewById(R.id.recycler_stories_description);
            cover = itemView.findViewById(R.id.recycler_stories_type);
        }
    }

    static class EventsViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView title, description;

        public EventsViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.recycler_comics_image);
            title = itemView.findViewById(R.id.recycler_comics_title);
            description = itemView.findViewById(R.id.recycler_comics_description);
        }
    }

    static class SeriesViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView title;

        public SeriesViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.recycler_series_image);
            title = itemView.findViewById(R.id.recycler_series_title);
        }
    }
}
