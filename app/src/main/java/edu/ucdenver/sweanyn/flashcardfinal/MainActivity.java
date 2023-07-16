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
    private BookAdapter adapter;

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
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 is the number of columns, adjust as necessary

        List<Book> flashcardBooks = new ArrayList<>();
        // Add flashcard books to the list

        adapter = new BookAdapter(flashcardBooks);
        recyclerView.setAdapter(adapter);
    }
}

//Implement the buttons for all menus and FAB's
//Implement the database for storing flashcards and books
//Logic to process each database entry (for the recyclerView)
//Onclick for each book displayed on main
//Add some sort of dialog when created a new book.
//Add edit button functionality for Question, Answer, and item_flashcard_book button.
//On Click for the Answer to reveal/hide the answer


