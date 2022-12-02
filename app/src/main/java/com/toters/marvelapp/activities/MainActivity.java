package com.toters.marvelapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.toters.marvelapp.R;
import com.toters.marvelapp.databinding.ActivityMainBinding;
import com.toters.marvelapp.fragments.DetailsFragment;
import com.toters.marvelapp.fragments.MainFragment;
import com.toters.marvelapp.helpers.Helpers;
import com.toters.marvelapp.networkRequest.SendRequest;

public class MainActivity extends AppCompatActivity implements SendRequest.OnRequestComplete {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        if (Helpers.isLandscape(this)) {
//            binding.back.setVisibility(View.GONE);
//        }
        openMainFragment();
        initVariables();
    }

    private void openMainFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.frameLayout, new MainFragment(),MainFragment.class.getSimpleName())
                .addToBackStack(null)
                .commit();
    }

    private void initVariables() {

    }

    @Override
    public void onRequestComplete(int status, String response) {

    }
}