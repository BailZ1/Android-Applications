package com.example.assignment6;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateUserFragment extends Fragment {

    private CreateUserFragmentListener listener;
    private EditText nameEditText, emailEditText, ageEditText, countryEditText, dobEditText;

    public interface CreateUserFragmentListener {
        void onCountrySelected(String country);
        void onDateSelected(String date);
        void onUserCreated(User user);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CreateUserFragmentListener) {
            listener = (CreateUserFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement CreateUserFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_user, container, false);
        nameEditText = view.findViewById(R.id.enterName);
        emailEditText = view.findViewById(R.id.enterEmail);
        ageEditText = view.findViewById(R.id.enterAge);
        countryEditText = view.findViewById(R.id.countryEditText);
        dobEditText = view.findViewById(R.id.dobEditText);

        Button selectCountryButton = view.findViewById(R.id.buttonCountry);
        selectCountryButton.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerView, new CountryFragment(), "COUNTRY_FRAGMENT")
                    .addToBackStack("CREATE_USER_FRAGMENT")
                    .commit();
        });

        Button selectDoBButton = view.findViewById(R.id.buttonDoB);
        selectDoBButton.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerView, new DoBFragment(), "DOB_FRAGMENT")
                    .addToBackStack("CREATE_USER_FRAGMENT")
                    .commit();
        });

        Button submitButton = view.findViewById(R.id.buttonSubmit);
        submitButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String age = ageEditText.getText().toString();
            String country = countryEditText.getText().toString();
            String dob = dobEditText.getText().toString();

            if (name.isEmpty() || email.isEmpty() || age.isEmpty() || country.isEmpty() || dob.isEmpty()) {
                Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = new User(name, email, Integer.parseInt(age), country, dob);
            listener.onUserCreated(user);
        });

        return view;
    }

    public void updateCountry(String country) {
        countryEditText.setText(country);
        Log.d("Updating Country", "blah " + country);
    }

    public void updateDateOfBirth(String date) {
        dobEditText.setText(date);
        Log.d("Updating dob", "blah " + date);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
