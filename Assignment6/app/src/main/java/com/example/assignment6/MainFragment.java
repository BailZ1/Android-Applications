package com.example.assignment6;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainFragment extends Fragment {

    private MainFragmentListener listener;

    public interface MainFragmentListener {
        void onStartButtonClicked();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainFragmentListener) {
            listener = (MainFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement MainFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Button startButton = view.findViewById(R.id.buttonStart);
        startButton.setOnClickListener(v -> listener.onStartButtonClicked());
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
