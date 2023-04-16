package edu.sjsu.android.habiteamproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SettingFragment extends Fragment {

    public SettingFragment() {
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
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        //onclick listener for sign out
        view.findViewById(R.id.signout).setOnClickListener(v -> signOut());
        return view;

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

    private void signOut(){
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", getUsername());
        contentValues.put("logged_in", "false");
        getActivity().getContentResolver().update(HabiProvider.CONTENT_URI_CURRENT, contentValues, getUsername(), null);


        Intent i = new Intent(getActivity(), MainActivity.class);
        startActivity(i);
    }
}