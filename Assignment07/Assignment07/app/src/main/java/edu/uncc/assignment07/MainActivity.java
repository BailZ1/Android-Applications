// Assignment 07
// MainActivity.java
// Bailey Bowling

package edu.uncc.assignment07;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements GenresFragment.GenresListener, BooksFragment.BooksListener, BookDetailsFragment.BookDetailsListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main, new GenresFragment())
                    .commit();
        }
    }

    @Override
    public void gotoBooksForGenre(String genre) {
        BooksFragment booksFragment = BooksFragment.newInstance(genre);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, booksFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoBookDetails(Book book) {
        BookDetailsFragment bookDetailsFragment = BookDetailsFragment.newInstance(book);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, bookDetailsFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goBackToGenres() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void goBackToBooks() {
        getSupportFragmentManager().popBackStack();
    }
}
