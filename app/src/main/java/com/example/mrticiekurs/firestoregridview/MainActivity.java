package com.example.mrticiekurs.firestoregridview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Query query = FirebaseFirestore.getInstance().collection("images").limit(10);
        FirestoreRecyclerOptions<Images> images = new FirestoreRecyclerOptions.Builder<Images>()
                .setQuery(query, Images.class).build();





    }
}
