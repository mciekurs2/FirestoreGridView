package com.example.mrticiekurs.firestoregridview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class FullscreenActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        Bundle extras = getIntent().getExtras();
        String url = extras.getString("url");
        imageView = findViewById(R.id.fullImageView);

        Glide.with(getApplicationContext()).load(url).apply(RequestOptions.centerCropTransform()).into(imageView);


    }
}
