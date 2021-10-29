package com.example.createtaskfragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.createtaskfragment.databinding.ActivityMainBinding;
import com.example.createtaskfragment.utils.Constants;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    NavController navController;
    AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_ru) {
            showChangeLanguageDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showChangeLanguageDialog() {
        final String[] listItems = {"English", "Russia"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Выбрать язык");
        builder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    setLocale("en");
                    recreate();
                } else if (which == 1) {
                    setLocale("ru");
                    recreate();
                }
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences(Constants.SETTINGS, MODE_PRIVATE).edit();
        editor.putString(Constants.MY_LANG, lang);
        editor.apply();
    }

    public void loadLocale() {
        SharedPreferences preferences = getSharedPreferences(Constants.SETTINGS, Activity.MODE_PRIVATE);
        String language = preferences.getString(Constants.MY_LANG, "");
        setLocale(language);
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