// Assignment 07
// BookDetailsFragment.java
// Bailey Bowling

package edu.uncc.assignment07;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BookDetailsFragment extends Fragment {

    private static final String ARG_BOOK = "book";
    private Book book;
    private BookDetailsListener mListener;

    public interface BookDetailsListener {
        void goBackToBooks();
    }

    public static BookDetailsFragment newInstance(Book book) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOK, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BookDetailsListener) {
            mListener = (BookDetailsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement BookDetailsListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_details, container, false);

        if (getArguments() != null) {
            book = (Book) getArguments().getSerializable(ARG_BOOK);
        }

        TextView textViewTitle = view.findViewById(R.id.textViewBookTitle);
        TextView textViewAuthor = view.findViewById(R.id.textViewAuthorName);
        TextView textViewGenre = view.findViewById(R.id.textViewGenre);
        TextView textViewYear = view.findViewById(R.id.textViewYear);

        textViewTitle.setText(book.getTitle());
        textViewAuthor.setText(book.getAuthor());
        textViewGenre.setText(book.getGenre());
        textViewYear.setText(String.valueOf(book.getYear()));

        view.findViewById(R.id.buttonBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goBackToBooks();
            }
        });

        return view;
    }
}
