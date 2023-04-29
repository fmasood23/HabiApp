package edu.sjsu.android.habiteamproject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SleepTracker extends Fragment {

    public EditText sleepInput;
    public TextView setAvg;

    public SleepTracker() {
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
        View view = inflater.inflate(R.layout.fragment_sleep_tracker, container, false);

        sleepInput = view.findViewById(R.id.enter_sleep);
        setAvg = view.findViewById(R.id.sleep_average);

        //onclick listener for submit
        view.findViewById(R.id.submit_sleep).setOnClickListener(this::setSleep);

        return view;
    }

    public void setSleep(View view){
        String inputted_hours = sleepInput.getText().toString();

        if(inputted_hours.isEmpty()){
            Toast.makeText(getActivity(),"Invalid input",Toast.LENGTH_LONG).show();
            return;
        }

        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put("username", getUsername());
            contentValues.put("num_hours", inputted_hours);
            getActivity().getContentResolver().insert(HabiProvider.CONTENT_URI_SLEEP, contentValues);
        }
        catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
        }

        setAvg.setText("You sleep an average of " + Double.toString(getNumSleep()) + " hours a night.");

    }

    public String getUsername() {
        UserManager currentUser = new UserManager();
        return currentUser.getUsername(getActivity());
    }

    public double getNumSleep() {
        int count = 0;
        try (Cursor c = getActivity().getContentResolver().
                query(HabiProvider.CONTENT_URI_SLEEP, null, getUsername(), null, null)) {
            if (c.moveToFirst()) {
                double result = 0;
                do {
                    count++;
                    for (int i = 0; i < c.getColumnCount(); i++) {
                        result +=(c.getDouble(i));
                    }
                } while (c.moveToNext());
                return result/count;
            } else {
                return -1;
            }
        }
    }
}