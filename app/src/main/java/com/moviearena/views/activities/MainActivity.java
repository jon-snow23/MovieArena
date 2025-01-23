package com.moviearena.views.activities;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.moviearena.R;
import com.moviearena.databinding.ActivityMainBinding;
import com.moviearena.views.Home.HomeFragment;
import com.moviearena.views.MyHome.MyHomeFragment;
import com.moviearena.views.Search.SearchFragment;
import com.moviearena.views.WatchList.WatchListFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
        getSupportFragmentManager().beginTransaction().replace(binding.frame.getId() , new HomeFragment()).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onBackPressed() {

        if (WatchListFragment.listener != null){
            WatchListFragment.listener.onBackPressed();
        }
        else if (SearchFragment.listener != null){
            SearchFragment.listener.onBackPressed();
        }
        else if (MyHomeFragment.listener != null){
            new AlertDialog.Builder(this)
                    .setMessage(R.string.exit)
                    .setCancelable(true)
                    .setPositiveButton(R.string.yes, (dialogInterface, i) -> finish())
                    .setNegativeButton(R.string.no, null)
                    .show();
        }
        else{
            super.onBackPressed();

        }
    }
}