package edu.uncc.midtermapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SelectDiscountFragment extends Fragment {

    private RadioGroup discountRadioGroup;
    private RadioButton customRadioButton;
    private SeekBar customSeekBar;
    private TextView customSeekBarValue;
    private String selectedDiscount;

    public interface SelectDiscountFragmentListener {
        void onDiscountSelected(String discount);
    }

    private SelectDiscountFragmentListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof SelectDiscountFragmentListener) {
            listener = (SelectDiscountFragmentListener) getActivity();
        } else {
            throw new RuntimeException(getActivity().toString()
                    + " must implement SelectDiscountFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_discount, container, false);

        discountRadioGroup = view.findViewById(R.id.discountRadioGroup);
        customRadioButton = view.findViewById(R.id.customRadioButton);
        customSeekBar = view.findViewById(R.id.customSeekBar);
        customSeekBarValue = view.findViewById(R.id.customSeekBarValue);

        customSeekBar.setMax(50);
        customSeekBar.setProgress(25);
        customSeekBarValue.setText(customSeekBar.getProgress() + "%");

        customSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                customSeekBarValue.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        Button submitButton = view.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = discountRadioGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(getActivity(), "A selection is required", Toast.LENGTH_SHORT).show();
                } else {
                    if (selectedId == R.id.customRadioButton) {
                        selectedDiscount = customSeekBar.getProgress() + "%";
                    } else {
                        RadioButton selectedRadioButton = view.findViewById(selectedId);
                        selectedDiscount = selectedRadioButton.getText().toString();
                    }
                    listener.onDiscountSelected(selectedDiscount);
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
