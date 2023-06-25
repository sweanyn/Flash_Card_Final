package edu.ucdenver.sweanyn.flashcardfinal;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FlashcardBookActivity extends AppCompatActivity {
    private TextView flashcardBookNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_book);

        flashcardBookNameTextView = findViewById(R.id.flashcard_book_name);

        FlashcardBook flashcardBook = (FlashcardBook) getIntent().getSerializableExtra("flashcard_book");
        if (flashcardBook != null) {
            flashcardBookNameTextView.setText(flashcardBook.getName());
        }
    }
}

