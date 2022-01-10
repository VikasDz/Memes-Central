package com.t.memesworld;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.jsibbold.zoomage.ZoomageView;

public class FullImageView extends AppCompatActivity {

    private ZoomageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image_view);

        imageView = findViewById(R.id.imageView);

        String image = getIntent().getStringExtra("image");

        Glide.with(this).load(image).into(imageView);

    }
}