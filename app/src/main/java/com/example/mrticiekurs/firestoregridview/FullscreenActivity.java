package com.example.mrticiekurs.firestoregridview;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

public class FullscreenActivity extends AppCompatActivity {

    private ImageView imageView;
    private ProgressBar progressBar;
    private ScrollView scrollView;
    private FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        Bundle extras = getIntent().getExtras();
        String url = extras.getString("url");
        imageView = findViewById(R.id.fullImageView);
        progressBar = findViewById(R.id.progFull);
        scrollView = (ScrollView) findViewById(R.id.scrollViewMain);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ScrollPositionObserver());



        Glide.with(getApplicationContext()).load(url).
                transition(DrawableTransitionOptions.withCrossFade()).
                apply(RequestOptions.centerCropTransform().
                        diskCacheStrategy(DiskCacheStrategy.DATA)).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(imageView);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    private class ScrollPositionObserver implements ViewTreeObserver.OnScrollChangedListener{

        private int imageViewHeight;

        public ScrollPositionObserver(){
            imageViewHeight = getResources().getDimensionPixelSize(R.dimen.layout_dim);
        }

        @Override
        public void onScrollChanged() {
            int scrollY = Math.min(Math.max(scrollView.getScrollY(), 0), imageViewHeight);
            imageView.setTranslationY(scrollY / 2);
            float alpha = scrollY / (float) imageViewHeight;

        }
    }



}
