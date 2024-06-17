package edu.uncc.assignment09;

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

import java.util.ArrayList;

import edu.uncc.assignment09.databinding.BookRowItemBinding;
import edu.uncc.assignment09.databinding.FragmentBooksBinding;
import edu.uncc.assignment09.databinding.GenresRowItemBinding;

public class BooksFragment extends Fragment {
    private static final String ARG_PARAM_GENRE = "ARG_PARAM_GENRE";
    private String mGenre;

    public static BooksFragment newInstance(String genre) {
        BooksFragment fragment = new BooksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_GENRE, genre);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGenre = getArguments().getString(ARG_PARAM_GENRE);
        }
    }

    ArrayList<Book> mBooks = new ArrayList<>();

    public BooksFragment() {
        // Required empty public constructor
    }

    FragmentBooksBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBooksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(mGenre);
        mBooks.clear();
        mBooks.addAll(Data.getBooksByGenre(mGenre));

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.closeBooks();
            }
        });

        binding.recyclerViewBooks.setHasFixedSize(true);
        binding.recyclerViewBooks.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.recyclerViewBooks.setAdapter(new BookRecyclerViewAdapter());
    }

    BooksListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BooksListener) {
            mListener = (BooksListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement BooksListener");
        }
    }

    interface BooksListener{
        void closeBooks();
        void gotoBookDetails(Book book);
    }

    class BookRecyclerViewAdapter extends RecyclerView.Adapter<BookRecyclerViewAdapter.BookViewHolder> {

        public BookRecyclerViewAdapter() {
        }

        @NonNull
        @Override
        public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            BookRowItemBinding mBinding = BookRowItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            BookViewHolder holder = new BookViewHolder(mBinding);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
            Book book = mBooks.get(position);
            holder.position = holder.getAdapterPosition();
            holder.book = book;

            holder.mBinding.textViewBookTitleTemplate.setText(book.getTitle());
            holder.mBinding.textViewAuthorNameTemplate.setText(book.getAuthor());
            holder.mBinding.textViewBookGenreTemplate.setText(book.getGenre());
            holder.mBinding.textViewYearTemplate.setText(book.getYear() + "");
        }

        @Override
        public int getItemCount() {
            return mBooks.size();
        }

        public class BookViewHolder extends RecyclerView.ViewHolder {

            BookRowItemBinding mBinding;
            int position;
            Book book;
            public BookViewHolder(BookRowItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;

                mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.gotoBookDetails(book);
                    }
                });
            }
        }
    }

}

