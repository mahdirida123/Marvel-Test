package com.toters.marvelapp.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.toters.marvelapp.R;
import com.toters.marvelapp.activities.GalleryActivity;
import com.toters.marvelapp.adapters.DetailsAdapter;
import com.toters.marvelapp.databinding.FragmentDetailsBinding;
import com.toters.marvelapp.helpers.Constant;
import com.toters.marvelapp.helpers.Helpers;
import com.toters.marvelapp.models.Characters;
import com.toters.marvelapp.models.Content;
import com.toters.marvelapp.networkRequest.SendRequest;

import java.util.ArrayList;
import java.util.List;

public class DetailsFragment extends Fragment implements SendRequest.OnRequestComplete {

    private static final String TAG = "DetailsFragment";
    private FragmentDetailsBinding binding;
    private Characters characters;
    private List<Content> objectList;
    private SendRequest sendRequest;
    private final int REQUEST_COMICS = 0;
    private final int REQUEST_EVENTS = 1;
    private final int REQUEST_STORIES = 2;
    private final int REQUEST_SERIES = 3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: start");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: start");
        binding = FragmentDetailsBinding.inflate(getLayoutInflater());

        initVariables();
        getExtras();
        fillContent();
        initClickListeners();
        getData(REQUEST_COMICS, Constant.COMICS);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: start");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (sendRequest.getStatus() == AsyncTask.Status.RUNNING) {
            sendRequest.cancel(true);
        }
    }

    private void initClickListeners() {
        binding.back.setOnClickListener(v -> {
            Constant.lastCharacter = null;
            requireActivity().onBackPressed();
        });
        binding.detailsImage.setOnClickListener(v -> openGalleryActivity());
    }

    private void openGalleryActivity() {
        Intent intent = new Intent(requireActivity(), GalleryActivity.class);
        String image = characters.getPicPath();
        String title = characters.getTitle();
        intent.putExtra("characterName", title);
        intent.putExtra("imageUrl", image);
        startActivity(intent);
    }

    private void getExtras() {
        characters = (Characters) getArguments().getSerializable(Characters.class.getSimpleName());
    }

    private void initVariables() {
        objectList = new ArrayList<>();
        String imageTransitionName = getArguments().getString("TRANS_NAME");
        binding.detailsImage.setTransitionName(imageTransitionName);

        if (Helpers.isLandscape(requireActivity())) {
            binding.back.setVisibility(View.GONE);
        }
    }

    private void fillContent() {
        Glide.with(requireActivity())
                .load(characters.getPicPath())
                .transition(DrawableTransitionOptions.withCrossFade()) //Here a fading animation
                .dontTransform()
                .placeholder(R.drawable.image_placeholder)
                .into(binding.detailsImage);

        binding.title.setText(characters.getTitle());
        binding.description.setText(characters.getDescription());
    }

    private void getData(int requestCode, String type) {
        sendRequest = new SendRequest(this, requestCode);
        sendRequest.execute(Constant.CHARACTERS + "/" + characters.getId() + "/" + type + "?limit=3&");
    }

    @Override
    public void onRequestComplete(int status, String response, int requestCode) {
        switch (status) {
            case Constant.STATUS_OK:

                switch (requestCode) {
                    case REQUEST_COMICS:
                        Log.d(TAG, "onRequestComplete: comics done");
                        parseComics(response);
                        getData(REQUEST_EVENTS, Constant.EVENTS);
                        break;
                    case REQUEST_EVENTS:
                        Log.d(TAG, "onRequestComplete: events done");
                        parseEvents(response);
                        getData(REQUEST_SERIES, Constant.SERIES);
                        break;
                    case REQUEST_SERIES:
                        Log.d(TAG, "onRequestComplete: series done");
                        parseSeries(response);
                        getData(REQUEST_STORIES, Constant.STORIES);
                        break;
                    case REQUEST_STORIES:
                        Log.d(TAG, "onRequestComplete: stories done");
                        Log.d(TAG, "onRequestComplete: -----------------------------");
                        parseStories(response);
                        initRecyclerView();
                        break;
                }
                break;

            case Constant.STATUS_FAILED:
                binding.progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(requireActivity(), "Couldn't connect to server", Toast.LENGTH_SHORT).show();
        }
    }

    private void parseComics(String response) {
        objectList.add(Helpers.getComics(response));
    }

    private void parseEvents(String response) {
        objectList.add(Helpers.getEvents(response));
    }

    private void parseStories(String response) {
        objectList.add(Helpers.getStories(response));
    }

    private void parseSeries(String response) {
        objectList.add(Helpers.getSeries(response));
    }

    private void initRecyclerView() {
        binding.progressBar.setVisibility(View.INVISIBLE);
        objectList = Helpers.filterContentList(objectList);
        characters.setContentList(objectList);
        Log.d(TAG, "initRecyclerView: content list " + objectList);
        DetailsAdapter adapter = new DetailsAdapter(objectList, requireActivity());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.recyclerView.setAdapter(adapter);
    }
}