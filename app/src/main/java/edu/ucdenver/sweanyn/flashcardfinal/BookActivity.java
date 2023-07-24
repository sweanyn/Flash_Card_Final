package edu.ucdenver.sweanyn.flashcardfinal;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.ucdenver.sweanyn.flashcardfinal.databinding.ActivityBookBinding;

public class BookActivity extends AppCompatActivity {
    private ActivityBookBinding binding;
    private BookDao bookDao;
    private long bookId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        bookId = getIntent().getLongExtra("book_id", -1);
        if (bookId != -1) {
            FlashcardDatabase db = FlashcardDatabase.getInstance(this);
            bookDao = db.bookDao();
            getBook(bookId);
        }
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
                    // Add your code to handle the new card here
                    // For example, you could refresh the list of cards
                }
            });
            dialog.show(getSupportFragmentManager(), "AddCardDialog");
            return true;

        }

        else {
            return super.onOptionsItemSelected(item);
        }
    }
}


