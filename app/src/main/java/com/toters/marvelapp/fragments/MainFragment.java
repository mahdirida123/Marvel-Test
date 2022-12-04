package com.toters.marvelapp.fragments;

import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.toters.marvelapp.R;
import com.toters.marvelapp.adapters.HorizontalAdapter;
import com.toters.marvelapp.adapters.VerticalAdapter;
import com.toters.marvelapp.databinding.FragmentMainBinding;
import com.toters.marvelapp.helpers.Constant;
import com.toters.marvelapp.helpers.DetailsTransition;
import com.toters.marvelapp.helpers.Helpers;
import com.toters.marvelapp.models.Characters;

public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    private FragmentMainBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(getLayoutInflater());

        initHorizontalRecyclerView();
        handleScreenOrientation();

        return binding.getRoot();
    }

    private void handleScreenOrientation() {
        if (binding.verticalRecyclerView != null) {
            initVerticalRecyclerView();
        } else if (Constant.lastCharacter != null) {
            openDetail(Constant.lastCharacter, null);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);
    }

    private void initVerticalRecyclerView() {
        VerticalAdapter verticalAdapter = new VerticalAdapter(this, Constant.charactersList);
        assert binding.verticalRecyclerView != null;
        binding.verticalRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.verticalRecyclerView.setAdapter(verticalAdapter);
    }

    private void initHorizontalRecyclerView() {
        HorizontalAdapter horizontalAdapter = new HorizontalAdapter(this, Constant.charactersList);
        LinearLayoutManager manager = new LinearLayoutManager(requireActivity());
        if (getResources().getBoolean(R.bool.isLandscape)) {
            manager.setOrientation(LinearLayoutManager.VERTICAL);
        } else {
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        }
        binding.horizontalRecyclerView.setLayoutManager(manager);
        binding.horizontalRecyclerView.setAdapter(horizontalAdapter);
    }

    public void openDetail(Characters characters, ImageView imageView) {

        Constant.lastCharacter = characters;
        Bundle bundle = new Bundle();
        bundle.putSerializable(Characters.class.getSimpleName(), characters);

        DetailsFragment detailsFragment = new DetailsFragment();

        detailsFragment.setSharedElementEnterTransition(new DetailsTransition());
        detailsFragment.setEnterTransition(new Fade());
        setExitTransition(new Fade());

        if (Helpers.isLandscape(requireActivity())) {
            assert binding.noCharacterText != null;
            binding.noCharacterText.setVisibility(View.GONE);
            detailsFragment.setArguments(bundle);
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

            if (imageView != null) {
                fragmentManager
                        .beginTransaction()
                        .addSharedElement(imageView, imageView.getTransitionName())
                        .replace(R.id.landFrameLayout, detailsFragment, DetailsFragment.class.getSimpleName())
                        .addToBackStack(null)
                        .commit();
            } else {
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.landFrameLayout, detailsFragment, DetailsFragment.class.getSimpleName())
                        .addToBackStack(null)
                        .commit();
            }
        } else {
            detailsFragment.setSharedElementReturnTransition(new DetailsTransition());
            bundle.putString("TRANS_NAME", imageView.getTransitionName());
            detailsFragment.setArguments(bundle);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .addSharedElement(imageView, imageView.getTransitionName())
                    .replace(R.id.frameLayout, detailsFragment, DetailsFragment.class.getSimpleName())
                    .addToBackStack(null)
                    .commit();
        }
    }
}