package edu.ucdenver.sweanyn.flashcardfinal;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.room.Room;

import java.util.concurrent.Executors;

import edu.ucdenver.sweanyn.flashcardfinal.databinding.DialogAddBookBinding;

public class AddBookDialog extends DialogFragment {

    // Define the DAOs here
    private FlashcardDao flashcardDao;
    private BookDao bookDao;
    private DialogAddBookBinding binding;

    //Defining the listener interface
    public interface Listener {
        void onBookAdded(Book newBook);
    }
    private Listener listener;
    public void setListener(Listener listener) {
        this.listener = listener;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        binding = DialogAddBookBinding.inflate(LayoutInflater.from(getContext()));

        // Get the instance of FlashcardDatabase and then get the DAOs
        FlashcardDatabase flashcardDatabase = FlashcardDatabase.getInstance(getContext());
        flashcardDao = flashcardDatabase.flashcardDao();
        bookDao = flashcardDatabase.bookDao();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(binding.getRoot());

        binding.buttonMainMenu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();

                    }
                }
        );

        binding.buttonClear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.textInputBook.setText("");
                        binding.textInputQuestion.setText("");
                        binding.textInputAnswer.setText("");
                    }
                }
        );

        binding.buttonSave.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // need to add on click for save button
                        // Fetch the user's inputs
                        String bookTitle = binding.textInputBook.getText().toString();
                        String question = binding.textInputQuestion.getText().toString();
                        String answer = binding.textInputAnswer.getText().toString();

                        // Running the Database query in a second thread (To check if the book exists already)
                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                // Search for a book with the same name in the database
                                Book existingBook = bookDao.findByName(bookTitle);

                                if (existingBook != null) {
                                    // Book exists, add a new flashcard to this book
                                    Flashcard newFlashcard = new Flashcard();
                                    newFlashcard.setQuestion(question);
                                    newFlashcard.setAnswer(answer);
                                    newFlashcard.setBookId((int) existingBook.getId());
                                    flashcardDao.insert(newFlashcard);
                                } else {
                                    // Book doesn't exist, create a new book and a new flashcard
                                    Book newBook = new Book();
                                    newBook.setBookName(bookTitle);
                                    long newBookId = bookDao.insert(newBook);
                                    newBook.setId(newBookId);

                                    Flashcard newFlashcard = new Flashcard();
                                    newFlashcard.setQuestion(question);
                                    newFlashcard.setAnswer(answer);
                                    newFlashcard.setBookId((int) newBookId);
                                    flashcardDao.insert(newFlashcard);

                                    if (listener != null) {
                                        // This is running on a separate thread, need to run on UI thread
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                listener.onBookAdded(newBook);
                                            }
                                        });
                                    }
                                }
                            }
                        });

                        dismiss();
                    }
                }
        );

        return builder.create();
    }
}
