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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class CountryFragment extends Fragment {

    private CountryFragmentListener listener;

    public interface CountryFragmentListener {
        void onCountrySelected(String country);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CountryFragmentListener) {
            listener = (CountryFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement CountryFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country, container, false);
        RadioGroup countryRadioGroup = view.findViewById(R.id.radioGroupCountry);

        Button submitButton = view.findViewById(R.id.submit_button);
        submitButton.setOnClickListener(v -> {
            int selectedId = countryRadioGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(getActivity(), "Please select a country", Toast.LENGTH_SHORT).show();
                return;
            }
            String country = ((RadioButton) view.findViewById(selectedId)).getText().toString();
            listener.onCountrySelected(country);
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
