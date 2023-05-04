package edu.sjsu.android.habiteamproject;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bar);
        getSupportFragmentManager().beginTransaction().replace(R.id.current_fragment, new HomeFragment()).addToBackStack("first").commit();
        bottomNavigationView.setOnItemSelectedListener(this::handleBottomNav);
    }

    private boolean handleBottomNav(MenuItem item){
        Fragment currentFragment = null;
        int currentItem = item.getItemId();

        if (currentItem == R.id.home) {
            currentFragment = new HomeFragment();
        } else if (currentItem == R.id.calendar) {
            currentFragment = new CalendarFragment();
        } else if (currentItem == R.id.notifications) {
            currentFragment = new NotificationFragment();
        } else if (currentItem == R.id.settings) {
            currentFragment = new SettingFragment();
        }

        if (currentFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.current_fragment, currentFragment).addToBackStack("next").commit();
        }
        return true;
    }
}