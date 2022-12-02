package com.toters.marvelapp.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.toters.marvelapp.R;
import com.toters.marvelapp.databinding.FragmentDetailsBinding;
import com.toters.marvelapp.models.Characters;
import com.toters.marvelapp.networkRequest.SendRequest;

public class DetailsFragment extends Fragment {

    private FragmentDetailsBinding binding;
    private Characters characters;
    private SendRequest sendRequest;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(getLayoutInflater());
        initVariables();
        getExtras();
        fillContent();
        initClickListeners();
        return binding.getRoot();
    }

    private void initClickListeners() {
        binding.back.setOnClickListener(v -> requireActivity().onBackPressed());
    }

    private void getExtras() {
        characters = (Characters) getArguments().getSerializable(Characters.class.getSimpleName());
    }

    private void initVariables() {
        String imageTransitionName = getArguments().getString("TRANS_NAME");
        binding.detailsImage.setTransitionName(imageTransitionName);
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
}