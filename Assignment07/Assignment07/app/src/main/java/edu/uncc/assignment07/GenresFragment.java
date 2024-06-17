// Assignment 07
// GenresFragment.java
// Bailey Bowling

package edu.uncc.assignment07;

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
import android.widget.ListView;

import java.util.ArrayList;

import edu.uncc.assignment07.databinding.FragmentGenresBinding;

public class GenresFragment extends Fragment {
    public GenresFragment() {
        // Required empty public constructor
    }

    FragmentGenresBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGenresBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    ArrayList<String> mGenres = Data.getAllGenres();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Genres");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mGenres);
        binding.listViewGenres.setAdapter(adapter);

        binding.listViewGenres.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String genre = mGenres.get(position);
                mListener.gotoBooksForGenre(genre);
            }
        });
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

    interface GenresListener {
        void gotoBooksForGenre(String genre);
    }
}
