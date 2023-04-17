package edu.sjsu.android.habiteamproject;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarFragment extends Fragment {

    public TextView date;

    public CalendarView calendarView;

    public EditText dateTitle;

    public String dateFormat;

    public CalendarFragment() {
        // Required empty public constructor
        dateFormat = "";
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
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        date = view.findViewById(R.id.get_date);
        dateTitle = view.findViewById(R.id.enter_title);
        calendarView = view.findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView v, int year, int month,
                                            int dayOfMonth) {
                String m="";
                int mon = month+1;
                if (mon < 10) {
                    m = "0" + String.valueOf(mon);
                }
                else{
                    m = String.valueOf(mon);
                }
                dateFormat = m + "/" + String.valueOf(dayOfMonth) + "/" + String.valueOf(year);
                view.findViewById(R.id.add_event).setOnClickListener(val -> add(dateFormat));
            }

        });

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String selectedDate = sdf.format(new Date(calendarView.getDate()));
        view.findViewById(R.id.add_event).setOnClickListener(val -> add(selectedDate));
        view.findViewById(R.id.get_event).setOnClickListener(this::getAllDates);

        return view;
    }

    public void add(String v) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("username", getUsername());
            contentValues.put("date", v);
            contentValues.put("title", dateTitle.getText().toString());

            getActivity().getContentResolver().insert(HabiProvider.CONTENT_URI_CALENDAR, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
        }
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


    public void getAllDates(View view) {
        // Sort by student name
        try (Cursor c = getActivity().getContentResolver().query(HabiProvider.CONTENT_URI_CALENDAR, null, getUsername(), null, null)) {
            if (c.moveToFirst()) {
                String result = "All Dates: \n";
                do {
                    for (int i = 1; i < c.getColumnCount(); i++) {
                        result = result.concat(c.getString(i) + "      ");
                    }
                    result = result.concat("\n");
                } while (c.moveToNext());
                date.setText(result);
            }
        }
    }
}