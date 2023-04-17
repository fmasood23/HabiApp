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
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_water_tracker, container, false);

        total = view.findViewById(R.id.water_total);
        total.setText("You drank " + String.valueOf(getWaterTotal()) + " glasses of water today.");

        view.findViewById(R.id.submit_water).setOnClickListener(this::add);

        return view;
    }

    public void add(View v){
        try{
            String currentTime = Calendar.getInstance().getTime().toString();
            String[] current = currentTime.split(" ");
            String newCurrent = current[0] + " " + current[1] + " " + current[2];
            ContentValues contentValues = new ContentValues();
            contentValues.put("username", getUsername());
            contentValues.put("date", newCurrent);
            contentValues.put("count", 1);

            getActivity().getContentResolver().insert(HabiProvider.CONTENT_URI_WATER, contentValues);
        }
        catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
        }
        total.setText("You drank " + String.valueOf(getWaterTotal()) + " glasses of water today.");
    }

    public String getUsername() {
        try (Cursor c = getActivity().getContentResolver().
                query(HabiProvider.CONTENT_URI_CURRENT, null, null, null, null)) {
            if (c.moveToFirst()) {
                String result = "";
                do {
                    for (int i = 0; i < c.getColumnCount(); i++) {
                        result = result.concat
                                (c.getString(i));
                    }
                } while (c.moveToNext());
                return result;
            } else {
                return null;
            }
        }
    }

    public int getWaterTotal() {
        try (Cursor c = getActivity().getContentResolver().
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