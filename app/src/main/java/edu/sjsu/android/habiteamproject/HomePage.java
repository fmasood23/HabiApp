package edu.sjsu.android.habiteamproject;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomePage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment home = new HomeFragment();
        Fragment calendar = new CalendarFragment();
        Fragment notif = new NotificationFragment();
        Fragment setting = new SettingFragment();

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bar);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, home).addToBackStack("first").commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    selectedFragment = home;
                } else if (itemId == R.id.calendar) {
                    selectedFragment = calendar;
                } else if (itemId == R.id.notifications) {
                    selectedFragment = notif;
                } else if (itemId == R.id.settings) {
                    selectedFragment = setting;
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).addToBackStack("next").commit();
                }
                return true;
            }
        });
    }
}