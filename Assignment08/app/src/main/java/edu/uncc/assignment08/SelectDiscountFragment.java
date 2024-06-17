package edu.uncc.assignment08;
// Bailey Bowling
// Assignment 08
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;

import edu.uncc.assignment08.databinding.FragmentSelectDiscountBinding;


public class SelectDiscountFragment extends Fragment {

    private final String[] discountOptions = {"10%", "15%", "18%", "Custom"};

    public SelectDiscountFragment() {
        // Required empty public constructor
    }

    FragmentSelectDiscountBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectDiscountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.seekBar.setMax(50);
        binding.seekBar.setProgress(25);

        // Set up the ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, discountOptions);
        binding.listView.setAdapter(adapter);

        // Set the onItemClickListener for the ListView
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedDiscount = adapter.getItem(position);
                if (selectedDiscount.equals("Custom")) {
                    // Set selectedDiscount to the value of the SeekBar
                    double discount = binding.seekBar.getProgress();
                    mListener.onDiscountSelected(discount);
                } else {
                    // Set selectedDiscount to the value of the selected item
                    mListener.onDiscountSelected(Double.parseDouble(selectedDiscount.replace("%", "")));
                }
            }
        });

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.textViewSeekBarProgress.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelSelectDiscount();
            }
        });
    }

    SelectDiscountListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SelectDiscountListener) {
            mListener = (SelectDiscountListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SelectDiscountListener");
        }
    }

    interface SelectDiscountListener {
        void onDiscountSelected(double discount);
        void onCancelSelectDiscount();
    }
}