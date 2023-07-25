package edu.ucdenver.sweanyn.flashcardfinal;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.ucdenver.sweanyn.flashcardfinal.databinding.ActivityBookBinding;

public class BookActivity extends AppCompatActivity implements AddCardDialog.Listener {
    private ActivityBookBinding binding;
    private BookDao bookDao;
    private long bookId;
    private FlashcardDao flashcardDao;
    private List<Flashcard> flashcards;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = ActivityBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);



        bookId = getIntent().getLongExtra("book_id", -1);
        if (bookId != -1)
        {
            FlashcardDatabase db = FlashcardDatabase.getInstance(this);
            flashcardDao = db.flashcardDao();
            bookDao = db.bookDao();
            getBook(bookId);
            loadFlashcards();
        }
        // Attach a click listener to the edit buttons
        // Question edit button.
        binding.contentCards.editBookQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // The current question text
                String currentQuestion = binding.contentCards.flashcardBookQuestion.getText().toString();
                showEditDialog(currentQuestion, true);
            }
        });
        // Answer edit button.
        binding.contentCards.editBookAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // The current answer text
                String currentAnswer = binding.contentCards.flashcardBookQuestion.getText().toString();
                showEditDialog(currentAnswer, false);
            }
        });
        // Attach a click listener to the answer view
        binding.contentCards.flashcardBookAnswer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // If the current text is "[ TAP TO REVEAL ]", change it to the actual answer
                // Otherwise, change it back to "[ TAP TO REVEAL ]"
                if (binding.contentCards.flashcardBookAnswer.getText().toString().equals("[ TAP TO REVEAL ]"))
                {
                    binding.contentCards.flashcardBookAnswer.setText(flashcards.get(currentIndex).getAnswer());
                }
                else
                {
                    binding.contentCards.flashcardBookAnswer.setText("[ TAP TO REVEAL ]");
                }
            }
        });
        //Setting the on click listeners for the Two FAB forward and backward buttons.
        //Forward button
        binding.nextfab.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                if (!flashcards.isEmpty())
                {
                    currentIndex = (currentIndex + 1) % flashcards.size();
                    updateCardData();
                }
            }
        });

        //Backward button
        binding.previousfab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (!flashcards.isEmpty())
                {
                    currentIndex = (currentIndex - 1 + flashcards.size()) % flashcards.size();
                    updateCardData();
                }
            }
        });
    }

    private void getBook(long bookId) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                Book book = bookDao.getById(bookId);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (book != null) {
                            binding.bookName.setText(book.getBookName());
                        }
                    }
                });
            }
        });
    }

    // The loadFlashcards method:
    private void loadFlashcards() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                flashcards = flashcardDao.getByBookId(bookId);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!flashcards.isEmpty()) {
                            updateCardData();
                        }
                    }
                });
            }
        });
    }

    // The updateCardData method:
    private void updateCardData()
    {
        if (flashcards.isEmpty())
        {
            binding.contentCards.flashcardBookQuestion.setText("Question");
            binding.contentCards.flashcardBookAnswer.setText("Answer");
        }
        else
        {
            Flashcard flashcard = flashcards.get(currentIndex);
            binding.contentCards.flashcardBookQuestion.setText(flashcard.getQuestion());
            binding.contentCards.flashcardBookAnswer.setText("[ TAP TO REVEAL ]");
        }
    }

    private void updateBookName(String newBookName) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                Book book = bookDao.getById(bookId);
                if (book != null) {
                    book.setBookName(newBookName);
                    bookDao.update(book);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // Update the book name in the UI
                            binding.bookName.setText(newBookName);
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book, menu);
        return true;
    }


    // Handles the click events on menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.action_home) {
            // handle click event on 'Home' action
            Intent homeIntent = new Intent(this, MainActivity.class);
            startActivity(homeIntent);
            return true;

        }

        else if (item.getItemId() == R.id.action_edit_book) {
            // handle click event on 'Edit Book Name' action
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setTextColor(Color.DKGRAY);
            builder.setView(input);

            builder.setTitle("Edit Book Name")
                    .setMessage("Enter the new name for the book.")
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            String newBookName = input.getText().toString();
                            if (!TextUtils.isEmpty(newBookName)) {
                                //Saving the book name.
                                updateBookName(newBookName);
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });

            AlertDialog dialog = builder.show();

            // Change the button color
            Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            positiveButton.setTextColor(Color.DKGRAY);
            negativeButton.setTextColor(Color.DKGRAY);
            return true;

        }

        else if (item.getItemId() == R.id.action_delete_book) {
            // handle click event on 'Delete Book' action
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete Book")
                    .setMessage("Are you sure you want to delete this book?")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Delete the book from the database
                            Book book = bookDao.getById(bookId);
                            bookDao.delete(book);
                            //need to go to main activity after this (to do)
                            finish();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });

            AlertDialog dialog = builder.show();

            // Change the button color
            Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            positiveButton.setTextColor(Color.DKGRAY);
            negativeButton.setTextColor(Color.DKGRAY);
            return true;

        }

        else if (item.getItemId() == R.id.action_new_card) {
            AddCardDialog dialog = new AddCardDialog(bookId);
            dialog.setListener(new AddCardDialog.Listener() {
                @Override
                public void onCardAdded(Flashcard newFlashcard) {
                    loadFlashcards();
                    updateCardData();
                    AddCardDialog dialog = new AddCardDialog(bookId);
                    dialog.setListener(this);
                }
            });
            dialog.show(getSupportFragmentManager(), "AddCardDialog");
            return true;

        }

        else if (item.getItemId() == R.id.action_delete_card) {
            if (!flashcards.isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Delete Card")
                        .setMessage("Are you sure you want to delete this card?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Flashcard currentFlashcard = flashcards.get(currentIndex);

                                // Create a new thread for database operation
                                ExecutorService executor = Executors.newSingleThreadExecutor();
                                Handler handler = new Handler(Looper.getMainLooper());

                                executor.execute(() -> {
                                    // Deleting the current card
                                    flashcardDao.delete(currentFlashcard);

                                    // Load flashcards again and update the card on the screen in the main thread
                                    handler.post(() -> {
                                        // Removing the card from our List.
                                        flashcards.remove(currentIndex);
                                        // Resetting the index if required.
                                        if (currentIndex >= flashcards.size()) {
                                            currentIndex = 0;
                                        }
                                        loadFlashcards();
                                        updateCardData();
                                    });
                                });
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });

                AlertDialog dialog = builder.show();

                // Change the button color
                Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                positiveButton.setTextColor(Color.DKGRAY);
                negativeButton.setTextColor(Color.DKGRAY);
            }
            return true;
        }


        else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCardAdded(Flashcard newFlashcard) {
        loadFlashcards();
        updateCardData();
    }


    private void showEditDialog(String currentText, boolean isQuestion) {
        if (!flashcards.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setText("");
            builder.setView(input);

            builder.setTitle(isQuestion ? "Edit Question" : "Edit Answer")
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            String newText = input.getText().toString();
                            if (!TextUtils.isEmpty(newText)) {
                                // Saving the new text.
                                if (isQuestion) {
                                    updateQuestion(newText);
                                } else {
                                    updateAnswer(newText);
                                }
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });

            AlertDialog dialog = builder.show();
            // Change the button color
            Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            positiveButton.setTextColor(Color.DKGRAY);
            negativeButton.setTextColor(Color.DKGRAY);
            // TODO: Customize the dialog if needed
        }
    }

    private void updateQuestion(String newQuestion) {
        if (!flashcards.isEmpty()) {
            // Update the question of the current flashcard
            Flashcard currentFlashcard = flashcards.get(currentIndex);
            currentFlashcard.setQuestion(newQuestion);
            // Update the flashcard in the database
            Executors.newSingleThreadExecutor().execute(() -> {
                flashcardDao.update(currentFlashcard);
                // fetch the updated flashcards in the main thread
                runOnUiThread(() -> {
                    loadFlashcards();
                    updateCardData();
                });
            });
        }
    }

    private void updateAnswer(String newAnswer) {
        if (!flashcards.isEmpty()) {
            // Update the answer of the current flashcard
            Flashcard currentFlashcard = flashcards.get(currentIndex);
            currentFlashcard.setAnswer(newAnswer);
            // Update the flashcard in the database
            Executors.newSingleThreadExecutor().execute(() -> {
                flashcardDao.update(currentFlashcard);
                // fetch the updated flashcards in the main thread
                runOnUiThread(() -> {
                    loadFlashcards();
                    updateCardData();
                });
            });
        }
    }

}


