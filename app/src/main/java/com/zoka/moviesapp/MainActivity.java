package com.zoka.moviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.zoka.moviesapp.models.MoviesModel;

public class MainActivity extends AppCompatActivity {
    private static Boolean mTowPan;
    FrameLayout f_Panel_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        f_Panel_2 = (FrameLayout) findViewById(R.id.panel_two_id);
        if (savedInstanceState == null) {


            getSupportFragmentManager().beginTransaction().replace(R.id.panel_one_id, new MoviesFragment()).commit();
        }
        if (null == f_Panel_2) {
            mTowPan = false;

        } else {
            mTowPan = true;
        }
        MoviesFragment.setMoviesListener(new MoviesListener() {
            @Override
            public void setMovies(MoviesModel movies) {
                if (mTowPan) {//Case Tow pan Ui
                    DetailsFragment dFragment = new DetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Intent.EXTRA_TEXT, movies);
                    dFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.panel_two_id, dFragment).commit();


                } else { //Case One  pan Ui

                    Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Intent.EXTRA_TEXT, movies);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }

            }
        });

    }

}