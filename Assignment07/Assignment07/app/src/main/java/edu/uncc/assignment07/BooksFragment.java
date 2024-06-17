// Assignment 07
// File: BooksFragment.java
// Full name of the student: Bailey Bowling

package edu.uncc.assignment07;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;

public class BooksFragment extends Fragment {

    private static final String ARG_GENRE = "genre";
    private String genre;
    private List<Book> books;
    private BooksListener mListener;

    public interface BooksListener {
        void gotoBookDetails(Book book);
        void goBackToGenres();
    }

    public static BooksFragment newInstance(String genre) {
        BooksFragment fragment = new BooksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_GENRE, genre);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BooksListener) {
            mListener = (BooksListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement BooksListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, container, false);

        if (getArguments() != null) {
            genre = getArguments().getString(ARG_GENRE);
        }

        TextView textViewGenre = view.findViewById(R.id.textViewGenre);
        textViewGenre.setText(genre);

        ListView listView = view.findViewById(R.id.listViewBooks);
        books = Data.getBooksByGenre(genre);

        BookAdapter adapter = new BookAdapter();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = books.get(position);
                mListener.gotoBookDetails(book);
            }
        });

        view.findViewById(R.id.buttonBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goBackToGenres();
            }
        });

        return view;
    }

    private class BookAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return books.size();
        }

        @Override
        public Object getItem(int position) {
            return books.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_book, parent, false);
            }

            TextView textViewTitle = convertView.findViewById(R.id.textViewTitle);
            TextView textViewAuthor = convertView.findViewById(R.id.textViewAuthor);
            TextView textViewGenre = convertView.findViewById(R.id.textViewGenre);
            TextView textViewYear = convertView.findViewById(R.id.textViewYear);

            Book book = books.get(position);

            textViewTitle.setText(book.getTitle());
            textViewAuthor.setText(book.getAuthor());
            textViewGenre.setText(book.getGenre());
            textViewYear.setText(String.valueOf(book.getYear()));

            return convertView;
        }
    }
}
