// Assignment 5
// EditUserFragment.java
// Bailey Bowling

package com.example.assignmen5;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditUserFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private EditText nameEditText, emailEditText;
    private RadioGroup roleRadioGroup;
    private User user;

    public EditUserFragment() {
        // Required empty public constructor
    }

    public static EditUserFragment newInstance(User user) {
        EditUserFragment fragment = new EditUserFragment();
        Bundle args = new Bundle();
        args.putSerializable("user", user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("user");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_user, container, false);

        nameEditText = view.findViewById(R.id.editName);
        emailEditText = view.findViewById(R.id.editEmail);
        roleRadioGroup = view.findViewById(R.id.roleRadioGroup);
        Button submitButton = view.findViewById(R.id.submitButton);
        Button cancelButton = view.findViewById(R.id.cancelButton);

        if (user != null) {
            nameEditText.setText(user.getName());
            emailEditText.setText(user.getEmail());
            // Set the correct role
            int roleButtonId = -1;
            switch (user.getRole()) {
                case "Student":
                    roleButtonId = R.id.radioStudent;
                    break;
                case "Employee":
                    roleButtonId = R.id.radioEmployee;
                    break;
                case "Other":
                    roleButtonId = R.id.radioOther;
                    break;
            }
            roleRadioGroup.check(roleButtonId);
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSubmit();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onCancelButtonClicked();
                }
            }
        });

        return view;
    }

    private void handleSubmit() {
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        int selectedRoleId = roleRadioGroup.getCheckedRadioButtonId();

        if (name.isEmpty()) {
            Toast.makeText(getActivity(), "Name is required", Toast.LENGTH_SHORT).show();
        } else if (email.isEmpty()) {
            Toast.makeText(getActivity(), "Email is required", Toast.LENGTH_SHORT).show();
        } else if (!isValidEmail(email)) {
            Toast.makeText(getActivity(), "Invalid email format", Toast.LENGTH_SHORT).show();
        } else if (selectedRoleId == -1) {
            Toast.makeText(getActivity(), "Role is required", Toast.LENGTH_SHORT).show();
        } else {
            String role = ((RadioButton) getView().findViewById(selectedRoleId)).getText().toString();
            user.setName(name);
            user.setEmail(email);
            user.setRole(role);
            if (mListener != null) {
                mListener.onSubmitButtonClicked(user);
            }
        }
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onSubmitButtonClicked(User user);
        void onCancelButtonClicked();
    }
}
