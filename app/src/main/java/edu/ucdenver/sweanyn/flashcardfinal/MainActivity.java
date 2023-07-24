package edu.ucdenver.sweanyn.flashcardfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import edu.ucdenver.sweanyn.flashcardfinal.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements AddBookDialog.Listener {

    private ActivityMainBinding binding;
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    
    private FlashcardDatabase flashcardDatabase;
    private List<Book> flashcardBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        FloatingActionButton fab = binding.createNewBookFab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event here to create a new flashcard book.
                AddBookDialog addBookDialog = new AddBookDialog();
                addBookDialog.setListener(MainActivity.this);  // Set the MainActivity as the Listener
                addBookDialog.show(getSupportFragmentManager(), "");
            }
        });

        recyclerView = binding.contentCards.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //linear layout manager for a single column scrollable list
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        flashcardDatabase = FlashcardDatabase.getInstance(this);
        flashcardBooks = flashcardDatabase.bookDao().getAll(); //fetch all our books!

        bookAdapter = new BookAdapter(flashcardBooks);
        recyclerView.setAdapter(bookAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handles overflow for adding new card
        int id = item.getItemId();

        if (id == R.id.action_new_book) {
            // Handle the click event here to create a new flashcard book.
            AddBookDialog addBookDialog = new AddBookDialog();
            addBookDialog.setListener(MainActivity.this);  // Set the MainActivity as the Listener
            addBookDialog.show(getSupportFragmentManager(), "");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBookAdded(Book newBook) {
        bookAdapter.addBook(newBook);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Apply theme to the menu.
        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(this, R.style.CustomPopupMenu);
        }
        return true;
    }
}


//Nathan
//MainActivity.java (database, onClicks, bindings, recyclerView)
//Logic to process each database entry (for the recyclerView)
//Onclick for each book displayed on main
//Implement the database for storing flashcards and books in main
//On Click for the Answer to reveal/hide the answer

//Sejal
//Add some sort of dialog when created a new book (in main).
    //need to add on click for save button in AddBookDialog.java

//Add dialog box for new card (in book)
    //Need a method in MainActivity for adding card (just like onBookAdded) so that we can link menu_book to it
    //Need to complete AddCardDialog.java

//Implement the buttons for all menus and FAB's
//Add edit button functionality for Question, Answer, and item_flashcard_book button.
        //Dialog boxes?
    //Created edit_text.xml as a common layout for editing Question, Answer, and item_flashcard_book button
