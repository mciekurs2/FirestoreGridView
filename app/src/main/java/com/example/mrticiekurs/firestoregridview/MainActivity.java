package com.example.mrticiekurs.firestoregridview;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
                Glide.with(getApplicationContext()).load(model.getUrl()).apply(RequestOptions.centerCropTransform()).into(holder.imageView);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(getApplicationContext(), model.getUrl().toString(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, FullscreenActivity.class);
                        Bundle extras = new Bundle();
                        extras.putString("url", model.getUrl());
                        intent.putExtras(extras);
                        startActivity(intent);


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

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }



}
