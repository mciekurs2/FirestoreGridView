package com.example.mrticiekurs.firestoregridview;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private FirestoreRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        getImages();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

    }


    public void getImages(){
        Query query = FirebaseFirestore.getInstance().collection("images");
        FirestoreRecyclerOptions<Images> response = new FirestoreRecyclerOptions.Builder<Images>()
                .setQuery(query, Images.class).build();

        adapter = new FirestoreRecyclerAdapter<Images, ImageHolder>(response) {
            @Override
            protected void onBindViewHolder(@NonNull ImageHolder holder, int position, @NonNull final Images model) {

                progressBar = findViewById(R.id.progCircle);

                Glide.with(getApplicationContext()).load(model.getUrl()).transition(DrawableTransitionOptions.withCrossFade()).apply(RequestOptions.centerCropTransform().diskCacheStrategy(DiskCacheStrategy.ALL)).listener(new RequestListener<Drawable>() {
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
                }).into(holder.imageView);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN) //to Allow transition animation
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(getApplicationContext(), model.getUrl().toString(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, FullscreenActivity.class);
                        Bundle extras = new Bundle();
                        extras.putString("url", model.getUrl());
                        intent.putExtras(extras);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });

            }

            @Override
            public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_layout, parent, false);
                return new ImageHolder(view);
            }
        };

    }

    public class ImageHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imageView)
        ImageView imageView;

        public ImageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

/*    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }*/



}
