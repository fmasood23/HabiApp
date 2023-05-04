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


public class GroceryList extends Fragment {

    private ListView list;
    private ArrayAdapter<String> mAdapter;

    public GroceryList() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grocery_list, container, false);
        list = (ListView) view.findViewById(R.id.list_grocery);
        view.findViewById(R.id.add_grocery).setOnClickListener(this::add);
        view.findViewById(R.id.delete_grocery).setOnClickListener(this::delete);
        populateList();
        return view;
    }

    public void add(View v) {
        final EditText taskEditText = new EditText(getContext());
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Add a new Grocery Item:")
                .setMessage("What is your grocery item?")
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
        ArrayList<String> grocery_list = new ArrayList<>();

        if(getItems()!=null) {
            grocery_list.addAll(getItems());
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(getActivity(),
                    R.layout.g_item,
                    R.id.task_title,
                    grocery_list);
            list.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(grocery_list);
            mAdapter.notifyDataSetChanged();
        }
    }

    public String getUsername() {
        UserManager currentUser = new UserManager();
        return currentUser.getUsername(getActivity());
    }

    public ArrayList<String> getItems() {
        ArrayList<String> items = new ArrayList<>();
        try (Cursor c = requireActivity().getContentResolver().query(HabiProvider.CONTENT_URI_GROCERY, null, null, null, null)) {
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

    public void insert(String grocery){
        try{
            if(grocery.isEmpty()){
                Toast.makeText(getActivity(), "Field is required", Toast.LENGTH_SHORT).show();
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("username", getUsername());
            contentValues.put("item", grocery);

            requireActivity().getContentResolver().insert(HabiProvider.CONTENT_URI_GROCERY, contentValues);
        }
        catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(View v){
        try{
            requireActivity().getContentResolver().delete(HabiProvider.CONTENT_URI_GROCERY, getUsername(), null);
            populateList();
        }
        catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
        }
    }
}