package edu.sjsu.android.habiteamproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        date = view.findViewById(R.id.get_date);
        dateTitle = view.findViewById(R.id.enter_title);
        calendarView = view.findViewById(R.id.calendarView);
        view.findViewById(R.id.reset).setOnClickListener(this::delete);

        calendarView.setOnDateChangeListener((v, year, month, dayOfMonth) -> {
            String m;
            int mon = month+1;
            if (mon < 10) {
                m = "0" + mon;
            }
            else{
                m = String.valueOf(mon);
            }
            dateFormat = m + "/" + dayOfMonth + "/" + year;
            view.findViewById(R.id.add_event).setOnClickListener(val -> add(dateFormat));
        });

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String selectedDate = sdf.format(new Date(calendarView.getDate()));
        view.findViewById(R.id.add_event).setOnClickListener(val -> add(selectedDate));
        view.findViewById(R.id.get_event).setOnClickListener(this::getAllDates);

        return view;
    }

    public void add(String dates) {
        try {
            String title = dateTitle.getText().toString().trim();
            if(title.isEmpty()){
                Toast.makeText(getActivity(), "Title field is required", Toast.LENGTH_SHORT).show();
            }
            else {
                ContentValues contentValues = new ContentValues();
                contentValues.put("username", getUsername());
                contentValues.put("date", dates);
                contentValues.put("title", title);

                requireActivity().getContentResolver().insert(HabiProvider.CONTENT_URI_CALENDAR, contentValues);
                date.setText("");
                Toast.makeText(getActivity(), "Date added", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Adding date did not work", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(View v){
        try {
            requireActivity().getContentResolver().delete(HabiProvider.CONTENT_URI_CALENDAR, getUsername(), null);
            date.setText("No events added \n");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Deleting dates did not work", Toast.LENGTH_SHORT).show();
        }
    }

    public String getUsername() {
        UserManager currentUser = new UserManager();
        return currentUser.getUsername(getActivity());
    }


    public void getAllDates(View view) {
        date.setText("No events added \n");
        try (Cursor c = requireActivity().getContentResolver().query(HabiProvider.CONTENT_URI_CALENDAR, null, getUsername(), null, null)) {
            if (c.moveToFirst()) {
                String result = "All Dates: \n";
                do {
                    for (int i = 1; i < c.getColumnCount(); i++) {
                        if(c.getString(i).length()>=20){
                            int newline = c.getString(i).length()/20;
                            for(int j=0; j<=newline; j++){
                                if((j*20)+20>c.getString(i).length()){
                                    result = result.concat(c.getString(i).substring(j*20));
                                } else{
                                    result = result.concat(c.getString(i).substring(j*20, (j*20)+20)  + "\n                             ");
                                }
                            }
                        }
                        else {
                            result = result.concat(c.getString(i) + "       ");
                        }
                    }
                    result = result.concat("\n\n");
                } while (c.moveToNext());
                date.setText(result);
            }
        }
    }
}