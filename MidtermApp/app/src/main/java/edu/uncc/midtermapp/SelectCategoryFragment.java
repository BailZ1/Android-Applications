package edu.uncc.midtermapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SelectCategoryFragment extends Fragment {

    private RadioGroup categoryRadioGroup;
    private String selectedCategory;

    public interface SelectCategoryFragmentListener {
        void onCategorySelected(String category);
    }

    private SelectCategoryFragmentListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof SelectCategoryFragmentListener) {
            listener = (SelectCategoryFragmentListener) getActivity();
        } else {
            throw new RuntimeException(getActivity().toString()
                    + " must implement SelectCategoryFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_category, container, false);

        categoryRadioGroup = view.findViewById(R.id.categoryRadioGroup);

        Button submitButton = view.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = categoryRadioGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(getActivity(), "A selection is required", Toast.LENGTH_SHORT).show();
                } else {
                    RadioButton selectedRadioButton = view.findViewById(selectedId);
                    selectedCategory = selectedRadioButton.getText().toString();
                    listener.onCategorySelected(selectedCategory);
                }
            }
        });

        Button cancelButton = view.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }
}
