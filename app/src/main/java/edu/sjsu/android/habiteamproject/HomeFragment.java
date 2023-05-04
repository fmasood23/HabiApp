package edu.sjsu.android.habiteamproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment {


    public HomeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //on click listeners for tracker pages
        view.findViewById(R.id.water_button).setOnClickListener(v ->
                getParentFragmentManager().beginTransaction().replace(R.id.current_fragment, new WaterTracker()).addToBackStack(null).commit());

        view.findViewById(R.id.to_do_button).setOnClickListener(v ->
                getParentFragmentManager().beginTransaction().replace(R.id.current_fragment, new ToDoList()).addToBackStack(null).commit());

        view.findViewById(R.id.sleep_button).setOnClickListener(v ->
                getParentFragmentManager().beginTransaction().replace(R.id.current_fragment, new SleepTracker()).addToBackStack(null).commit());

        view.findViewById(R.id.grocery_button).setOnClickListener(v ->
                getParentFragmentManager().beginTransaction().replace(R.id.current_fragment, new GroceryList()).addToBackStack(null).commit());

        return view;
    }

}