package com.zoka.moviesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_details, new DetailsFragment()).commit();
    }
}
