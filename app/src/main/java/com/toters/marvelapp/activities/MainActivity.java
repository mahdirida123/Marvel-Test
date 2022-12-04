package com.toters.marvelapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.toters.marvelapp.R;
import com.toters.marvelapp.databinding.ActivityMainBinding;
import com.toters.marvelapp.fragments.DetailsFragment;
import com.toters.marvelapp.fragments.MainFragment;
import com.toters.marvelapp.helpers.Constant;
import com.toters.marvelapp.helpers.Helpers;
import com.toters.marvelapp.models.Characters;
import com.toters.marvelapp.networkRequest.SendRequest;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    public Characters lastCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkFragments();
        openMainFragment();

    }

    private void checkFragments() {
        Fragment details = getSupportFragmentManager().findFragmentByTag(DetailsFragment.class.getSimpleName());
        if(getResources().getBoolean(R.bool.isLandscape) && details!=null){
            getSupportFragmentManager().beginTransaction().remove(details).commit();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(Characters.class.getSimpleName(), lastCharacter);
        super.onSaveInstanceState(outState);
    }

    private void openMainFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.frameLayout, new MainFragment(), MainFragment.class.getSimpleName())
                .addToBackStack(null)
                .commit();
    }
}