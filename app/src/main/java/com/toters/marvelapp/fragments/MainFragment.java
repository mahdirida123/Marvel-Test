package com.toters.marvelapp.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.toters.marvelapp.R;
import com.toters.marvelapp.adapters.HorizontalAdapter;
import com.toters.marvelapp.adapters.VerticalAdapter;
import com.toters.marvelapp.databinding.FragmentMainBinding;
import com.toters.marvelapp.helpers.Constant;
import com.toters.marvelapp.helpers.DetailsTransition;
import com.toters.marvelapp.models.Characters;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;
    private HorizontalAdapter horizontalAdapter;
    private VerticalAdapter verticalAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(getLayoutInflater());

        initHorizontalRecyclerView();
        initVerticalRecyclerView();

        return binding.getRoot();
    }

    private void initVerticalRecyclerView() {
        verticalAdapter = new VerticalAdapter(this, Constant.charactersList);
        binding.verticalRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.verticalRecyclerView.setAdapter(verticalAdapter);
    }

    private void initHorizontalRecyclerView() {
        horizontalAdapter = new HorizontalAdapter(this, Constant.charactersList);
        LinearLayoutManager manager = new LinearLayoutManager(requireActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.horizontalRecyclerView.setLayoutManager(manager);
        binding.horizontalRecyclerView.setAdapter(horizontalAdapter);
    }

    public void openDetail(Characters characters, ImageView imageView) {

        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setSharedElementEnterTransition(new DetailsTransition());
        detailsFragment.setEnterTransition(new Fade());
        setExitTransition(new Fade());
        detailsFragment.setSharedElementReturnTransition(new DetailsTransition());

        Bundle bundle = new Bundle();
        bundle.putString("TRANS_NAME",imageView.getTransitionName());
        bundle.putSerializable(Characters.class.getSimpleName(),characters);
        detailsFragment.setArguments(bundle);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .addSharedElement(imageView, imageView.getTransitionName())
                .replace(R.id.frameLayout, detailsFragment,DetailsFragment.class.getSimpleName())
                .addToBackStack(null)
                .commit();
    }
}