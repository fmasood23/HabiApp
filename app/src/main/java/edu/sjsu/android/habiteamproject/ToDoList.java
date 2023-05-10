package edu.sjsu.android.habiteamproject;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ToDoList extends Fragment {
    
    private ListView list;
    private ArrayAdapter<String> mAdapter;


    public ToDoList() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_to_do_list, container, false);
        list = (ListView) view.findViewById(R.id.list_todo);
        view.findViewById(R.id.add_to_do).setOnClickListener(this::add);
        view.findViewById(R.id.delete_to_do).setOnClickListener(this::delete);
        populateList();
        return view;
    }

    public void add(View v) {
        final EditText taskEditText = new EditText(getContext());
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Add a new To-Do Item:")
                .setMessage("What is your to-do item?")
                .setView(taskEditText)
                .setPositiveButton("Add Item", (dialog1, which) -> {
                    String task = String.valueOf(taskEditText.getText());
                    insert(task);
                    populateList();
                })
                .setNegativeButton("Back", null)
                .create();
        dialog.show();
    }

    private void populateList() {
        ArrayList<String> to_do_list = new ArrayList<>();

        if(getItems()!=null) {
            to_do_list.addAll(getItems());
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(getActivity(),
                    R.layout.item,
                    R.id.task_title,
                    to_do_list);
            list.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(to_do_list);
            mAdapter.notifyDataSetChanged();
        }
    }

    public String getUsername() {
        UserManager currentUser = new UserManager();
        return currentUser.getUsername(getActivity());
    }

    public ArrayList<String> getItems() {
        ArrayList<String> items = new ArrayList<>();
        try (Cursor c = requireActivity().getContentResolver().query(HabiProvider.CONTENT_URI_TO_DO, null, null, null, null)) {
            if (c.moveToFirst()) {
                do {
                    if(c.getString(0).equals(getUsername())) {
                        items.add(c.getString(1));
                    }
                } while (c.moveToNext());
                return items;
            } else {
                return null;
            }
        }
    }

    public void insert(String content){
        try{
            if(content.isEmpty()){
                Toast.makeText(getActivity(), "Field is required", Toast.LENGTH_SHORT).show();
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("username", getUsername());
            contentValues.put("content", content);

            requireActivity().getContentResolver().insert(HabiProvider.CONTENT_URI_TO_DO, contentValues);
        }
        catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(), "Could not add item", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(View v){
        try{
            requireActivity().getContentResolver().delete(HabiProvider.CONTENT_URI_TO_DO, getUsername(), null);
            populateList();
        }
        catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(), "Could not delete list", Toast.LENGTH_SHORT).show();
        }
    }
}