package edu.sjsu.android.habiteamproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

public class HomeFragment extends Fragment {


    public HomeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

//        NavController controller = NavHostFragment.findNavController(this);
////
//        view.findViewById(R.id.water_button).setOnClickListener(nv ->
//                NavHostFragment.findNavController(this).navigate(R.id.action_homeFragment_to_waterTracker));

        view.findViewById(R.id.water_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new WaterTracker());
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        view.findViewById(R.id.to_do_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new ToDoList());
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        view.findViewById(R.id.to_do_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new ToDoList());
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        view.findViewById(R.id.sleep_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new SleepTracker());
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        view.findViewById(R.id.grocery_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new GroceryList());
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return view;
    }

}