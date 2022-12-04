package com.toters.marvelapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.toters.marvelapp.R;
import com.toters.marvelapp.databinding.ActivityGalleryBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.net.URL;

public class GalleryActivity extends AppCompatActivity {

    private static final String TAG = "GalleryActivity";
    private ActivityGalleryBinding binding;
    private String imageUrl;
    private String characterName;
    private Bitmap bitmap1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGalleryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getExtras();
        loadImage();
        initClickListeners();

    }

    private void initClickListeners() {
        binding.share.setOnClickListener(v -> checkPermission());
        binding.back.setOnClickListener(v -> finish());
    }

    private void loadImage() {
        binding.title.setText(characterName.trim());
        Picasso.get().load(imageUrl)
                .placeholder(R.drawable.image_placeholder)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        bitmap1 = bitmap;
                        binding.image.setImageBitmap(bitmap);
                        binding.progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        binding.progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
    }


    private void getExtras() {
        imageUrl = getIntent().getStringExtra("imageUrl");
        Log.d(TAG, "getExtras: url " + imageUrl);
        characterName = getIntent().getStringExtra("characterName");
    }

    protected void checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                + ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Storage" +
                        " permission is required .");
                builder.setTitle("Please grant this permission");
                builder.setPositiveButton("OK", (dialogInterface, i) -> ActivityCompat.requestPermissions(
                        GalleryActivity.this,
                        new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1
                ));
                builder.setNeutralButton("Cancel", (dialog, which) -> finish());
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                // Directly request for required permissions, without explanation
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        1
                );
            }
        } else {
            shareImage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                shareImage();
            }
        }
    }

    private void shareImage() {
        if (bitmap1 == null) {
            return;
        }

        String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap1, "Marvel-" + characterName, null);
        Uri bitmapUri = Uri.parse(bitmapPath);

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, "Send to"));

    }
}