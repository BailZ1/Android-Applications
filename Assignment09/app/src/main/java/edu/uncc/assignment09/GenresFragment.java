package edu.uncc.assignment09;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.uncc.assignment09.databinding.FragmentGenresBinding;
import edu.uncc.assignment09.databinding.GenresRowItemBinding;

public class GenresFragment extends Fragment implements GenreRecyclerViewAdapter.IGenreRecycler {
    public GenresFragment() {
        // Required empty public constructor
    }

    FragmentGenresBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGenresBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    ArrayList<String> mGenres = Data.getAllGenres();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Genres");

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.recyclerView.setAdapter(new GenreRecyclerViewAdapter(mGenres, this));

    }

    GenresListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof GenresListener) {
            mListener = (GenresListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement GenresListener");
        }
    }

    @Override
    public void sendGenre(String genre) {
        mListener.gotoBooksForGenre(genre);
    }

    interface GenresListener {
        void gotoBooksForGenre(String genre);
    }



}

class GenreRecyclerViewAdapter extends RecyclerView.Adapter<GenreRecyclerViewAdapter.GenreViewHolder> {
    ArrayList<String> mGenres;
    IGenreRecycler mListener;

    public GenreRecyclerViewAdapter(ArrayList<String> mGenres, IGenreRecycler mListener) {
        this.mListener = mListener;
        this.mGenres = mGenres;
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GenresRowItemBinding mBinding = GenresRowItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        GenreViewHolder holder = new GenreViewHolder(mBinding, mListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        String genre = mGenres.get(position);
        holder.mBinding.textViewGenreTemplate.setText(genre);
        holder.position = holder.getAdapterPosition();
        holder.genre = genre;
    }

    @Override
    public int getItemCount() {
        return mGenres.size();
    }

    public static class GenreViewHolder extends RecyclerView.ViewHolder {

        GenresRowItemBinding mBinding;
        int position;
        IGenreRecycler mListener;
        String genre;
        public GenreViewHolder(GenresRowItemBinding binding, IGenreRecycler mListener) {
            super(binding.getRoot());
            mBinding = binding;
            this.mListener = mListener;

            mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.sendGenre(genre);
                }
            });
        }
    }

    interface IGenreRecycler {
        void sendGenre(String genre);
    }
}