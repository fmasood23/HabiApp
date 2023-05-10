package edu.sjsu.android.habiteamproject;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class WaterTracker extends Fragment {

    public TextView total;

    public WaterTracker() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_water_tracker, container, false);

        total = view.findViewById(R.id.water_total);
        String set = "You drank " + getWaterTotal() + " glasses of water today.";
        total.setText(set);

        view.findViewById(R.id.submit_water).setOnClickListener(this::add);

        return view;
    }

    public void add(View v) {
        try {
            String currentTime = Calendar.getInstance().getTime().toString();
            String[] current = currentTime.split(" ");
            String newCurrent = current[0] + " " + current[1] + " " + current[2];
            ContentValues contentValues = new ContentValues();
            contentValues.put("username", getUsername());
            contentValues.put("date", newCurrent);
            contentValues.put("count", 1);

            requireActivity().getContentResolver().insert(HabiProvider.CONTENT_URI_WATER, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Could not add glass of water", Toast.LENGTH_SHORT).show();
        }
        String set = "You drank " + getWaterTotal() + " glasses of water today.";
        total.setText(set);
    }

    public String getUsername() {
        UserManager currentUser = new UserManager();
        return currentUser.getUsername(getActivity());
    }

    public int getWaterTotal() {
        try (Cursor c = requireActivity().getContentResolver().
                query(HabiProvider.CONTENT_URI_WATER, null, getUsername(), null, null)) {
            if (c.moveToFirst()) {
                int result = 0;
                do {
                    result++;
                } while (c.moveToNext());
                return result;
            } else {
                return 0;
            }
        }
    }
}