package com.toters.marvelapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.toters.marvelapp.BuildConfig;
import com.toters.marvelapp.R;
import com.toters.marvelapp.databinding.ActivityLauncherBinding;
import com.toters.marvelapp.helpers.Constant;
import com.toters.marvelapp.helpers.Helpers;
import com.toters.marvelapp.networkRequest.SendRequest;

import org.json.JSONException;

public class LauncherActivity extends AppCompatActivity implements SendRequest.OnRequestComplete {

    private static final String TAG = "LauncherActivity";
    private ActivityLauncherBinding binding;
    private boolean canGo = true;
    private int countTime=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLauncherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initVariables();
        launchApp();
    }

    private void initVariables() {
        binding.version.setText(String.format("version %s", BuildConfig.VERSION_NAME));
    }

    private void launchApp() {
        binding.image1.animate().translationX(-20).setDuration(2000).start();

        binding.image1.animate().alpha(1).setDuration(800).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                binding.image1.animate().alpha(0).setDuration(800).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        binding.lottie.playAnimation();
                        binding.lottie.animate().alpha(0.8f).setDuration(800).start();
                        if (canGo) {
                            canGo = false;
                            new Handler().postDelayed(LauncherActivity.this::getCharacters, 1000);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
    }

    private void getCharacters() {
        SendRequest sendRequest = new SendRequest(this,0);
        sendRequest.execute("characters?");
    }

    @Override
    public void onRequestComplete(int status, String response,int requestCode) {
        if (status == 1) {
            try {
                Helpers.parseCharacters(response);
                gotoMainActivity();
            } catch (JSONException e) {
                Toast.makeText(this, "There is a problem concerning our server, please try again later", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
        }
    }

    private void gotoMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}