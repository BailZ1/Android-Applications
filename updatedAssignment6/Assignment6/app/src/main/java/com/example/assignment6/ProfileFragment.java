package com.example.assignment6;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProfileFragment extends Fragment {

    private static final String ARG_USER = "user";

    private User user;

    public static ProfileFragment newInstance(User user) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable(ARG_USER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView nameTextView = view.findViewById(R.id.name_text_view);
        TextView emailTextView = view.findViewById(R.id.email_text_view);
        TextView ageTextView = view.findViewById(R.id.age_text_view);
        TextView countryTextView = view.findViewById(R.id.country_text_view);
        TextView dobTextView = view.findViewById(R.id.dob_text_view);

        nameTextView.setText(user.getName());
        emailTextView.setText(user.getEmail());
        ageTextView.setText(String.valueOf(user.getAge()));
        countryTextView.setText(user.getCountry());
        dobTextView.setText(user.getDateOfBirth());

        return view;
    }
}
