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

import edu.uncc.assignment10.databinding.FragmentSelectCategoryBinding;
import edu.uncc.assignment10.databinding.SimpleRowItemBinding;


public class SelectCategoryFragment extends Fragment {

    String[] mCategories = {"Housing", "Transportation", "Food", "Health", "Other"};

    public SelectCategoryFragment() {
        // Required empty public constructor
    }

    FragmentSelectCategoryBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelSelectCategory();
            }
        });

        binding.recyclerViewCategory.setHasFixedSize(true);
        binding.recyclerViewCategory.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.recyclerViewCategory.setAdapter(new CategoryRecyclerViewAdapter());
    }

    SelectCategoryListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SelectCategoryListener) {
            mListener = (SelectCategoryListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SelectCategoryListener");
        }
    }

    interface SelectCategoryListener {
        void selectCategory(String category);
        void onCancelSelectCategory();
    }

    class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.CategoryViewHolder> {

        public CategoryRecyclerViewAdapter() {
        }

        @NonNull
        @Override
        public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            SimpleRowItemBinding mBinding = SimpleRowItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            CategoryViewHolder holder = new CategoryViewHolder(mBinding);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
            String category = mCategories[position];
            holder.mBinding.textViewRowItemTemplate.setText(category);
            holder.position = holder.getAdapterPosition();
            holder.category = category;
        }

        @Override
        public int getItemCount() {
            return 5;
        }

        public class CategoryViewHolder extends RecyclerView.ViewHolder {

            SimpleRowItemBinding mBinding;
            int position;
            String category;
            public CategoryViewHolder(SimpleRowItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;

                mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.selectCategory(category);
                    }
                });
            }
        }

    }

}