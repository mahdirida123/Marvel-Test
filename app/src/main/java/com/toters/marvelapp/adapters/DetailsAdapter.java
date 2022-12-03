package com.toters.marvelapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.toters.marvelapp.R;
import com.toters.marvelapp.models.Content;
import com.toters.marvelapp.models.Series;

import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {


    private final List<Content> contentList;
    private final Context context;

    public DetailsAdapter(List<Content> contentList, Context context) {
        this.contentList = contentList;
        this.context = context;
    }

    @NonNull
    @Override
    public DetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsAdapter.ViewHolder holder, int position) {

        Content content = contentList.get(position);
        holder.title.setText(content.getTitle());

        ContentAdapter adapter = new ContentAdapter(content.getObjectList(),context);
        if(content.getObjectList().get(0) instanceof Series){
            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.recyclerView.setLayoutManager(manager);
        }else{
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
        holder.recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.recycler_content_title);
            recyclerView = itemView.findViewById(R.id.recycler_content_recyclerView);
        }
    }


}
