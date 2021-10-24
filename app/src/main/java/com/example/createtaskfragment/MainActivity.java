package com.example.createtaskfragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.createtaskfragment.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    NavController navController;
    private ActivityMainBinding binding;
    AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment_content_main);

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.createTaskFragment);
            }
        });
         mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home_main, R.id.nav_gallery, R.id.nav_slideshow)
                .build();
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.btnNavigation, navController);

        hideButon();
    }

    private void hideButon() {
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.createTaskFragment) {
                binding.appBarMain.toolbar.setVisibility(View.GONE);
                binding.appBarMain.fab.setVisibility(View.GONE);
            } else {
                binding.appBarMain.toolbar.setVisibility(View.VISIBLE);
                binding.appBarMain.fab.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}