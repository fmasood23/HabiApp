package edu.sjsu.android.habiteamproject;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class SettingFragment extends Fragment {

    public SettingFragment() {
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
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        //onclick listener for sign out
        view.findViewById(R.id.signout).setOnClickListener(v -> signOut());
        view.findViewById(R.id.change_pass).setOnClickListener(this::add);
        view.findViewById(R.id.reset_user).setOnClickListener(this::reset_acc);

        return view;

    }

    public void reset_acc(View v){
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Warning!")
                .setMessage("This will clear all user history. Would you like to proceed?")
                .setPositiveButton("Yes", (dialog1, which) -> {
                    try{
                        String user = getUsername();
                        requireActivity().getContentResolver().delete(HabiProvider.CONTENT_URI_WATER, user, null);
                        requireActivity().getContentResolver().delete(HabiProvider.CONTENT_URI_TO_DO, user, null);
                        requireActivity().getContentResolver().delete(HabiProvider.CONTENT_URI_SLEEP, user, null);
                        requireActivity().getContentResolver().delete(HabiProvider.CONTENT_URI_GROCERY, user, null);
                        requireActivity().getContentResolver().delete(HabiProvider.CONTENT_URI_CALENDAR, user, null);

                        Toast.makeText(getActivity(), "User data was deleted", Toast.LENGTH_SHORT).show();
                    } catch (SQLiteException e){
                        Toast.makeText(getActivity(), "User data was not erased", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null)
                .create();
        dialog.show();
    }

    public String getUsername() {
        UserManager currentUser = new UserManager();
        return currentUser.getUsername(getActivity());
    }

    private void signOut(){
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("username", getUsername());
            contentValues.put("logged_in", "false");
            requireActivity().getContentResolver().update(HabiProvider.CONTENT_URI_CURRENT, contentValues, getUsername(), null);


            Intent i = new Intent(getActivity(), MainActivity.class);
            startActivity(i);
        }
        catch(SQLiteException e){
            Toast.makeText(getActivity(), "Sign out could not be completed", Toast.LENGTH_SHORT).show();
        }
    }

    public void add(View v) {
        final EditText taskEditText = new EditText(getContext());
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Change your Password:")
                .setMessage("Enter your new password:")
                .setView(taskEditText)
                .setPositiveButton("Change", (dialog1, which) -> {
                    String pass = String.valueOf(taskEditText.getText());
                    updateAcc(pass);
                })
                .setNegativeButton("Back", null)
                .create();
        dialog.show();
    }

    private void updateAcc(String pass){
        if(pass.isEmpty()){
            Toast.makeText(getActivity(), "Do not password field leave empty", Toast.LENGTH_SHORT).show();
        }

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("password", pass);
            requireActivity().getContentResolver().update(HabiProvider.CONTENT_URI, contentValues, getUsername(), null);
            Toast.makeText(getActivity(), "Password changed", Toast.LENGTH_SHORT).show();
        }
        catch(SQLiteException e){
            Toast.makeText(getActivity(), "Password couldn't be changed", Toast.LENGTH_SHORT).show();
        }
    }
}