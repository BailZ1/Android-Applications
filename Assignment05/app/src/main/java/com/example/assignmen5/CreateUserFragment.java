// Assignment 5
// CreateUserFragment.java
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

public class CreateUserFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private EditText nameEditText, emailEditText;
    private RadioGroup roleRadioGroup;

    public CreateUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_user, container, false);

        nameEditText = view.findViewById(R.id.enterName);
        emailEditText = view.findViewById(R.id.enterEmail);
        roleRadioGroup = view.findViewById(R.id.roleRadioGroup);
        Button nextButton = view.findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleNext();
            }
        });

        return view;
    }

    private void handleNext() {
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
            User user = new User(name, email, role);
            if (mListener != null) {
                mListener.onNextButtonClicked(user);
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
        void onNextButtonClicked(User user);
    }
}
