package com.zoka.moviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DetailsFragment dFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Intent.EXTRA_TEXT, getIntent().getParcelableExtra(Intent.EXTRA_TEXT));
        dFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_details, dFragment).commit();
    }
}
