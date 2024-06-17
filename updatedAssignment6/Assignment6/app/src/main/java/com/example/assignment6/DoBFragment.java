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
import android.widget.DatePicker;
import android.widget.Toast;
import java.util.Calendar;

public class DoBFragment extends Fragment {

    private DoBFragmentListener listener;
    private DatePicker datePicker;

    public interface DoBFragmentListener {
        void onDateSelected(String date);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DoBFragmentListener) {
            listener = (DoBFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement DoBFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_do_b, container, false);
        datePicker = view.findViewById(R.id.datePicker);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -18);
        datePicker.setMaxDate(calendar.getTimeInMillis());

        Button submitButton = view.findViewById(R.id.submit_button);
        submitButton.setOnClickListener(v -> {
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth() + 1;
            int year = datePicker.getYear();
            String date = day + "/" + month + "/" + year;
            listener.onDateSelected(date);
        });

        Button cancelButton = view.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
