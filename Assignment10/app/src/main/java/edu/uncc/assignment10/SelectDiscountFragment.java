package edu.uncc.assignment10;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import edu.uncc.assignment10.databinding.SimpleRowItemBinding;
import edu.uncc.assignment10.databinding.FragmentSelectDiscountBinding;


public class SelectDiscountFragment extends Fragment {
    public SelectDiscountFragment() {
        // Required empty public constructor
    }

    FragmentSelectDiscountBinding binding;
    String[] rowItems = {"10%", "15%", "18%", "Custom"};
    int seekBarProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectDiscountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.seekBar.setMax(50);
        binding.seekBar.setProgress(25);

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.textViewSeekBarProgress.setText(progress + "%");
                seekBarProgress = progress;
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

        binding.recyclerViewDiscount.setHasFixedSize(true);
        binding.recyclerViewDiscount.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.recyclerViewDiscount.setAdapter(new DiscountRecyclerViewAdapter());
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

    class DiscountRecyclerViewAdapter extends RecyclerView.Adapter<SelectDiscountFragment.DiscountRecyclerViewAdapter.DiscountViewHolder> {

        public DiscountRecyclerViewAdapter() {
        }

        @NonNull
        @Override
        public DiscountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            SimpleRowItemBinding mBinding = SimpleRowItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            DiscountViewHolder holder = new DiscountViewHolder(mBinding);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull DiscountViewHolder holder, int position) {
            String rowItem = rowItems[position];
            holder.mBinding.textViewRowItemTemplate.setText(rowItem);
            holder.position = holder.getAdapterPosition();
        }

        @Override
        public int getItemCount() {
            return 4;
        }

        public class DiscountViewHolder extends RecyclerView.ViewHolder {

            SimpleRowItemBinding mBinding;
            int position;
            public DiscountViewHolder(SimpleRowItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;

                mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position == 0) {
                            mListener.onDiscountSelected(10);
                        } else if (position == 1) {
                            mListener.onDiscountSelected(15);
                        } else if (position == 2) {
                            mListener.onDiscountSelected(18);
                        } else if (position == 3) {
                            mListener.onDiscountSelected(seekBarProgress);
                        }
                    }
                });
            }
        }

    }
}