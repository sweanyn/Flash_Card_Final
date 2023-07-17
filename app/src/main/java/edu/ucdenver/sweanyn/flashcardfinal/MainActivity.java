package edu.ucdenver.sweanyn.flashcardfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import edu.ucdenver.sweanyn.flashcardfinal.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    
    private FlashcardDatabase flashcardDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.createNewBookFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event here to create a new flashcard book.
                AddBookDialog addBookDialog = new AddBookDialog();
                addBookDialog.show(getSupportFragmentManager(), "");
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 is the number of columns, adjust as necessary

        List<Book> flashcardBooks = new ArrayList<>();
        // Add flashcard books to the list

        bookAdapter = new BookAdapter(flashcardBooks);
        recyclerView.setAdapter(bookAdapter);
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
//Implement the buttons for all menus and FAB's
//Add edit button functionality for Question, Answer, and item_flashcard_book button.
        //Dialog boxes?
